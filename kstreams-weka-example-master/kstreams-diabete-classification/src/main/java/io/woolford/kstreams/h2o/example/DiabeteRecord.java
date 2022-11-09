package io.woolford.kstreams.h2o.example;

public class DiabeteRecord {

    private int preg;
    private int plas;
    private int pres;
    private int skin;
    private int insu;
    private Double mass;
    private Double pedi;
    private int age;
    private String clas;
    private String predictedClass;

    public int getPreg() {
        return preg;
    }

    public void setPreg(int preg) {
        this.preg = preg;
    }

    public int getPlas() {
        return plas;
    }

    public void setPlas(int plas) {
        this.plas = plas;
    }

    public int getPres() {
        return pres;
    }

    public void setPres(int pres) {
        this.pres = pres;
    }

    public int getSkin() {
        return skin;
    }

    public void setSkin(int skin) {
        this.skin = skin;
    }

    public int getInsu() {
        return insu;
    }

    public void setInsu(int insu) {
        this.insu = insu;
    }

    public Double getMass() {
        return mass;
    }

    public void setMass(Double mass) {
        this.mass = mass;
    }

    public Double getPedi() {
        return pedi;
    }

    public void setPedi(Double pedi) {
        this.pedi = pedi;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getClas() {
        return clas;
    }

    public void setClas(String clas) {
        this.clas = clas;
    }

    public String getPredictedClass() {
        return predictedClass;
    }

    public void setPredictedClass(String predictedClass) {
        this.predictedClass = predictedClass;
    }
}
