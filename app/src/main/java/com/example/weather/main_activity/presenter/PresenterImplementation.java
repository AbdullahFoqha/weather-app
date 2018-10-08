package com.example.weather.main_activity.presenter;

import com.example.weather.api.api_models.WeatherInfo;

public class PresenterImplementation implements MainContract.Presenter, MainContract.Weather.OnFinishedListener {

    private MainContract.MainView mainView;
    private MainContract.Weather weather;

    public PresenterImplementation(MainContract.MainView mainView, MainContract.Weather weather) {
        this.mainView = mainView;
        this.weather = weather;
    }

    @Override
    public void requestDataFromServer(String cityName) {
        weather.getWeatherInfo(this, cityName);
    }

    @Override
    public void loadFromDB(String cityName) {
        mainView.loadFromDB(cityName);
    }

    @Override
    public void showDialog() {
        if (mainView != null) {
            mainView.showDialog();
        }
    }

    @Override
    public void hideDialog() {
        if (mainView != null) {
            mainView.hideDialog();
        }
    }


    @Override
    public void onFinished(WeatherInfo weatherInfo) {
        if (mainView != null) {
            mainView.onResponse(weatherInfo);
        }
    }

    @Override
    public void onFailure(Throwable t) {

    }
}
