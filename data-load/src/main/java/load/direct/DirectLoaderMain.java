package load.direct;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Timer;
import com.datastax.driver.core.BatchStatement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static load.direct.Metrics.metricRegistry;

@SpringBootConfiguration
@ComponentScan(value = "load.direct")
@EnableConfigurationProperties
public class DirectLoaderMain implements CommandLineRunner {

    private final Inserts inserts;

    private final CassandraLoader cassandraLoader;

    private final ForkJoinPool forkJoinPool;

    @Autowired
    public DirectLoaderMain(Inserts inserts,
                            CassandraLoader cassandraLoader,
                            ForkJoinPool forkJoinPool) {
        this.inserts = inserts;
        this.cassandraLoader = cassandraLoader;
        this.forkJoinPool = forkJoinPool;
    }

    @Override
    public void run(String... args) throws Exception {
        ConsoleReporter reporter = ConsoleReporter.forRegistry(metricRegistry)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build();
        reporter.start(5, TimeUnit.SECONDS);

        final Stream<BatchStatement> batchStatements = inserts.generate();

        forkJoinPool.submit(() ->
                batchStatements
                        .parallel()
                        .forEach(batchStatement -> {
                            final Timer.Context batchExecuteTime = Metrics.batchExecuteTime.time();
                            try {
                                cassandraLoader.executeAsync(batchStatement);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            } finally {
                                batchExecuteTime.stop();
                            }
                        })
        );
    }

    public static void main(String[] args) {
        SpringApplication.run(DirectLoaderMain.class, args);
    }
}
