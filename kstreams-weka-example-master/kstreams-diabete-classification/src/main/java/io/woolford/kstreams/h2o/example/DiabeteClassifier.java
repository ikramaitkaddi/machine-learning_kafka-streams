package io.woolford.kstreams.h2o.example;


import io.woolford.kstreams.h2o.example.serde.DiabeteRecordSerde;
import io.woolford.kstreams.h2o.example.serde.DiabetePredictionRecordSerde;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.streams.*;
import org.apache.kafka.streams.kstream.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.io.InputStream;

import java.time.Duration;
import java.util.*;

class DiabeteClassifier {

    final Logger LOG = LoggerFactory.getLogger(DiabeteClassifier.class);
    public static final String MODElPATH = "C://data/modelDiabetes.bin";


    DiabeteClassifier() throws Exception {


    }

    void run() throws IOException {

        // load properties
        Properties props = new Properties();
        InputStream input = DiabeteClassifier.class.getClassLoader().getResourceAsStream("config.properties");
        props.load(input);

        DiabeteRecordSerde diabeteRecordSerde = new DiabeteRecordSerde();
        DiabetePredictionRecordSerde diabetePredictionRecordSerde = new DiabetePredictionRecordSerde();
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, diabetePredictionRecordSerde.getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, diabeteRecordSerde.getClass());

        // create the topics
        createTopics(props);

        final StreamsBuilder builder = new StreamsBuilder();

        // create a stream of the raw iris records
        KStream<DiabeteClassifiedRecord, DiabeteRecord> diabeteStream = builder.stream("test", Consumed.with(diabetePredictionRecordSerde, diabeteRecordSerde));

        // classify the raw iris messages with the classifyIris function.
        KStream<DiabeteClassifiedRecord, DiabeteRecord> diabeteStreamClassified = diabeteStream.map((k, v) -> {

            // predict the species
            DiabeteRecord diabeteRecord = null;

            try {
                diabeteRecord = classifyDiabete(v);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            // create actual/predicted record
            DiabeteClassifiedRecord irisClassifiedRecord = new DiabeteClassifiedRecord();
            irisClassifiedRecord.setClas(diabeteRecord.getClas());
            irisClassifiedRecord.setPredictedClass(diabeteRecord.getPredictedClass());

            try {
                return new KeyValue<>(irisClassifiedRecord, classifyDiabete(v));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

       // write the classified records back to Kafka
        diabeteStreamClassified.to("pridection");

        // create one-minute tumbling windows containing actual/predicted counts
      KTable<Windowed<DiabeteClassifiedRecord>, Long> irisClassifiedWindowCounts = diabeteStreamClassified
                .selectKey((k, v) -> k)
                .groupByKey()
                .windowedBy(TimeWindows.of(Duration.ofMinutes(1)))
                .count(Materialized.as("page-count"));

        // write the windowed counts to iris-classified-window topic
       irisClassifiedWindowCounts.toStream().map((k, v) -> {
            DiabeteClassifiedWindowRecord irisClassifiedWindowRecord = new DiabeteClassifiedWindowRecord();
            irisClassifiedWindowRecord.setClas(k.key().getClas());
            irisClassifiedWindowRecord.setPredictedClass(k.key().getPredictedClass());
            irisClassifiedWindowRecord.setStartMs(k.window().start());
            irisClassifiedWindowRecord.setEndMs(k.window().end());
            irisClassifiedWindowRecord.setCount(v);
            return new KeyValue(null, irisClassifiedWindowRecord);
        }).to("diabete-result");

        //TODO: add topic with correct classification percentage

        // run it
        final Topology topology = builder.build();

        // show topology
        LOG.info(topology.describe().toString());

        final KafkaStreams streams = new KafkaStreams(topology, props);
        streams.cleanUp();
        streams.start();

        // Add shutdown hook to respond to SIGTERM and gracefully close Kafka Streams
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));

    }

    private DiabeteRecord classifyDiabete(DiabeteRecord irisRecord) throws Exception {


           /**************************/

           /******************************/

            ModelClassifier cls = new ModelClassifier();
            String classname =cls.classifiy(cls.createInstance(irisRecord.getPreg(),irisRecord.getPlas(),irisRecord.getPres(),irisRecord.getSkin(),irisRecord.getInsu(),irisRecord.getMass(),irisRecord.getPedi(),irisRecord.getAge()), MODElPATH);
            System.out.println("\n The class name for the instance is " +classname);
            irisRecord.setPredictedClass(classname);
            //irisRecord.setClas(classname);
        System.out.println("the predected class is ++++++++++++++++++++++"+irisRecord.getClas());

        return irisRecord;

    }

    private void createTopics(Properties props){

        AdminClient client = AdminClient.create(props);

        List<NewTopic> topics = new ArrayList<>();
        for (String topicName : ((String) props.get("topics")).split(",")){
            NewTopic topic = new NewTopic(topicName, 1, (short) 3);
            topics.add(topic);
        }

        client.createTopics(topics);
        client.close();

    }

}
