package inc.temp.right.always.kafkaconsumercassandra;

import com.datastax.oss.driver.api.core.DefaultConsistencyLevel;
import inc.temp.right.always.kafkaconsumercassandra.listeners.KafkaConsumerListener;
import inc.temp.right.always.kafkaconsumercassandra.model.AnomalyByRoom;
import inc.temp.right.always.kafkaconsumercassandra.model.AnomalyByThermometer;
import inc.temp.right.always.kafkaconsumercassandra.model.AnomalyByTime;
import inc.temp.right.always.kafkaconsumercassandra.repositories.AnomalyByRoomRepository;
import inc.temp.right.always.kafkaconsumercassandra.repositories.AnomalyByThermometerRepository;
import inc.temp.right.always.kafkaconsumercassandra.repositories.AnomalyByTimeRepository;
import inc.temp.right.always.kafkaconsumercassandra.services.AnomaliesService;
import inc.temp.right.always.temperaturemodel.TemperatureMeasurement;
import org.junit.jupiter.api.Test;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.InsertOptions;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class KafkaConsumerCassandraApplicationTests {
	@Test
	void KafkaConsumerListener_handleTemperatureMeasurement_Test() {
		KafkaConsumerListener kafkaConsumerListener = new KafkaConsumerListener();
		AnomaliesService anomaliesService = mock(AnomaliesService.class);

		TemperatureMeasurement temperatureMeasurement = spy(new TemperatureMeasurement("room-1", "thermometer-1", Timestamp.valueOf("2024-02-01 00:00:01").toInstant().toEpochMilli(), 20.0));
		AnomalyByRoom anomalyByRoom = new AnomalyByRoom("room-1", Timestamp.valueOf("2024-02-01 00:00:01").toInstant(), "thermometer-1", 20.0);
		AnomalyByThermometer anomalyByThermometer = new AnomalyByThermometer("thermometer-1", Timestamp.valueOf("2024-02-01 00:00:01").toInstant(), "room-1", 20.0);
		AnomalyByTime anomalyByTime = new AnomalyByTime(Timestamp.valueOf("2024-02-01 00:00:01").toInstant(), "thermometer-1", "room-1", 20.0);

		doReturn(anomalyByRoom).when(temperatureMeasurement).toAnomalyByRoom();
		doReturn(anomalyByThermometer).when(temperatureMeasurement).toAnomalyByThermometer();
		doReturn(anomalyByTime).when(temperatureMeasurement).toAnomalyByTime();
		doReturn(anomalyByRoom).when(anomaliesService).saveWithOptions(anomalyByRoom);
		doReturn(anomalyByThermometer).when(anomaliesService).saveWithOptions(anomalyByThermometer);
		doReturn(anomalyByTime).when(anomaliesService).saveWithOptions(anomalyByTime);

		kafkaConsumerListener.setAnomaliesService(anomaliesService);

		kafkaConsumerListener.handleTemperatureMeasurement(temperatureMeasurement);

		verify(temperatureMeasurement, times(1)).toAnomalyByRoom();
		verify(temperatureMeasurement, times(1)).toAnomalyByThermometer();
		verify(temperatureMeasurement, times(1)).toAnomalyByTime();
		verify(anomaliesService, times(1)).saveWithOptions(anomalyByRoom);
		verify(anomaliesService, times(1)).saveWithOptions(anomalyByThermometer);
		verify(anomaliesService, times(1)).saveWithOptions(anomalyByTime);
	}

	@Test
	void AnomaliesService_save_and_saveWithOptions_Test() {
		AnomaliesService anomaliesService = new AnomaliesService();

		InsertOptions insertOptions = InsertOptions.builder().consistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();

		AnomalyByRoomRepository anomalyByRoomRepository = mock(AnomalyByRoomRepository.class);
		AnomalyByThermometerRepository anomalyByThermometerRepository = mock(AnomalyByThermometerRepository.class);
		AnomalyByTimeRepository anomalyByTimeRepository = mock(AnomalyByTimeRepository.class);
		CassandraTemplate cassandraTemplate = mock(CassandraTemplate.class);

		AnomalyByRoom anomalyByRoom = new AnomalyByRoom("room-1", Timestamp.valueOf("2024-02-01 00:00:01").toInstant(), "thermometer-1", 20.0);
		AnomalyByThermometer anomalyByThermometer = new AnomalyByThermometer("thermometer-1", Timestamp.valueOf("2024-02-01 00:00:01").toInstant(), "room-1", 20.0);
		AnomalyByTime anomalyByTime = new AnomalyByTime(Timestamp.valueOf("2024-02-01 00:00:01").toInstant(), "thermometer-1", "room-1", 20.0);

		when(anomalyByRoomRepository.save(anomalyByRoom)).thenReturn(anomalyByRoom);
		when(anomalyByThermometerRepository.save(anomalyByThermometer)).thenReturn(anomalyByThermometer);
		when(anomalyByTimeRepository.save(anomalyByTime)).thenReturn(anomalyByTime);

//		when(cassandraTemplate.insert(anomalyByRoom, insertOptions).getEntity()).thenReturn(anomalyByRoom);
//		when(cassandraTemplate.insert(anomalyByThermometer, insertOptions).getEntity()).thenReturn(anomalyByThermometer);
//		when(cassandraTemplate.insert(anomalyByTime, insertOptions).getEntity()).thenReturn(anomalyByTime);

		anomaliesService.setAnomalyByRoomRepository(anomalyByRoomRepository);
		anomaliesService.setAnomalyByThermometerRepository(anomalyByThermometerRepository);
		anomaliesService.setAnomalyByTimeRepository(anomalyByTimeRepository);
		anomaliesService.setCassandraTemplate(cassandraTemplate);

//		AnomalyByRoom resultAnomalyByRoom1 = anomaliesService.saveWithOptions(anomalyByRoom);
//		AnomalyByThermometer resultAnomalyByThermometer1 = anomaliesService.saveWithOptions(anomalyByThermometer);
//		AnomalyByTime resultAnomalyByTime1 = anomaliesService.saveWithOptions(anomalyByTime);
		AnomalyByRoom resultAnomalyByRoom2 = anomaliesService.save(anomalyByRoom);
		AnomalyByThermometer resultAnomalyByThermometer2 = anomaliesService.save(anomalyByThermometer);
		AnomalyByTime resultAnomalyByTime2 = anomaliesService.save(anomalyByTime);

//		verify(cassandraTemplate, times(1)).insert(anomalyByRoom, insertOptions);
//		verify(cassandraTemplate, times(1)).insert(anomalyByThermometer, insertOptions);
//		verify(cassandraTemplate, times(1)).insert(anomalyByTime, insertOptions);
		verify(anomalyByRoomRepository, times(1)).save(anomalyByRoom);
		verify(anomalyByThermometerRepository, times(1)).save(anomalyByThermometer);
		verify(anomalyByTimeRepository, times(1)).save(anomalyByTime);

//		assertEquals(anomalyByRoom, resultAnomalyByRoom1, String.format("Expected result: %s and returned result: %s are not the same when saving anomaly.", anomalyByRoom, resultAnomalyByRoom1));
//		assertEquals(anomalyByThermometer, resultAnomalyByThermometer1, String.format("Expected result: %s and returned result: %s are not the same when saving anomaly.", anomalyByThermometer, resultAnomalyByRoom1));
//		assertEquals(anomalyByTime, resultAnomalyByTime1, String.format("Expected result: %s and returned result: %s are not the same when saving anomaly.", anomalyByTime, resultAnomalyByRoom1));
		assertEquals(anomalyByRoom, resultAnomalyByRoom2, String.format("Expected result: %s and returned result: %s are not the same when saving anomaly.", anomalyByRoom, resultAnomalyByRoom2));
		assertEquals(anomalyByThermometer, resultAnomalyByThermometer2, String.format("Expected result: %s and returned result: %s are not the same when saving anomaly.", anomalyByThermometer, resultAnomalyByRoom2));
		assertEquals(anomalyByTime, resultAnomalyByTime2, String.format("Expected result: %s and returned result: %s are not the same when saving anomaly.", anomalyByTime, resultAnomalyByRoom2));
	}

	@Test
	void TemperatureMeasurement_toAnomalyByRoom_and_toAnomalyByThermometer_and_toAnomalyByTime_Test() {
		TemperatureMeasurement temperatureMeasurement = new TemperatureMeasurement("room-1", "thermometer-1", Timestamp.valueOf("2024-02-01 00:00:01").toInstant().toEpochMilli(), 20.0);

		AnomalyByRoom anomalyByRoom = new AnomalyByRoom("room-1", Timestamp.valueOf("2024-02-01 00:00:01").toInstant(), "thermometer-1", 20.0);
		AnomalyByThermometer anomalyByThermometer = new AnomalyByThermometer("thermometer-1", Timestamp.valueOf("2024-02-01 00:00:01").toInstant(), "room-1", 20.0);
		AnomalyByTime anomalyByTime = new AnomalyByTime(Timestamp.valueOf("2024-02-01 00:00:01").toInstant(), "thermometer-1", "room-1", 20.0);

		AnomalyByRoom anomalyByRoomResult = temperatureMeasurement.toAnomalyByRoom();
		AnomalyByThermometer anomalyByThermometerResult = temperatureMeasurement.toAnomalyByThermometer();
		AnomalyByTime anomalyByTimeResult = temperatureMeasurement.toAnomalyByTime();

		assertEquals(anomalyByRoom, anomalyByRoomResult, String.format("Expected result: %s and returned result: %s are not the same when saving anomaly.", anomalyByRoom, anomalyByRoomResult));
		assertEquals(anomalyByThermometer, anomalyByThermometerResult, String.format("Expected result: %s and returned result: %s are not the same when saving anomaly.", anomalyByThermometer, anomalyByThermometerResult));
		assertEquals(anomalyByTime, anomalyByTimeResult, String.format("Expected result: %s and returned result: %s are not the same when saving anomaly.", anomalyByTime, anomalyByTimeResult));
	}
}
