package load.bulk.configuration;

import com.datastax.driver.core.RegularStatement;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import load.domain.InfareRecord;
import load.input.HttpFileInputSource;
import load.input.InfareInputSource;
import load.input.MultiInputSources;

import org.apache.cassandra.dht.Murmur3Partitioner;
import org.apache.cassandra.io.sstable.CQLSSTableWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static com.datastax.driver.core.querybuilder.QueryBuilder.bindMarker;
import static java.util.stream.Collectors.toList;

@Configuration
public class BulkLoaderConfiguration {

    @Bean
    public InfareInputSource httpInputSource(Infare infare) {
        return new MultiInputSources(
                infare.getInputUrls().stream()
                        .map(HttpFileInputSource::new)
                        .collect(toList()));
    }

    @Bean
    public CQLSSTableWriter tableWriter(@Value("${cassandra.keyspace}") String keyspace,
                                        @Value("${cassandra.table}") String table,
                                        @Value("${output.directory}") String outputDirectory,
                                        @Value("${schema.path}") String schemaPath) throws IOException {

        Files.createDirectories(Paths.get(outputDirectory));

        final List<Object> bindMarkers = InfareRecord.columns.stream()
                .map(ignored -> bindMarker())
                .collect(toList());

        final RegularStatement statement = QueryBuilder.insertInto(keyspace, table)
                .values(InfareRecord.columns, bindMarkers);

        return CQLSSTableWriter.builder().inDirectory(outputDirectory)
                .forTable(schema(schemaPath, keyspace, table))
                .using(statement.getQueryString())
                .withPartitioner(new Murmur3Partitioner())
                .build();
    }

    private String schema(String schemaPath,
                          String keyspace,
                          String table) {
        try {
            final URL resource = this.getClass().getClassLoader().getResource(schemaPath);
            return String.format(new String(Files.readAllBytes(Paths.get(resource.toURI()))), keyspace, table);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

}
