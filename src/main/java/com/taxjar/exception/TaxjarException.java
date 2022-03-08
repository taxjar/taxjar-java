package com.taxjar.exception;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

public class TaxjarException extends Exception {
    private Integer statusCode;

    public TaxjarException(String errorMessage, int statusCode) {
        super(parseMessage(errorMessage), null);
        this.statusCode = parseStatusCode(errorMessage, statusCode);
    }

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

        if (errorMessage == null || errorMessage.equals("")) return "";

        try {
            JsonObject json = gson.fromJson(errorMessage, JsonObject.class);
            JsonElement error = json.get("error");
            JsonElement detail = json.get("detail");

            if (error != null && detail != null) {
                return error.getAsString() + " - " + detail.getAsString();
            } else {
                return errorMessage;
            }
        } catch (JsonSyntaxException e) {
            return errorMessage;
        }
    }

    private static Integer parseStatusCode(String errorMessage, int statusCode) {
        if (errorMessage == null || errorMessage.equals("")) {
            return statusCode;
        } else {
            return parseStatusCode(errorMessage);
        }
    }

    private static Integer parseStatusCode(String errorMessage) {
        Gson gson = new Gson();

        if (errorMessage == null) return 0;

        try {
            JsonObject json = gson.fromJson(errorMessage, JsonObject.class);
            JsonElement status = json.get("status");

            if (status != null) {
                return status.getAsInt();
            } else {
                return 0;
            }
        } catch (JsonSyntaxException e) {
            return 0;
        }
    }
}
