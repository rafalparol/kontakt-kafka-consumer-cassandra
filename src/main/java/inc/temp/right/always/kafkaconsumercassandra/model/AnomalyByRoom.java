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
@Table("anomalies_by_room")
public class AnomalyByRoom {
    @PrimaryKeyColumn(name = "roomid", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String roomId;
    @PrimaryKeyColumn(name = "measuredat", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
    private Instant measuredAt;
    @PrimaryKeyColumn(name = "thermometerid", ordinal = 2, type = PrimaryKeyType.CLUSTERED)
    private String thermometerId;
    @Column("temperature")
    private double temperature;
}
