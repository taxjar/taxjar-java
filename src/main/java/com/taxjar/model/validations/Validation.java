package com.taxjar.model.validations;

import com.google.gson.annotations.SerializedName;

public class Validation {
    @SerializedName("valid")
    Boolean valid;

    @SerializedName("exists")
    Boolean exists;

    @SerializedName("vies_available")
    Boolean viesAvailable;

    @SerializedName("vies_response")
    ViesResponse viesResponse;

    public Boolean getValid() {
        return valid;
    }

    public Boolean getExists() {
        return exists;
    }

    public Boolean getViesAvailable() {
        return viesAvailable;
    }

    public ViesResponse getViesResponse() {
        return viesResponse;
    }
}
