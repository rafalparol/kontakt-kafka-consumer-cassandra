package inc.temp.right.always.kafkaconsumercassandra.repositories;

import inc.temp.right.always.kafkaconsumercassandra.model.AnomalyByRoom;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnomalyByRoomRepository extends CassandraRepository<AnomalyByRoom, String> {

}
