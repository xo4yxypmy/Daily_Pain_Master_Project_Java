package com.fernfog.dailyPain.objects;

public class Symptome {
    String startOfPain;
    String endOfPain;
    String nameOfCategory;
    float painLvl;

    public Symptome(String startOfPain, String endOfPain,  String nameOfCategory, float painLvl) {
        this.startOfPain = startOfPain;
        this.endOfPain = endOfPain;
        this.nameOfCategory = nameOfCategory;
        this.painLvl = painLvl;
    }

    public String getStartOfPain() {
        return startOfPain;
    }

    public String getEndOfPain() {
        return endOfPain;
    }

    public String getNameOfCategory() {
        return nameOfCategory;
    }

    public float getPainLvl() {
        return painLvl;
    }
}
