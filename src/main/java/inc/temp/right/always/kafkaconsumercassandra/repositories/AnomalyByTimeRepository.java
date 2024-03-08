package inc.temp.right.always.kafkaconsumercassandra.repositories;

import inc.temp.right.always.kafkaconsumercassandra.model.AnomalyByTime;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnomalyByTimeRepository extends CassandraRepository<AnomalyByTime, Long> {

}
