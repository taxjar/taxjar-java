package com.taxjar.model.validations;

import com.google.gson.annotations.SerializedName;

public class ViesResponse {
    @SerializedName("country_code")
    String countryCode;

    @SerializedName("vat_number")
    String vatNumber;

    @SerializedName("request_date")
    String requestDate;

    @SerializedName("valid")
    Boolean valid;

    @SerializedName("name")
    String name;

    @SerializedName("address")
    String address;

    public String getCountryCode() {
        return countryCode;
    }

    public String getVatNumber() {
        return vatNumber;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public Boolean getValid() {
        return valid;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }
}
