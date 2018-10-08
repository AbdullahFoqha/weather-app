package com.example.weather.database;

import io.realm.RealmList;
import io.realm.RealmObject;

public class WeatherInfo extends RealmObject {
    private Location location;
    private Current current;
    private RealmList<ForecastDay> forecastDays;

    public RealmList<ForecastDay> getForecastDays() {
        return forecastDays;
    }

    public void setForecastDays(RealmList<ForecastDay> forecastDays) {
        this.forecastDays = forecastDays;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Current getCurrent() {
        return current;
    }

    public void setCurrent(Current current) {
        this.current = current;
    }

}
