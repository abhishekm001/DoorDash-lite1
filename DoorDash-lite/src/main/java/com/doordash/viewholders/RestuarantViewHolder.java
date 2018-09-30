package com.doordash.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.doordash.databeans.Restaurant;
import com.doordash.lite.R;
import com.doordash.utils.CommonUtils;

public class RestuarantViewHolder extends RecyclerView.ViewHolder {

    private TextView name;
    private ImageView coverImage;
    private TextView description;
    private TextView status;
    private TextView deiveryFee;

    public RestuarantViewHolder(View itemView) {
        super(itemView);

        name = itemView.findViewById(R.id.restaurant_name);
        description = itemView.findViewById(R.id.description);
        coverImage = itemView.findViewById(R.id.cover_image);
        status = itemView.findViewById(R.id.status);
        deiveryFee = itemView.findViewById(R.id.delivery_fee);

    }

    public void bind(Restaurant restaurant) {

        name.setText(restaurant.getName());

        if (!CommonUtils.isNullOrEmpty(restaurant.getDescription())) {
            description.setText(restaurant.getDescription());
            description.setVisibility(View.VISIBLE);
        } else {
            description.setVisibility(View.GONE);
        }

        status.setText(restaurant.getStatus());

        if (CommonUtils.convertCentsToDollar(restaurant.getDeliveryFee()) > 0) {
            deiveryFee.setVisibility(View.VISIBLE);
            deiveryFee.setText(String.valueOf(CommonUtils.convertCentsToDollar(restaurant.getDeliveryFee())));
        } else {
            deiveryFee.setVisibility(View.GONE);

        }
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.progress_loader);
        requestOptions.fallback(R.drawable.restaurant_image_error);
        requestOptions.error(R.drawable.restaurant_image_error);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestOptions.timeout(500);
        requestOptions.fitCenter();

        Glide.with(itemView.getContext())
                .load(restaurant.getCoverImageUrl())
                .apply(requestOptions)
                .into(coverImage);

    }

}
