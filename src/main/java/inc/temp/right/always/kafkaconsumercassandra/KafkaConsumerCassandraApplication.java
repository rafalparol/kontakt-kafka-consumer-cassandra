package inc.temp.right.always.kafkaconsumercassandra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@SpringBootApplication
@EnableCassandraRepositories(basePackages = "inc.temp.right.always.kafkaconsumercassandra.repositories")
public class KafkaConsumerCassandraApplication {
	public static void main(String[] args) {
		SpringApplication.run(KafkaConsumerCassandraApplication.class, args);
	}
}
