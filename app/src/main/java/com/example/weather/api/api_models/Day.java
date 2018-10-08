package com.example.weather.api.api_models;

import com.google.gson.annotations.SerializedName;

public class Day {

    public int getAvgHumidity() {
        return avgHumidity;
    }

    public void setAvgHumidity(int avgHumidity) {
        this.avgHumidity = avgHumidity;
    }

    @SerializedName("avghumidity")
    private int avgHumidity;

    @SerializedName("maxtemp_c")
    private Double maxTemperature;

    @SerializedName("mintemp_c")
    private Double minTemperature;

    @SerializedName("avgtemp_c")
    private Double avgTemperature;

    private Condition condition;

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public Double getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(Double maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public Double getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(Double minTemperature) {
        this.minTemperature = minTemperature;
    }

    public Double getAvgTemperature() {
        return avgTemperature;
    }

    public void setAvgTemperature(Double avgTemperature) {
        this.avgTemperature = avgTemperature;
    }

    public Double getMaxWindSpeed() {
        return maxWindSpeed;
    }

    public void setMaxWindSpeed(Double maxWindSpeed) {
        this.maxWindSpeed = maxWindSpeed;
    }

    public Double getAvgVisibility() {
        return avgVisibility;
    }

    public void setAvgVisibility(Double avgVisibility) {
        this.avgVisibility = avgVisibility;
    }

    @SerializedName("maxwind_kph")
    private Double maxWindSpeed;

    @SerializedName("avgvis_km")
    private Double avgVisibility;


}
