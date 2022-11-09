package io.woolford.kstreams.h2o.example;

import weka.classifiers.Classifier;
import weka.core.Debug;
import weka.core.Instances;
import weka.core.converters.CSVSaver;


import java.io.File;

//@Component
public class TrainModel {
    public static final String MODElPATH = "C://data/modelDiabetes.bin";
    public static final String DATASETPATH = "/data/data.csv";
   // @PostConstruct
    void train() throws Exception {

        ModelGenerator mg = new ModelGenerator();

        Instances dataset = mg.loadDataset(DATASETPATH);


        // divide dataset to train dataset 80% and test dataset 20%
        int trainSize = (int) Math.round(dataset.numInstances() * 0.8);
        int testSize = dataset.numInstances() - trainSize;

        dataset.randomize(new Debug.Random(1));// if you comment this line the accuracy of the model will be droped from 96.6% to 80%

        //Normalize dataset
        //filter.setInputFormat(dataset);


        Instances traindataset = new Instances(dataset, 0, trainSize);
        Instances testdataset = new Instances(dataset, trainSize, testSize);
        CSVSaver saver = new CSVSaver();
        saver.setInstances( testdataset);
        saver.setFile(new File("C://data/testDiabetes.csv"));
        saver.writeBatch();
        // build classifier with train dataset
        // MultilayerPerceptron ann = (MultilayerPerceptron) mg.buildClassifier(traindataset);
        Classifier ann = new weka.classifiers.functions.Logistic();

        ann.buildClassifier( traindataset );

        // Evaluate classifier with test dataset
        String evalsummary = mg.evaluateModel(ann, traindataset, testdataset);
        System.out.println("Evaluation: " + evalsummary);

        //Save model
        mg.saveModel(ann, MODElPATH);
        ModelClassifier cls = new ModelClassifier();
        String classname =cls.classifiy(cls.createInstance(1,81,74,41,57,46.3,1.096,130), MODElPATH);
        System.out.println("\n The predicted class is  " +classname);
    }
}
