package com.taxjar.model.taxes;

import com.google.gson.annotations.SerializedName;

public class Shipping {
    @SerializedName("taxable_amount")
    Float taxableAmount;

    @SerializedName("tax_collectable")
    Float taxCollectable;

    @SerializedName("combined_tax_rate")
    Float combinedTaxRate;

    @SerializedName("state_taxable_amount")
    Float stateTaxableAmount;

    @SerializedName("state_sales_tax_rate")
    Float stateTaxRate;

    @SerializedName("state_amount")
    Float stateAmount;

    @SerializedName("county_taxable_amount")
    Float countyTaxableAmount;

    @SerializedName("county_tax_rate")
    Float countyTaxRate;

    @SerializedName("county_amount")
    Float countyAmount;

    @SerializedName("city_taxable_amount")
    Float cityTaxableAmount;

    @SerializedName("city_tax_rate")
    Float cityTaxRate;

    @SerializedName("city_amount")
    Float cityAmount;

    @SerializedName("special_taxable_amount")
    Float specialDistrictTaxableAmount;

    @SerializedName("special_tax_rate")
    Float specialDistrictTaxRate;

    @SerializedName("special_district_amount")
    Float specialDistrictAmount;

    // International

    @SerializedName("country_taxable_amount")
    Float countryTaxableAmount;

    @SerializedName("country_tax_rate")
    Float countryTaxRate;

    @SerializedName("country_tax_collectable")
    Float countryTaxCollectable;

    // Canada

    @SerializedName("gst_taxable_amount")
    Float gstTaxableAmount;

    @SerializedName("gst_tax_rate")
    Float gstTaxRate;

    @SerializedName("gst")
    Float gst;

    @SerializedName("pst_taxable_amount")
    Float pstTaxableAmount;

    @SerializedName("pst_tax_rate")
    Float pstTaxRate;

    @SerializedName("pst")
    Float pst;

    @SerializedName("qst_taxable_amount")
    Float qstTaxableAmount;

    @SerializedName("qst_tax_rate")
    Float qstTaxRate;

    @SerializedName("qst")
    Float qst;

    public Float getTaxableAmount() {
        return taxableAmount;
    }

    public Float getTaxCollectable() {
        return taxCollectable;
    }

    public Float getCombinedTaxRate() {
        return combinedTaxRate;
    }

    public Float getStateTaxableAmount() {
        return stateTaxableAmount;
    }

    public Float getStateTaxRate() {
        return stateTaxRate;
    }

    public Float getStateAmount() {
        return stateAmount;
    }

    public Float getCountyTaxableAmount() {
        return countyTaxableAmount;
    }

    public Float getCountyTaxRate() {
        return countyTaxRate;
    }

    public Float getCountyAmount() {
        return countyAmount;
    }

    public Float getCityTaxableAmount() {
        return cityTaxableAmount;
    }

    public Float getCityTaxRate() {
        return cityTaxRate;
    }

    public Float getCityAmount() {
        return cityAmount;
    }

    public Float getSpecialDistrictTaxableAmount() {
        return specialDistrictTaxableAmount;
    }

    public Float getSpecialDistrictTaxRate() {
        return specialDistrictTaxRate;
    }

    public Float getSpecialDistrictAmount() {
        return specialDistrictAmount;
    }

    public Float getCountryTaxableAmount() {
        return countryTaxableAmount;
    }

    public Float getCountryTaxRate() {
        return countryTaxRate;
    }

    public Float getCountryTaxCollectable() {
        return countryTaxCollectable;
    }

    public Float getGstTaxableAmount() {
        return gstTaxableAmount;
    }

    public Float getGstTaxRate() {
        return gstTaxRate;
    }

    public Float getGst() {
        return gst;
    }

    public Float getPstTaxableAmount() {
        return pstTaxableAmount;
    }

    public Float getPstTaxRate() {
        return pstTaxRate;
    }

    public Float getPst() {
        return pst;
    }

    public Float getQstTaxableAmount() {
        return qstTaxableAmount;
    }

    public Float getQstTaxRate() {
        return qstTaxRate;
    }

    public Float getQst() {
        return qst;
    }
}
