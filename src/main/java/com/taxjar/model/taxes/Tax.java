package com.taxjar.model.taxes;

import com.google.gson.annotations.SerializedName;
import com.taxjar.model.taxes.Breakdown;

public class Tax {
    @SerializedName("order_total_amount")
    Float orderTotalAmount;

    @SerializedName("shipping")
    Float shipping;

    @SerializedName("taxable_amount")
    Float taxableAmount;

    @SerializedName("amount_to_collect")
    Float amountToCollect;

    @SerializedName("rate")
    Float rate;

    @SerializedName("has_nexus")
    Boolean hasNexus;

    @SerializedName("freight_taxable")
    Boolean freightTaxable;

    @SerializedName("tax_source")
    String taxSource;

    // ---

    @SerializedName("breakdown")
    Breakdown breakdown;

    public Float getOrderTotalAmount() {
        return orderTotalAmount;
    }

    public Float getShipping() {
        return shipping;
    }

    public Float getTaxableAmount() {
        return taxableAmount;
    }

    public Float getAmountToCollect() {
        return amountToCollect;
    }

    public Float getRate() {
        return rate;
    }

    public Boolean getHasNexus() {
        return hasNexus;
    }

    public Boolean getFreightTaxable() {
        return freightTaxable;
    }

    public String getTaxSource() {
        return taxSource;
    }

    public Breakdown getBreakdown() {
        return breakdown;
    }
}
