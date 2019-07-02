package com.taxjar.model.transactions;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Refund {
    @SerializedName("transaction_id")
    String transactionId;

    @SerializedName("user_id")
    Integer userId;

    @SerializedName("transaction_date")
    String transactionDate;

    @SerializedName("transaction_reference_id")
    String transactionReferenceId;

    @SerializedName("from_country")
    String fromCountry;

    @SerializedName("from_zip")
    String fromZip;

    @SerializedName("from_state")
    String fromState;

    @SerializedName("from_city")
    String fromCity;

    @SerializedName("from_street")
    String fromStreet;

    @SerializedName("to_country")
    String toCountry;

    @SerializedName("to_zip")
    String toZip;

    @SerializedName("to_state")
    String toState;

    @SerializedName("to_city")
    String toCity;

    @SerializedName("to_street")
    String toStreet;

    @SerializedName("amount")
    Float amount;

    @SerializedName("shipping")
    Float shipping;

    @SerializedName("sales_tax")
    Float salesTax;

    @SerializedName("exemption_type")
    String exemptionType;

    @SerializedName("line_items")
    List<LineItem> lineItems;

    public String getTransactionId() {
        return transactionId;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public String getTransactionReferenceId() {
        return transactionReferenceId;
    }

    public String getFromCountry() {
        return fromCountry;
    }

    public String getFromZip() {
        return fromZip;
    }

    public String getFromState() {
        return fromState;
    }

    public String getFromCity() {
        return fromCity;
    }

    public String getFromStreet() {
        return fromStreet;
    }

    public String getToCountry() {
        return toCountry;
    }

    public String getToZip() {
        return toZip;
    }

    public String getToState() {
        return toState;
    }

    public String getToCity() {
        return toCity;
    }

    public String getToStreet() {
        return toStreet;
    }

    public Float getAmount() {
        return amount;
    }

    public Float getShipping() {
        return shipping;
    }

    public Float getSalesTax() {
        return salesTax;
    }

    public String getExemptionType() {
        return exemptionType;
    }

    public List<LineItem> getLineItems() {
        return lineItems;
    }
}
