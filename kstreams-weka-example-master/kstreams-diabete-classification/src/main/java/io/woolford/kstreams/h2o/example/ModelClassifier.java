package io.woolford.kstreams.h2o.example;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.classifiers.Classifier;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;
import weka.core.SerializationHelper;

/**
 * Website: http://www.emaraic.com
 * Github link: https://github.com/emara-geek/weka-example
 */
public class ModelClassifier {


    private Attribute  preg;
    private Attribute  plas;
    private Attribute  pres;
    private Attribute  skin;
    private Attribute  insu;
    private Attribute  mass;
    private Attribute  pedi;
    private Attribute  age;
 

    private ArrayList<Attribute> attributes;
    private ArrayList<String> classVal;
    private Instances dataRaw;


    public ModelClassifier() {
    	preg = new Attribute("preg");
    	plas = new Attribute("plas");
    	pres = new Attribute("pres");
    	skin = new Attribute("skin");
    	insu = new Attribute("insu");
    	mass = new Attribute("mass");
    	pedi = new Attribute("pedi");
    	age = new Attribute("age");
        attributes = new ArrayList<Attribute>();
        classVal = new ArrayList<String>();
        classVal.add("tested_positive");
        classVal.add("tested_negative");
       

        attributes.add(preg);
        attributes.add(plas);
        attributes.add(pres);
        attributes.add(skin);
        attributes.add(insu);
        attributes.add(mass);
        attributes.add(pedi);
        attributes.add(age);

        attributes.add(new Attribute("clas", classVal));
        dataRaw = new Instances("TestInstances", attributes, 0);
        dataRaw.setClassIndex(dataRaw.numAttributes() - 1);
    }

    
    public Instances createInstance(   int preg, int plas, int pres, int skin, int insu, Double mass, Double pedi, int age) {
        dataRaw.clear();
        double[] instanceValue1 = new double[]{preg,  plas,pres,skin,insu, mass,pedi,age};
        dataRaw.add(new DenseInstance(1.0, instanceValue1));
        return dataRaw;
    }


    public String classifiy(Instances insts, String path) {
        String result = "Not classified!!";
        Classifier cls = null;
        try {
            cls = (Classifier) SerializationHelper.read(path);
            result = classVal.get((int) cls.classifyInstance(insts.firstInstance()));
        } catch (Exception ex) {
            Logger.getLogger(ModelClassifier.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }


    public Instances getInstance() {
        return dataRaw;
    }
    

}
