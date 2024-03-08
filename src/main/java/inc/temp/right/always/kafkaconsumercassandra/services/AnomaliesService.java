package inc.temp.right.always.kafkaconsumercassandra.services;

import com.datastax.oss.driver.api.core.DefaultConsistencyLevel;
import inc.temp.right.always.kafkaconsumercassandra.model.AnomalyByRoom;
import inc.temp.right.always.kafkaconsumercassandra.model.AnomalyByThermometer;
import inc.temp.right.always.kafkaconsumercassandra.model.AnomalyByTime;
import inc.temp.right.always.kafkaconsumercassandra.repositories.AnomalyByRoomRepository;
import inc.temp.right.always.kafkaconsumercassandra.repositories.AnomalyByThermometerRepository;
import inc.temp.right.always.kafkaconsumercassandra.repositories.AnomalyByTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.InsertOptions;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
public class AnomaliesService {
    InsertOptions insertOptions = InsertOptions.builder().consistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
    @Autowired
    private AnomalyByRoomRepository anomalyByRoomRepository;
    @Autowired
    private AnomalyByThermometerRepository anomalyByThermometerRepository;
    @Autowired
    private AnomalyByTimeRepository anomalyByTimeRepository;
    @Autowired
    private CassandraTemplate cassandraTemplate;

    @Retryable
    public AnomalyByRoom save(AnomalyByRoom anomalyByRoom) {
        return anomalyByRoomRepository.save(anomalyByRoom);
    }

    @Retryable
    public AnomalyByRoom saveWithOptions(AnomalyByRoom anomalyByRoom) {
        return cassandraTemplate.insert(anomalyByRoom, insertOptions).getEntity();
    }

    @Retryable
    public AnomalyByThermometer save(AnomalyByThermometer anomalyByThermometer) {
        return anomalyByThermometerRepository.save(anomalyByThermometer);
    }

    @Retryable
    public AnomalyByThermometer saveWithOptions(AnomalyByThermometer anomalyByThermometer) {
        return cassandraTemplate.insert(anomalyByThermometer, insertOptions).getEntity();
    }

    @Retryable
    public AnomalyByTime save(AnomalyByTime anomalyByTime) {
        return anomalyByTimeRepository.save(anomalyByTime);
    }

    @Retryable
    public AnomalyByTime saveWithOptions(AnomalyByTime anomalyByTime) {
        return cassandraTemplate.insert(anomalyByTime, insertOptions).getEntity();
    }
}
