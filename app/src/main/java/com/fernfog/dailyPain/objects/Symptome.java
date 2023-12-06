package com.fernfog.dailyPain.objects;

public class Symptome {
    String startOfPainDate;
    String startOfPainTime;
    String endOfPainDate;
    String endOfPainTime;
    String nameOfCategory;
    float painLvl;

    public Symptome(String startOfPainDate, String startOfPainTime, String endOfPainDate, String endOfPainTime, String nameOfCategory, float painLvl) {
        this.startOfPainDate = startOfPainDate;
        this.startOfPainTime = startOfPainTime;
        this.endOfPainDate = endOfPainDate;
        this.endOfPainTime = endOfPainTime;
        this.nameOfCategory = nameOfCategory;
        this.painLvl = painLvl;
    }

    public String getStartOfPainDate() {
        return startOfPainDate;
    }

    public void setStartOfPainDate(String startOfPainDate) {
        this.startOfPainDate = startOfPainDate;
    }

    public String getStartOfPainTime() {
        return startOfPainTime;
    }

    public void setStartOfPainTime(String startOfPainTime) {
        this.startOfPainTime = startOfPainTime;
    }

    public String getEndOfPainDate() {
        return endOfPainDate;
    }

    public void setEndOfPainDate(String endOfPainDate) {
        this.endOfPainDate = endOfPainDate;
    }

    public String getEndOfPainTime() {
        return endOfPainTime;
    }

    public void setEndOfPainTime(String endOfPainTime) {
        this.endOfPainTime = endOfPainTime;
    }

    public String getNameOfCategory() {
        return nameOfCategory;
    }

    public void setNameOfCategory(String nameOfCategory) {
        this.nameOfCategory = nameOfCategory;
    }

    public float getPainLvl() {
        return painLvl;
    }

    public void setPainLvl(float painLvl) {
        this.painLvl = painLvl;
    }
}
