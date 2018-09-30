package com.doordash.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.doordash.databeans.Restaurant;
import com.doordash.lite.R;
import com.doordash.viewholders.RestuarantViewHolder;

import java.util.List;

/**
 * Created by amaheshwari on 9/29/2018.
 */

public class RestaurantRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<Restaurant> restaurantList;

    public RestaurantRecyclerViewAdapter(List<Restaurant> restaurantList) {
        this.restaurantList = restaurantList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurants_view, parent, false);
        return new RestuarantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((RestuarantViewHolder) holder).bind(restaurantList.get(position));
    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }


    public void updateRestaurantList(List<Restaurant> newRestaurantList) {

        if (newRestaurantList == null) return;

        if (restaurantList != null) {
            restaurantList.clear();
            restaurantList.addAll(newRestaurantList);
        } else {
            restaurantList = newRestaurantList;
        }
        notifyDataSetChanged();

    }

}
