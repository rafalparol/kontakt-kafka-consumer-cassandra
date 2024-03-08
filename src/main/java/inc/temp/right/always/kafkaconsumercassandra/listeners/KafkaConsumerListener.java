package inc.temp.right.always.kafkaconsumercassandra.listeners;

import inc.temp.right.always.kafkaconsumercassandra.services.AnomaliesService;
import inc.temp.right.always.temperaturemodel.TemperatureMeasurement;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Log4j2
@KafkaListener(topics = "${main.config.kafka.topic}")
public class KafkaConsumerListener {
    @Autowired
    private AnomaliesService anomaliesService;
    @KafkaHandler
    public void handleTemperatureMeasurement(TemperatureMeasurement temperatureMeasurement) {
        log.info(String.format("Message received: %s", temperatureMeasurement));
        anomaliesService.saveWithOptions(temperatureMeasurement.toAnomalyByRoom());
        anomaliesService.saveWithOptions(temperatureMeasurement.toAnomalyByThermometer());
        anomaliesService.saveWithOptions(temperatureMeasurement.toAnomalyByTime());
    }
}
