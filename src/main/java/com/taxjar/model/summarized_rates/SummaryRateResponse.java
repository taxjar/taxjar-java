package com.taxjar.model.summarized_rates;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class SummaryRateResponse {
    @SerializedName("summary_rates")
    public List<SummaryRate> summaryRates;
}
