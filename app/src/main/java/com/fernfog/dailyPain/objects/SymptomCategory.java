package com.fernfog.dailyPain.objects;

public class SymptomCategory {
    private String name;
    private String color;

    public SymptomCategory(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }
}