package com.taxjar.model.summarized_rates;

import com.google.gson.annotations.SerializedName;

public class AverageRate {
    @SerializedName("label")
    String label;

    @SerializedName("rate")
    Float rate;

    public String getLabel() {
        return label;
    }

    public Float getRate() {
        return rate;
    }
}
