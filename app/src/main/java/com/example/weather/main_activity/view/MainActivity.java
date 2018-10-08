package com.example.weather.main_activity.view;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weather.R;
import com.example.weather.adapters.DayAdapter;
import com.example.weather.api.api_models.Condition;
import com.example.weather.api.api_models.Day;
import com.example.weather.api.api_models.ForecastDay;
import com.example.weather.api.api_models.WeatherInfo;
import com.example.weather.database.Cities;
import com.example.weather.main_activity.presenter.MainContract;
import com.example.weather.main_activity.presenter.PresenterImplementation;
import com.example.weather.main_activity.presenter.RecyclerItemClickListener;
import com.example.weather.main_activity.presenter.WeatherImplementation;
import com.example.weather.utilities.SharedPreferencesManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

public class MainActivity extends AppCompatActivity implements MainContract.MainView {

    @BindView(R.id.weatherImageView)
    ImageView weatherImageView;
    @BindView(R.id.weatherTextTextView)
    TextView weatherTextTextView;
    @BindView(R.id.weatherTemperatureTextView)
    TextView weatherTemperatureTextView;
    @BindView(R.id.cityImage)
    ImageView cityImage;
    @BindView(R.id.cityCountryTextView)
    TextView cityCountryTextView;
    @BindView(R.id.changeCityImageView)
    ImageView changeCityImageView;
    @BindView(R.id.forecastDayRecyclerView)
    RecyclerView forecastDayRecyclerView;
    @BindView(R.id.weatherDetailsImage)
    ImageView weatherDetailsImage;
    @BindView(R.id.dummyImage)
    ImageView dummyImage;
    @BindView(R.id.humidityTextView)
    TextView humidityTextView;
    @BindView(R.id.windSpeedTextView)
    TextView windSpeedTextView;
    @BindView(R.id.visibilityTextView)
    TextView visibilityTextView;

    private MainContract.Presenter presenter;
    private AlertDialog alertDialog;
    private Realm realm;
    private String savedCityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initRecyclerView();

        initDialog();

        realm = Realm.getDefaultInstance();

        if (!realm.isInTransaction()) {
            realm.beginTransaction();
        }

        if (!isNetworkAvailable(this))
            Toast.makeText(this, "There is no Internet Connection", Toast.LENGTH_SHORT).show();

        presenter = new PresenterImplementation(this, new WeatherImplementation());

        loadData(SharedPreferencesManager.getString(this, SharedPreferencesManager.LAST_SELECTED_CITY, "Amman"));

        changeCityImageView.setOnClickListener(ChangeCityOnClickListener);
    }

    private void loadData(String cityName) {
        SharedPreferencesManager.putString(this, SharedPreferencesManager.LAST_SELECTED_CITY, cityName);
        if (realm.where(com.example.weather.database.WeatherInfo.class).findAll().size() > 0)
            presenter.loadFromDB(cityName);
        if (isNetworkAvailable(this))
            presenter.requestDataFromServer(cityName);
        else
            Toast.makeText(this, "There is no Internet Connection", Toast.LENGTH_LONG).show();


    }

    private void initDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.choseCity).setItems(R.array.cities_array, (dialog, which) -> loadData(which == 0 ? getString(R.string.amman) : which == 1 ? getString(R.string.irbid) : getString(R.string.aqaba)));
        alertDialog = builder.create();
    }

    private void initRecyclerView() {
        forecastDayRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
    }

    View.OnClickListener ChangeCityOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            presenter.showDialog();
        }
    };

    private RecyclerItemClickListener recyclerItemClickListener = new RecyclerItemClickListener() {
        @Override
        public void onItemClick(ForecastDay forecastDay) {
            humidityTextView.setText(String.valueOf(forecastDay.getDay().getAvgHumidity()).concat(getString(R.string.percentage)));

            windSpeedTextView.setText(String.format(Locale.ENGLISH, getString(R.string.double_format), forecastDay.getDay().getMaxWindSpeed()).concat(getString(R.string.kph)));

            visibilityTextView.setText(String.format(Locale.ENGLISH, getString(R.string.double_format), forecastDay.getDay().getAvgVisibility()).concat(getString(R.string.km)));

            Picasso.get().load(getString(R.string.http).concat(forecastDay.getDay().getCondition().getIcon())).into(weatherImageView);

            weatherTextTextView.setText(forecastDay.getDay().getCondition().getTemperatureCondition());

            weatherTemperatureTextView.setText(String.format(Locale.ENGLISH, getString(R.string.celsius), String.format(Locale.ENGLISH, getString(R.string.double_format), forecastDay.getDay().getAvgTemperature())));
        }
    };

    @Override
    public void onResponse(WeatherInfo weatherInfo) {

        fillFieldsWithData(weatherInfo);

        presenter.hideDialog();

    }

    @Override
    public void showDialog() {
        alertDialog.show();
    }

    @Override
    public void hideDialog() {
        alertDialog.dismiss();
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    @Override
    public void loadFromDB(String cityName) {
        Cities cities = realm.where(Cities.class).contains("cityName", cityName).findFirst();
        if (cities != null) {

            com.example.weather.database.WeatherInfo weatherInfo = cities.getWeatherInfo();

            Picasso.get().load(getString(R.string.http).concat(weatherInfo.getCurrent().getCondition().getIcon())).into(weatherImageView);

            weatherTextTextView.setText(weatherInfo.getCurrent().getCondition().getTemperatureCondition());

            cityCountryTextView.setText(weatherInfo.getLocation().getCityName().concat(getString(R.string.comma)).concat(weatherInfo.getLocation().getCountry()));

            weatherTemperatureTextView.setText(String.format(Locale.ENGLISH, getString(R.string.celsius), String.format(Locale.ENGLISH, getString(R.string.double_format), weatherInfo.getCurrent().getCurrentTemperature())));

            humidityTextView.setText(String.valueOf(weatherInfo.getCurrent().getHumidity()).concat(getString(R.string.percentage)));

            windSpeedTextView.setText(String.format(Locale.ENGLISH, getString(R.string.double_format), weatherInfo.getCurrent().getWindSpeed()).concat(getString(R.string.kph)));

            visibilityTextView.setText(String.format(Locale.ENGLISH, getString(R.string.double_format), weatherInfo.getCurrent().getVisibility()).concat(getString(R.string.km)));

            ArrayList<ForecastDay> forecastDays = new ArrayList<>();

            for (com.example.weather.database.ForecastDay forecastDay : weatherInfo.getForecastDays()) {

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

            forecastDayRecyclerView.setAdapter(new DayAdapter(MainActivity.this, forecastDays, recyclerItemClickListener));

        }
    }

    void fillFieldsWithData(WeatherInfo weatherInfo) {

        Picasso.get().load(getString(R.string.http).concat(weatherInfo.getCurrent().getCondition().getIcon())).into(weatherImageView);

        weatherTextTextView.setText(weatherInfo.getCurrent().getCondition().getTemperatureCondition());

        cityCountryTextView.setText(weatherInfo.getLocation().getCityName().concat(getString(R.string.comma)).concat(weatherInfo.getLocation().getCountry()));

        weatherTemperatureTextView.setText(String.format(Locale.ENGLISH, getString(R.string.celsius), String.format(Locale.ENGLISH, getString(R.string.double_format), weatherInfo.getCurrent().getCurrentTemperature())));

        humidityTextView.setText(String.valueOf(weatherInfo.getCurrent().getHumidity()).concat(getString(R.string.percentage)));

        windSpeedTextView.setText(String.format(Locale.ENGLISH, getString(R.string.double_format), weatherInfo.getCurrent().getWindSpeed()).concat(getString(R.string.kph)));

        visibilityTextView.setText(String.format(Locale.ENGLISH, getString(R.string.double_format), weatherInfo.getCurrent().getVisibility()).concat(getString(R.string.km)));

        forecastDayRecyclerView.setAdapter(new DayAdapter(MainActivity.this, weatherInfo.getForecast().getForecastDays(), recyclerItemClickListener));

    }

}
