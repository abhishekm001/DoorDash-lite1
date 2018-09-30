package com.doordash.lite;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.doordash.adapters.RestaurantRecyclerViewAdapter;
import com.doordash.customviews.MyDividerItemDecoration;
import com.doordash.databeans.Restaurant;
import com.doordash.services.DoorDashRetroClient;

import java.util.List;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName();

    private Subscription subscription;
    private RecyclerView recyclerView;
    private RestaurantRecyclerViewAdapter restaurantRecyclerViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.restaurant_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));

        searchRestaurants();
    }

    private void searchRestaurants() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Searching..");
        progressDialog.show();
        subscription = DoorDashRetroClient.getInstance()
                .searchRestaurants("37.422740", "-122.139956", 0, 50)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Restaurant>>() {
                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "completed");
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "error");

                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onNext(List<Restaurant> restaurants) {
                        Log.i(TAG, "success: " + restaurants.size());

                        if (restaurantRecyclerViewAdapter == null) {
                            restaurantRecyclerViewAdapter = new RestaurantRecyclerViewAdapter(restaurants);
                            recyclerView.setAdapter(restaurantRecyclerViewAdapter);
                            MyDividerItemDecoration myDividerItemDecoration = new MyDividerItemDecoration(getBaseContext().getResources().getDrawable(R.drawable
                                    .divider));
                            recyclerView.addItemDecoration(myDividerItemDecoration);

                        } else {
                            restaurantRecyclerViewAdapter.updateRestaurantList(restaurants);
                        }

                    }
                });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
