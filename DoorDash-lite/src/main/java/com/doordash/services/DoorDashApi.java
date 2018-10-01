package com.doordash.services;

import com.doordash.databeans.Restaurant;

import java.util.List;
import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Service Apis
 */
public interface DoorDashApi {

    // https://api.doordash.com/v2/restaurant/?lat=37.422740&lng=-122.139956
    @GET("restaurant")
    Observable<List<Restaurant>> searchRestaurants(@QueryMap Map<String, String> params);

    // https://api.doordash.com/v2/restaurant/30
    @GET("restaurant/{id}")
    Observable<Restaurant> getRestaurant(@Path("id") int id);

}
