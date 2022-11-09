package io.woolford.kafkairisdata;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;


@Component
public class DiabeteKafkaFeed {

    private final Logger logger = LoggerFactory.getLogger(DiabeteKafkaFeed.class);

    @Autowired
    KafkaTemplate kafkaTemplate;

    private List<diabeteRecord> diabeteRecordList;

    public DiabeteKafkaFeed() throws IOException {

        String diabeteCsvString = "";
        ClassPathResource cpr = new ClassPathResource("testDiabetes.csv");
        try {
            byte[] bdata = FileCopyUtils.copyToByteArray(cpr.getInputStream());
            diabeteCsvString = new String(bdata, StandardCharsets.UTF_8);
        } catch (IOException e) {
            logger.warn("IOException", e);
        }

        CsvSchema schema = CsvSchema.builder()
                .addColumn("preg", CsvSchema.ColumnType.NUMBER)
                .addColumn("plas", CsvSchema.ColumnType.NUMBER)
                .addColumn("pres", CsvSchema.ColumnType.NUMBER)
                .addColumn("skin", CsvSchema.ColumnType.NUMBER)
                .addColumn("insu", CsvSchema.ColumnType.NUMBER)
                .addColumn("mass", CsvSchema.ColumnType.NUMBER)
                .addColumn("pedi", CsvSchema.ColumnType.NUMBER)
                .addColumn("age", CsvSchema.ColumnType.NUMBER)
                .addColumn("clas", CsvSchema.ColumnType.STRING)
                .build().withHeader();

        CsvMapper csvMapper = new CsvMapper();
        MappingIterator<diabeteRecord> diabeteIter = csvMapper.readerFor(diabeteRecord.class).with(schema).readValues(diabeteCsvString);
        this.diabeteRecordList = diabeteIter.readAll();

    }

    @Bean
    public NewTopic createDiabeteTopic() {
        return TopicBuilder.name("test")
                .partitions(1)
                .replicas(3)
                .build();
    }

    @Scheduled(fixedDelay = 1000L)
    private void publishDiabeteRecord() throws JsonProcessingException {

        Random rand = new Random();
        diabeteRecord diabeteRecord = diabeteRecordList.get(rand.nextInt(diabeteRecordList.size()));

        ObjectMapper mapper = new ObjectMapper();
        String diabeteRecordJson = mapper.writeValueAsString(diabeteRecord);

        kafkaTemplate.send("test", diabeteRecordJson);

    }

}
