package com.taxjar.model.categories;

import com.google.gson.annotations.SerializedName;

public class Category {
    @SerializedName("name")
    String name;

    @SerializedName("product_tax_code")
    String productTaxCode;

    @SerializedName("description")
    String description;

    public String getName() {
        return name;
    }

    public String getProductTaxCode() {
        return productTaxCode;
    }

    public String getDescription() {
        return description;
    }
}
