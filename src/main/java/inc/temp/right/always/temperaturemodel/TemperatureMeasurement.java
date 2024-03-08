package inc.temp.right.always.temperaturemodel;

import inc.temp.right.always.kafkaconsumercassandra.model.AnomalyByRoom;
import inc.temp.right.always.kafkaconsumercassandra.model.AnomalyByThermometer;
import inc.temp.right.always.kafkaconsumercassandra.model.AnomalyByTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TemperatureMeasurement {
    private String roomId;
    private String thermometerId;
    private long timestamp;
    private double temperature;

    public AnomalyByRoom toAnomalyByRoom() {
        return new AnomalyByRoom(this.roomId, Instant.ofEpochMilli(this.timestamp), this.thermometerId, this.temperature);
    }

    public AnomalyByThermometer toAnomalyByThermometer() {
        return new AnomalyByThermometer(this.thermometerId, Instant.ofEpochMilli(this.timestamp), this.roomId, this.temperature);
    }

    public AnomalyByTime toAnomalyByTime() {
        return new AnomalyByTime(Instant.ofEpochMilli(this.timestamp), this.thermometerId, this.roomId, this.temperature);
    }
}
