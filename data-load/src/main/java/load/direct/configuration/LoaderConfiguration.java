package load.direct.configuration;

import com.datastax.driver.core.Session;
import load.direct.CassandraLoader;
import load.direct.CassandraSink;
import load.direct.Inserts;
import load.input.HttpFileInputSource;
import load.input.InfareInputSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Semaphore;

@Configuration
@Import(load.bulk.configuration.Infare.class)
public class LoaderConfiguration {

    @Value("${infare.input.url}")
    private String url;

    @Value("${parallel.batches:20}")
    private Integer parallelBatches;

    @Bean
    public InfareInputSource httpInputSource() {
        return new HttpFileInputSource(url);
    }

    @Bean
    public Semaphore semaphore() {
        return new Semaphore(parallelBatches, true);
    }

    @Bean
    public Executor callbackExecutor(@Value("${callback.executors.threads:10}") Integer threads) {
        return Executors.newFixedThreadPool(threads);
    }

    @Bean
    public CassandraLoader cassandraLoader(Session session,
                                           Semaphore semaphore,
                                           Executor callbackExecutor) {
        return new CassandraLoader(session, semaphore, callbackExecutor);
    }

    @Bean
    public Inserts bulkInserts(InfareInputSource inputSource,
                               @Value("${batch.size:80}") Integer batchSize,
                               CassandraSink cassandraSink) {
        return new Inserts(inputSource, batchSize, cassandraSink.insertStatement);
    }

    @Bean
    public ForkJoinPool forkJoinPool(@Value("${inserts.parallelism:25}") Integer parallelism) {
        return new ForkJoinPool(parallelism);
    }
}
