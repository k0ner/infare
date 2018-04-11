package load.direct.configuration;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import load.direct.CassandraSink;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CassandraConfiguration {

    @Value("${cassandra.hosts}")
    private String cassandraHosts;

    @Value("${cassandra.port}")
    private Integer cassandraPort;

    @Value("${cassandra.keyspace:infare}")
    private String keyspace;

    @Value("${cassandra.table:prices}")
    private String table;

    @Value("${cassandra.replication.factor}")
    private Integer replicationFactor;

    @Bean
    public Cluster cluster() {
        return Cluster.builder()
                .addContactPoints(cassandraHosts)
                .withPort(cassandraPort)
                .build();
    }

    @Bean
    public Session session() {
        return cluster().newSession();
    }

    @Bean
    public CassandraSink cassandraSink() {
        return new CassandraSink(session(), keyspace, table, replicationFactor);
    }

}
