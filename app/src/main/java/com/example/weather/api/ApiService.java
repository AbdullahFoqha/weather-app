package com.example.weather.api;



import com.example.weather.api.api_models.WeatherInfo;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface ApiService {

    @GET("v1/forecast.json")
    Observable<WeatherInfo> getWeatherData(@Query("key") String key,
                                           @Query("q") String cityName,
                                           @Query("days") int days);

}
