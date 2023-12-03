package com.fernfog.dailyPain.objects;

public class User {
    private String firstName;
    private String lastName;
    private String patronymic;
    private String birthday;

    public User(String firstName, String lastName, String patronymic, String birthday) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.birthday = birthday;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public String getBirthday() {
        return birthday;
    }
}
