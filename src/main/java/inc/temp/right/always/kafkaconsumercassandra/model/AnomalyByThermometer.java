package inc.temp.right.always.kafkaconsumercassandra.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.Instant;

@Data
@AllArgsConstructor
@Table("anomalies_by_thermometer")
public class AnomalyByThermometer {
    @PrimaryKeyColumn(name = "thermometerid", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String thermometerId;
    @PrimaryKeyColumn(name = "measuredat", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
    private Instant measuredAt;
    @Column("roomid")
    private String roomId;
    @Column("temperature")
    private double temperature;
}
