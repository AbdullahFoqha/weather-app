package com.example.weather.api.api_models;

import com.google.gson.annotations.SerializedName;

public class Location {
    @SerializedName("name")
    private String cityName;
    private String country;
    private String localtime;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLocaltime() {
        return localtime;
    }

    public void setLocaltime(String localtime) {
        this.localtime = localtime;
    }
}
