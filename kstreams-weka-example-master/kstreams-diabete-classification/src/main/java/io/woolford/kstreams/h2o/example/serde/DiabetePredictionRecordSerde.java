package io.woolford.kstreams.h2o.example.serde;

import io.woolford.kstreams.h2o.example.DiabeteClassifiedRecord;
import org.apache.kafka.common.serialization.Serdes;

public class DiabetePredictionRecordSerde extends Serdes.WrapperSerde<DiabeteClassifiedRecord> {
    public DiabetePredictionRecordSerde() {
        super(new JsonSerializer(), new JsonDeserializer(DiabeteClassifiedRecord.class));
    }
}