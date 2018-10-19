package com.taxjar.model.validations;

import com.google.gson.annotations.SerializedName;

public class Address {
    @SerializedName("country")
    String country;

    @SerializedName("zip")
    String zip;

    @SerializedName("state")
    String state;

    @SerializedName("city")
    String city;

    @SerializedName("street")
    String street;

    public String getCountry() {
        return country;
    }

    public String getZip() {
        return zip;
    }

    public String getState() {
        return state;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }
}
