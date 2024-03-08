package inc.temp.right.always.kafkaconsumercassandra.repositories;

import inc.temp.right.always.kafkaconsumercassandra.model.AnomalyByThermometer;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnomalyByThermometerRepository extends CassandraRepository<AnomalyByThermometer, String> {

}
