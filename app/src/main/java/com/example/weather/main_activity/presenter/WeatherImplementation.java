package com.example.weather.main_activity.presenter;

import com.example.weather.api.ApiClient;
import com.example.weather.api.api_models.WeatherInfo;
import com.example.weather.database.Cities;
import com.example.weather.database.Condition;
import com.example.weather.database.Current;
import com.example.weather.database.Day;
import com.example.weather.database.ForecastDay;
import com.example.weather.database.Location;

import io.realm.Realm;
import io.realm.RealmList;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

public class WeatherImplementation implements MainContract.Weather {

    @Override
    public void getWeatherInfo(final OnFinishedListener onFinishedListener, String cityName) {

        ApiClient.getInstance().getApiService().getWeatherData(ApiClient.API_KEY, cityName, 7).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<WeatherInfo>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                onFinishedListener.onFailure(e);
            }

            @Override
            public void onNext(WeatherInfo weatherInfo) {

                Realm realm = Realm.getDefaultInstance();

                if (!realm.isInTransaction()) {
                    realm.beginTransaction();
                }

                com.example.weather.database.WeatherInfo dbWeatherInfo = new com.example.weather.database.WeatherInfo();

                Current current = new Current();
                current.setCloud(weatherInfo.getCurrent().getCloud());
                current.setCurrentTemperature(weatherInfo.getCurrent().getCurrentTemperature());
                current.setHumidity(weatherInfo.getCurrent().getHumidity());
                current.setWindSpeed(weatherInfo.getCurrent().getWindSpeed());
                current.setIs_day(weatherInfo.getCurrent().getIs_day());

                Condition condition = new Condition();
                condition.setCode(weatherInfo.getCurrent().getCondition().getCode());
                condition.setIcon(weatherInfo.getCurrent().getCondition().getIcon());
                condition.setTemperatureCondition(weatherInfo.getCurrent().getCondition().getTemperatureCondition());

                current.setCondition(condition);

                dbWeatherInfo.setCurrent(current);

                Location location = new Location();
                location.setCityName(weatherInfo.getLocation().getCityName());
                location.setCountry(weatherInfo.getLocation().getCountry());
                location.setLocaltime(weatherInfo.getLocation().getLocaltime());

                dbWeatherInfo.setLocation(location);

                RealmList<ForecastDay> forecastDays = new RealmList<>();
                for (com.example.weather.api.api_models.ForecastDay forecastDay : weatherInfo.getForecast().getForecastDays()) {

                    ForecastDay forecastDay1 = new ForecastDay();

                    forecastDay1.setDate(forecastDay.getDate());

                    Day day = new Day();
                    day.setAvgHumidity(forecastDay.getDay().getAvgHumidity());
                    day.setAvgTemperature(forecastDay.getDay().getAvgTemperature());
                    day.setAvgVisibility(forecastDay.getDay().getAvgVisibility());
                    day.setMaxTemperature(forecastDay.getDay().getMaxTemperature());
                    day.setMaxWindSpeed(forecastDay.getDay().getMaxWindSpeed());
                    day.setMinTemperature(forecastDay.getDay().getMinTemperature());

                    Condition condition1 = new Condition();
                    condition1.setCode(forecastDay.getDay().getCondition().getCode());
                    condition1.setIcon(forecastDay.getDay().getCondition().getIcon());
                    condition1.setTemperatureCondition(forecastDay.getDay().getCondition().getTemperatureCondition());

                    day.setCondition(condition1);

                    forecastDay1.setDay(day);

                    forecastDays.add(forecastDay1);
                }
                dbWeatherInfo.setForecastDays(forecastDays);

                Cities cities = new Cities();
                cities.setWeatherInfo(dbWeatherInfo);
                cities.setCityName(weatherInfo.getLocation().getCityName());

                realm.insertOrUpdate(cities);

                realm.commitTransaction();
                realm.close();

                onFinishedListener.onFinished(weatherInfo);

            }

        });

    }
}
