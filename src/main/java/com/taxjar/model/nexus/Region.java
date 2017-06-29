package com.taxjar.model.nexus;

import com.google.gson.annotations.SerializedName;

public class Region {
    @SerializedName("country_code")
    String countryCode;

    @SerializedName("country")
    String country;

    @SerializedName("region_code")
    String regionCode;

    @SerializedName("region")
    String region;

    public String getCountryCode() {
        return countryCode;
    }

    public String getCountry() {
        return country;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public String getRegion() {
        return region;
    }
}
