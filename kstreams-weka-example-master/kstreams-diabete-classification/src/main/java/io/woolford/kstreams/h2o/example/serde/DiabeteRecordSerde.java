package io.woolford.kstreams.h2o.example.serde;

import io.woolford.kstreams.h2o.example.DiabeteRecord;
import org.apache.kafka.common.serialization.Serdes;

public class DiabeteRecordSerde extends Serdes.WrapperSerde<DiabeteRecord> {
    public DiabeteRecordSerde() {
        super(new JsonSerializer(), new JsonDeserializer(DiabeteRecord.class));
    }
}
