package load.bulk;

import load.input.InfareInputSource;

import org.apache.cassandra.io.sstable.CQLSSTableWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.io.IOException;

@SpringBootApplication
@EnableConfigurationProperties
public class BulkLoaderMain implements CommandLineRunner {

    private final InfareInputSource infareInputSource;
    private final CQLSSTableWriter writer;

    @Autowired
    public BulkLoaderMain(InfareInputSource infareInputSource,
                          CQLSSTableWriter writer) {

        this.infareInputSource = infareInputSource;
        this.writer = writer;
    }

    @Override
    public void run(String... args) throws Exception {

        infareInputSource
                .get()
                .forEach(infareRecord -> {
                    try {
                        Metrics.lines.mark();
                        writer.addRow(infareRecord.generateValues());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
        writer.close();
    }

    public static void main(String[] args) {
        SpringApplication.run(BulkLoaderMain.class, args);
    }

}
