package io.woolford.kafkairisdata;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
public class DiabeteConsumer {

    private final Logger LOG = LoggerFactory.getLogger(DiabeteConsumer.class);

    @KafkaListener(topics="pridection", groupId = "diabete-consumer")
    private void processMessage(String message) {
        //LOG.info(message);
        System.out.println(message);
    }

}
