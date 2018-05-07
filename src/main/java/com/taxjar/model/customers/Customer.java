package com.taxjar.model.customers;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Customer {
    @SerializedName("customer_id")
    String customerId;

    @SerializedName("exemption_type")
    String exemptionType;

    @SerializedName("exempt_regions")
    List<ExemptRegion> exemptRegions;

    @SerializedName("name")
    String name;

    @SerializedName("country")
    String country;

    @SerializedName("state")
    String state;

    @SerializedName("zip")
    String zip;

    @SerializedName("city")
    String city;

    @SerializedName("street")
    String street;

    public String getCustomerId() {
        return customerId;
    }

    public String getExemptionType() {
        return exemptionType;
    }

    public List<ExemptRegion> getExemptRegions() {
        return exemptRegions;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String getState() {
        return state;
    }

    public String getZip() {
        return zip;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }
}
