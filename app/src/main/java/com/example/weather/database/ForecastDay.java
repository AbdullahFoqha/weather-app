package com.example.weather.database;


import io.realm.RealmObject;

public class ForecastDay extends RealmObject {
    private String date;
    private Day day;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }
}
