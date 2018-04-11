package load.direct.aws;

import com.codahale.metrics.ConsoleReporter;
import com.datastax.driver.core.BatchStatement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static load.direct.aws.Metrics.metricRegistry;

@SpringBootApplication
public class CassandraLoaderMain implements CommandLineRunner {

    private final Inserts inserts;

    private final CassandraLoader cassandraLoader;

    private final ForkJoinPool forkJoinPool;

    @Autowired
    public CassandraLoaderMain(Inserts inserts,
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

        final Stream<BatchStatement> boundStatements = inserts.generate();

        forkJoinPool.submit(() ->
                boundStatements
                        .parallel()
                        .forEach(boundStatement -> {

                            try {
                                cassandraLoader.executeAsync(boundStatement);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        })
        );
    }

    public static void main(String[] args) {
        SpringApplication.run(CassandraLoaderMain.class, args);
    }
}
