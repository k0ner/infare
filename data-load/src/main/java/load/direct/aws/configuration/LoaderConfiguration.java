package load.direct.aws.configuration;

import com.datastax.driver.core.Session;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Semaphore;

import load.direct.aws.CassandraLoader;
import load.direct.aws.CassandraSink;
import load.direct.aws.HttpFileInputSource;
import load.direct.aws.InfareInputSource;
import load.direct.aws.Inserts;

@Configuration
public class LoaderConfiguration {

    @Value("${infare.input.url}")
    private String url;

    @Value("${parallel.batches:40}")
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
    public Executor callbackExecutor(@Value("${callback.executors.threads:2}") Integer threads) {
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
                               @Value("${batch.size:100}") Integer batchSize,
                               CassandraSink cassandraSink) {
        return new Inserts(inputSource, batchSize, cassandraSink.insertStatement);
    }

    @Bean
    public ForkJoinPool forkJoinPool(@Value("${inserts.parallelism:16}") Integer parallelism) {
        return new ForkJoinPool(parallelism);
    }
}
