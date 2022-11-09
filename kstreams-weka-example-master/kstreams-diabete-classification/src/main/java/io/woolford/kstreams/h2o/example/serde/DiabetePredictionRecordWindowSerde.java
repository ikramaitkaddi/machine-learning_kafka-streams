package io.woolford.kstreams.h2o.example.serde;

import io.woolford.kstreams.h2o.example.DiabeteClassifiedWindowRecord;
import org.apache.kafka.common.serialization.Serdes;

public class DiabetePredictionRecordWindowSerde extends Serdes.WrapperSerde<DiabeteClassifiedWindowRecord> {
    public DiabetePredictionRecordWindowSerde() {
        super(new JsonSerializer(), new JsonDeserializer(DiabeteClassifiedWindowRecord.class));
    }
}