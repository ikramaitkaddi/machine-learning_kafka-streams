package io.woolford.kstreams.h2o.example;

public class DiabeteClassifiedRecord {

    private String clas;

    private String predictedClass;
    private boolean match;

    public String getClas() {
        return clas;
    }

    public void setClas(String clas) {
        this.clas = clas;
        updateMatch();
    }

    public String getPredictedClass() {
        return predictedClass;
    }

    public void setPredictedClass(String predictedClass) {
        this.predictedClass = predictedClass;
        updateMatch();
    }

    public boolean isMatch() {
        return match;
    }

    public void setMatch(boolean match) {
        this.match = match;
    }

    private void updateMatch(){
        if (this.clas.equals(this.predictedClass)){
            this.match = true;
        } else {
            this.match = false;
        }
    }

    @Override
    public String toString() {
        return "IrisClassifiedRecord{" +
                "clas='" + clas + '\'' +
                ", predictedSpecies='" + predictedClass + '\'' +
                ", match=" + match +
                '}';
    }

}
