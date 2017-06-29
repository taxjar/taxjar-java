package com.taxjar.exception;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class TaxjarException extends Exception {
    private Integer statusCode;
    private String message;

    public TaxjarException(String error) {
        Gson gson = new Gson();
        JsonObject json = gson.fromJson(error, JsonObject.class);
        this.statusCode = json.get("status").getAsInt();
        this.message = json.get("error").getAsString() + " - " + json.get("detail").getAsString();
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }
}
