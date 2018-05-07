package com.taxjar.model.customers;

import com.google.gson.annotations.SerializedName;

public class ExemptRegion {
    @SerializedName("country")
    String country;

    @SerializedName("state")
    String state;

    public String getCountry() {
        return country;
    }

    public String getState() {
        return state;
    }
}
