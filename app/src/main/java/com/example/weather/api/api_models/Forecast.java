package com.example.weather.api.api_models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Forecast {
    @SerializedName("forecastday")
    private ArrayList<ForecastDay> forecastDays;

    public ArrayList<ForecastDay> getForecastDays() {
        return forecastDays;
    }

    public void setForecastDays(ArrayList<ForecastDay> forecastDays) {
        this.forecastDays = forecastDays;
    }
}
