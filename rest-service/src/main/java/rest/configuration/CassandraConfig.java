package rest.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.core.mapping.CassandraMappingContext;

@Configuration
public class CassandraConfig extends AbstractCassandraConfiguration {

    @Value("${cassandra.keyspace}")
    private String keyspace;

    @Value("${cassandra.hosts}")
    private String hosts;

    @Value("${cassandra.port}")
    private int port;

    @Override
    protected String getKeyspaceName() {
        return keyspace;
    }

    @Bean
    public CassandraClusterFactoryBean cluster() {
        CassandraClusterFactoryBean cluster =
                new CassandraClusterFactoryBean();
        cluster.setContactPoints(hosts);
        cluster.setPort(port);
        return cluster;
    }

    @Bean
    public CassandraMappingContext cassandraMapping() throws ClassNotFoundException {
        return new CassandraMappingContext();
    }
}