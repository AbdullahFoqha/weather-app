package com.example.weather.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;

public class ApiClient {
    private static volatile ApiClient mInstance;
    public final static String API_KEY = "4fddc35ded904100b0b200652180210";
    private ApiService mApiService;
    private static final int TIMEOUT = 40;
    private Gson mGson;

    private ApiClient() {
        mGson = new GsonBuilder().create();
        OkHttpClient okClient = getOkHttpClient();
        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());
        Retrofit retrofit = new Retrofit.Builder()
                .client(okClient)
                .baseUrl("http://api.apixu.com/")
                .addConverterFactory(GsonConverterFactory.create(mGson))
                .addCallAdapterFactory(rxAdapter).build();
        mApiService = retrofit.create(ApiService.class);
    }

    public static ApiClient getInstance() {
        if (mInstance == null) {
            synchronized (ApiClient.class) {
                if (mInstance == null) {
                    mInstance = new ApiClient();
                }
            }
        }
        return mInstance;
    }


    public ApiService getApiService() {
        return mApiService;
    }

    private OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        okHttpClientBuilder.connectTimeout(TIMEOUT, TimeUnit.SECONDS);
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpClientBuilder.addInterceptor(logging);

        return okHttpClientBuilder.build();
    }

    public Gson getGson() {
        return mGson;
    }

}
