package load.direct;

import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.RegularStatement;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.QueryBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static com.datastax.driver.core.querybuilder.QueryBuilder.bindMarker;
import static java.util.stream.Collectors.toList;

public class CassandraSink {

    private static final Logger log = LoggerFactory.getLogger(CassandraSink.class);

    public final PreparedStatement insertStatement;
    private final Session session;
    private final String keyspace;
    private final Integer replicationFactor;

    public CassandraSink(Session session,
                         String keyspace,
                         String table,
                         Integer replicationFactor) {
        this.session = session;
        this.keyspace = keyspace;
        this.replicationFactor = replicationFactor;

        ensureKeyspaceCreated();
        ensureTableCreated();

        final List<Object> bindMarkers = InfareRecord.columns.stream()
                .map(ignored -> bindMarker())
                .collect(toList());

        final RegularStatement statement = QueryBuilder.insertInto(keyspace, table)
                .values(InfareRecord.columns, bindMarkers);

        this.insertStatement = session.prepare(statement);
    }

    private void ensureTableCreated() {
        final String cqlFilename = "cql/prices.cql";
        final URL resource = this.getClass().getClassLoader().getResource(cqlFilename);
        if (resource == null) {
            log.warn("File {} with CQL does not exist. Table will not be created!", cqlFilename);
            return;
        }

        final String cql;
        try {
            cql = new String(Files.readAllBytes(Paths.get(resource.toURI())));
            session.execute("use " + keyspace + "; ");
            session.execute(cql);
        } catch (IOException | URISyntaxException e) {
            log.warn("Exception during reading file {}. Table will not be created!", cqlFilename, e);
        }
    }

    private void ensureKeyspaceCreated() {
        session.execute(String.format(
                "create keyspace if not exists %s WITH replication = {'class': 'SimpleStrategy', 'replication_factor': %d}",
                keyspace, replicationFactor));
    }
}
