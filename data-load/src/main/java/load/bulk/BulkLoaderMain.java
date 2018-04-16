package load.bulk;

import load.input.InfareInputSource;

import org.apache.cassandra.io.sstable.CQLSSTableWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.io.IOException;
import java.util.List;
import java.util.function.Supplier;

@SpringBootApplication
@EnableConfigurationProperties
public class BulkLoaderMain implements CommandLineRunner {

    private final InfareInputSource infareInputSource;
    private final ThreadLocal<CQLSSTableWriter> writer;
    private final List<CQLSSTableWriter> writers;

    @Autowired
    public BulkLoaderMain(InfareInputSource infareInputSource,
                          Supplier<CQLSSTableWriter> writerSupplier,
                          List<CQLSSTableWriter> writers) {

        this.infareInputSource = infareInputSource;
        this.writer = ThreadLocal.withInitial(writerSupplier);
        this.writers = writers;
    }

    @Override
    public void run(String... args) throws Exception {

        infareInputSource
                .get()
                .parallel()
                .forEach(infareRecord -> {
                    try {
                        Metrics.lines.mark();
                        writer.get()
                              .addRow(infareRecord.generateValues());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

        writers.forEach(writer -> {
            try {
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }

    public static void main(String[] args) {
        SpringApplication.run(BulkLoaderMain.class, args);
    }

}
