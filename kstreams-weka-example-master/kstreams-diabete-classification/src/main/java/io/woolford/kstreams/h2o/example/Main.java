package io.woolford.kstreams.h2o.example;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class Main {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Main.class, args);
     //TrainModel trainModel = new TrainModel();
    // trainModel.train();
       DiabeteClassifier  Classifier = new DiabeteClassifier();
        Classifier.run();
    }

}
