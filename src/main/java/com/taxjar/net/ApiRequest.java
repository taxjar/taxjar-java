package com.taxjar.net;

import com.taxjar.exception.ApiConnectionException;
import com.taxjar.exception.TaxjarException;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

public class ApiRequest<T> {
    protected Call<T> apiCall;

    public ApiRequest(Call<T> call) {
        apiCall = call;
    }

    public T execute() throws TaxjarException {
        try {
            Response<T> response = apiCall.execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                throw new TaxjarException(response.errorBody().string(), response.code());
            }
        } catch (IOException e) {
            throw new ApiConnectionException(e.getMessage(), e);
        }
    }
}
