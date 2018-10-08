package com.example.weather.main_activity.presenter;

import com.example.weather.api.api_models.WeatherInfo;

public interface MainContract {

    interface MainView {
        void onResponse(WeatherInfo weatherInfo);

        void showDialog();

        void hideDialog();

        void loadFromDB(String cityName);
    }

    interface Weather {

        interface OnFinishedListener {
            void onFinished(WeatherInfo weatherInfo);

            void onFailure(Throwable t);
        }

        void getWeatherInfo(OnFinishedListener onFinishedListener, String cityName);
    }

    interface Presenter {

        void requestDataFromServer(String cityName);

        void loadFromDB(String cityName);

        void showDialog();

        void hideDialog();
    }

}
