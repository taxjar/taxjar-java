package com.taxjar.model.summarized_rates;

import com.google.gson.annotations.SerializedName;

public class SummaryRate {
    @SerializedName("country_code")
    String countryCode;

    @SerializedName("country")
    String country;

    @SerializedName("region_code")
    String regionCode;

    @SerializedName("region")
    String region;

    @SerializedName("minimum_rate")
    MinimumRate minimumRate;

    @SerializedName("average_rate")
    AverageRate averageRate;

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

    public MinimumRate getMinimumRate() {
        return minimumRate;
    }

    public AverageRate getAverageRate() {
        return averageRate;
    }
}
