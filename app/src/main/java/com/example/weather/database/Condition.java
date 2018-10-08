package com.example.weather.database;

import io.realm.RealmObject;

public class Condition extends RealmObject {
    private String temperatureCondition;
    private String icon;
    private int code;

    public String getTemperatureCondition() {
        return temperatureCondition;
    }

    public void setTemperatureCondition(String temperatureCondition) {
        this.temperatureCondition = temperatureCondition;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
