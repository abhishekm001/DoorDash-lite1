package com.doordash.databeans;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Restaurant implements Parcelable {

    public Restaurant(int id, String name, String description, String coverImageUrl, String status, int deliveryFee) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.coverImageUrl = coverImageUrl;
        this.status = status;
        this.deliveryFee = deliveryFee;
    }

    public Restaurant(Parcel in) {
        readFromParcel(in);
    }

    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("cover_img_url")
    private String coverImageUrl;
    @SerializedName("status")
    private String status;
    @SerializedName("delivery_fee")
    private int deliveryFee;

    // like/unlike
    private boolean like;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(int deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Restaurant createFromParcel(Parcel in) {
            return new Restaurant(in);
        }

        public Restaurant[] newArray(int size) {
            return new Restaurant[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(coverImageUrl);
        dest.writeString(status);
        dest.writeInt(deliveryFee);

        if (like) {
            dest.writeInt(1);
        } else
            dest.writeInt(0);

    }

    private void readFromParcel(Parcel in) {
        id = in.readInt();
        name = in.readString();
        description = in.readString();
        coverImageUrl = in.readString();
        status = in.readString();
        deliveryFee = in.readInt();

        if (in.readInt() == 1) {
            like = true;
        } else
            like = false;
    }

}
