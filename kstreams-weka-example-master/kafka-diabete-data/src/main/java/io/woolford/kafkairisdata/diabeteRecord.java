package io.woolford.kafkairisdata;

public class diabeteRecord {

    private int preg;
    private int plas;
    private int pres;
    private int skin;
    private String insu;
    private Double mass;
    private Double pedi;
    private int age;
    private String clas;

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

    public String getInsu() {
        return insu;
    }

    public void setInsu(String insu) {
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

    @Override
    public String toString() {
        return "diabeteRecord{" +
                "preg=" + preg +
                ", plas=" + plas +
                ", pres=" + pres +
                ", skin=" + skin +
                ", insu='" + insu + '\'' +
                ", mass=" + mass +
                ", pedi=" + pedi +
                ", age=" + age +
                ", clas='" + clas + '\'' +
                '}';
    }
}
