package com.example.weather.database;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Cities extends RealmObject {
    @PrimaryKey
    private String cityName;
    private WeatherInfo weatherInfo;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public WeatherInfo getWeatherInfo() {
        return weatherInfo;
    }

    public void setWeatherInfo(WeatherInfo weatherInfo) {
        this.weatherInfo = weatherInfo;
    }
}
