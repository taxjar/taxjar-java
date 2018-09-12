package com.taxjar.model.taxes;

import com.google.gson.annotations.SerializedName;

public class Jurisdictions {
    @SerializedName("country")
    String country;

    @SerializedName("state")
    String state;

    @SerializedName("county")
    String county;

    @SerializedName("city")
    String city;

    public String getCountry() { return country; }

    public String getState() { return state; }

    public String getCounty() { return county; }

    public String getCity() { return city; }
}
