package com.fernfog.dailyPain.objects;

public class Symptome {
    String startOfPainDate;
    String startOfPainTime;
    String endOfPainDate;
    String endOfPainTime;
    String nameOfCategory;
    String additional;
    float painLvl;

    public Symptome(String startOfPainDate, String startOfPainTime, String endOfPainDate, String endOfPainTime, String nameOfCategory, float painLvl, String additional) {
        this.startOfPainDate = startOfPainDate;
        this.startOfPainTime = startOfPainTime;
        this.endOfPainDate = endOfPainDate;
        this.endOfPainTime = endOfPainTime;
        this.nameOfCategory = nameOfCategory;
        this.painLvl = painLvl;
        this.additional = additional;
    }

    public String getAdditional() {
        return additional;
    }

    public String getStartOfPainDate() {
        return startOfPainDate;
    }

    public String getStartOfPainTime() {
        return startOfPainTime;
    }

    public String getEndOfPainDate() {
        return endOfPainDate;
    }

    public String getEndOfPainTime() {
        return endOfPainTime;
    }

    public String getNameOfCategory() {
        return nameOfCategory;
    }

    public float getPainLvl() {
        return painLvl;
    }

}
