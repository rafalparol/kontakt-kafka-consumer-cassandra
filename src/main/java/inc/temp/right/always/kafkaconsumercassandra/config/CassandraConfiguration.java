package inc.temp.right.always.kafkaconsumercassandra.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;

@Configuration
public class CassandraConfiguration extends AbstractCassandraConfiguration {
    @Value("${main.config.cassandra.keyspace-name}")
    private String keyspaceName;

    @Value("${main.config.cassandra.port}")
    private String port;

    @Value("${main.config.cassandra.contact-point}")
    private String contactPoint;

    /*
     * Provide a contact point to the configuration.
     */
    @Override
    public String getContactPoints() {
        return contactPoint + ":" + port;
    }

    /*
     * Provide a keyspace name to the configuration.
     */
    @Override
    public String getKeyspaceName() {
        return keyspaceName;
    }
}
