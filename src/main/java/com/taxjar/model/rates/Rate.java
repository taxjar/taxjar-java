package com.taxjar.model.rates;

import com.google.gson.annotations.SerializedName;

public class Rate {
    @SerializedName("zip")
    String zip;

    @SerializedName("state")
    String state;

    @SerializedName("state_rate")
    Float stateRate;

    @SerializedName("county")
    String county;

    @SerializedName("county_rate")
    Float countyRate;

    @SerializedName("city")
    String city;

    @SerializedName("city_rate")
    Float cityRate;

    @SerializedName("combined_district_rate")
    Float combinedDistrictRate;

    @SerializedName("combined_rate")
    Float combinedRate;

    @SerializedName("freight_taxable")
    Boolean freightTaxable;

    // International

    @SerializedName("country")
    String country;

    @SerializedName("name")
    String name;

    // Australia / SST States

    @SerializedName("country_rate")
    Float countryRate;

    // European Union

    @SerializedName("standard_rate")
    Float standardRate;

    @SerializedName("reduced_rate")
    Float reducedRate;

    @SerializedName("super_reduced_rate")
    Float superReducedRate;

    @SerializedName("parking_rate")
    Float parkingRate;

    @SerializedName("distance_sale_threshold")
    Float distanceSaleThreshold;

    public String getZip() {
        return zip;
    }

    public String getCountry() {
        return country;
    }

    public Float getCountryRate() {
        return countryRate;
    }

    public String getState() {
        return state;
    }

    public Float getStateRate() {
        return stateRate;
    }

    public String getCounty() {
        return county;
    }

    public Float getCountyRate() {
        return countyRate;
    }

    public String getCity() {
        return city;
    }

    public Float getCityRate() {
        return cityRate;
    }

    public Float getCombinedDistrictRate() {
        return combinedDistrictRate;
    }

    public Float getCombinedRate() {
        return combinedRate;
    }

    public Boolean getFreightTaxable() {
        return freightTaxable;
    }

    public String getName() {
        return name;
    }

    public Float getStandardRate() {
        return standardRate;
    }

    public Float getReducedRate() {
        return reducedRate;
    }

    public Float getSuperReducedRate() {
        return superReducedRate;
    }

    public Float getParkingRate() {
        return parkingRate;
    }

    public Float getDistanceSaleThreshold() {
        return distanceSaleThreshold;
    }
}
