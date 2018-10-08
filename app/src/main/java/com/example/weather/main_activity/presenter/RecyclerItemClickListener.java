package com.example.weather.main_activity.presenter;

import com.example.weather.api.api_models.ForecastDay;

public interface RecyclerItemClickListener {
    void onItemClick(ForecastDay forecastDay);
}
