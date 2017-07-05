package com.taxjar.model.transactions;

import com.google.gson.annotations.SerializedName;

public class LineItem {
    @SerializedName("id")
    String id;

    @SerializedName("quantity")
    Integer quantity;

    @SerializedName("product_identifier")
    String productIdentifier;

    @SerializedName("description")
    String description;

    @SerializedName("unit_price")
    Float unitPrice;

    @SerializedName("discount")
    Float discount;

    @SerializedName("sales_tax")
    Float salesTax;

    public String getId() {
        return id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getProductIdentifier() {
        return productIdentifier;
    }

    public String getDescription() {
        return description;
    }

    public Float getUnitPrice() {
        return unitPrice;
    }

    public Float getDiscount() {
        return discount;
    }

    public Float getSalesTax() {
        return salesTax;
    }
}
