package com.doordash.lite.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.doordash.adapters.RestaurantRecyclerViewAdapter;
import com.doordash.customviews.MyDividerItemDecoration;
import com.doordash.databeans.Restaurant;
import com.doordash.lite.R;
import com.doordash.lite.fragments.RetainedFragment;
import com.doordash.services.DoorDashRetroClient;
import com.doordash.utils.CommonUtils;

import java.io.Serializable;
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
    private List<Restaurant> restaurantList;
    private View noRestaurantFound;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.restaurant_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));

        noRestaurantFound = findViewById(R.id.no_restaurant_found);

        // Fetch state from dummy fragment which was set onSaveInstance
        savedInstanceState = RetainedFragment.getInstance(this.getSupportFragmentManager()).popData();

        // retrieve data from save state instead of calling service
        if (savedInstanceState != null) {

            if (savedInstanceState.containsKey("restaurant_list")) {
                restaurantList = (List<Restaurant>) savedInstanceState.get("restaurant_list");
                populateRecyclerView();
            }
        } else {
            // call service to get restaurant list
            searchRestaurants();
        }
    }

    // Service to fetch restaurant details
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
                        if (restaurantList != null) {
                            restaurantList.clear();
                        }
                        checkRestaurantListEmpty();

                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onNext(List<Restaurant> restaurants) {
                        Log.i(TAG, "success: " + restaurants.size());

                        restaurantList = restaurants;

                        checkRestaurantListEmpty();
                        populateRecyclerView();

                    }
                });

    }

    // check if restaurant list is empty
    // if empty dispplay not found message
    private void checkRestaurantListEmpty() {

        if (CommonUtils.isNullOrEmpty(restaurantList)) {

            recyclerView.setVisibility(View.GONE);
            noRestaurantFound.setVisibility(View.VISIBLE);

        } else {
            recyclerView.setVisibility(View.VISIBLE);
            noRestaurantFound.setVisibility(View.GONE);

        }
    }

    // intialize recycler view adapter
    private void populateRecyclerView() {

        if (restaurantRecyclerViewAdapter == null) {
            restaurantRecyclerViewAdapter = new RestaurantRecyclerViewAdapter(restaurantList);
            recyclerView.setAdapter(restaurantRecyclerViewAdapter);
            MyDividerItemDecoration myDividerItemDecoration = new MyDividerItemDecoration(getBaseContext().getResources().getDrawable(R.drawable
                    .divider));
            recyclerView.addItemDecoration(myDividerItemDecoration);

        } else {
            restaurantRecyclerViewAdapter.updateRestaurantList(restaurantList);
        }


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("restaurant_list", (Serializable) restaurantList);

        // Dummy fragment to save state
        RetainedFragment.getInstance(this.getSupportFragmentManager()).pushData((Bundle) outState.clone(), false);
        outState.clear();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Stops active service call
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
