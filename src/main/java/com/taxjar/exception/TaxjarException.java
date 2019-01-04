package com.taxjar.exception;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

public class TaxjarException extends Exception {
    private Integer statusCode;

    public TaxjarException(String errorMessage) {
        this(errorMessage, null);
    }

    public TaxjarException(String errorMessage, Throwable err) {
        super(parseMessage(errorMessage), err);
        this.statusCode = parseStatusCode(errorMessage);
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    private static String parseMessage(String errorMessage) {
        Gson gson = new Gson();

        try {
            JsonObject json = gson.fromJson(errorMessage, JsonObject.class);
            return json.get("error").getAsString() + " - " + json.get("detail").getAsString();
        } catch (JsonSyntaxException e) {
            return errorMessage;
        }
    }

    private static Integer parseStatusCode(String errorMessage) {
        Gson gson = new Gson();

        try {
            JsonObject json = gson.fromJson(errorMessage, JsonObject.class);
            return json.get("status").getAsInt();
        } catch (JsonSyntaxException e) {
            return 0;
        }
    }
}
