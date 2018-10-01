package com.doordash.services;

import com.doordash.databeans.Restaurant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Client to initiate service call
 */
public class DoorDashRetroClient {

    private static final String TAG = "DoorDashRetroClient";

    private static final String baseUrl = "https://api.doordash.com/v2/";

    // singleton instance
    private static DoorDashRetroClient instance;

    // instance of DoorDashApi
    private DoorDashApi doorDashApi;

    /**
     * Private constructor
     * should be accessed only by getInstance method..
     */
    private DoorDashRetroClient() {

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        doorDashApi = retrofit.create(DoorDashApi.class);
    }

    /**
     * Singleton instance of {@link DoorDashRetroClient}
     *
     * @return instance of {@link DoorDashRetroClient}
     */
    public static DoorDashRetroClient getInstance() {
        if (instance == null) {
            instance = new DoorDashRetroClient();
        }
        return instance;
    }


    // Expose call for apis

    public Observable<List<Restaurant>> searchRestaurants(String latitude, String longitude, int offset, int limit) {
        Map<String, String> map = new HashMap<>();
        map.put("lat", latitude);
        map.put("lng", longitude);
        map.put("offset", String.valueOf(offset));
        map.put("limit", String.valueOf(limit));
        return doorDashApi.searchRestaurants(map);
    }
}
