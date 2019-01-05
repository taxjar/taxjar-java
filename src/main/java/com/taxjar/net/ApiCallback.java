package com.taxjar.net;

import com.taxjar.exception.ApiConnectionException;
import com.taxjar.exception.TaxjarException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;

public class ApiCallback<T> implements Callback<T> {
    protected Listener<T> apiListener;

    public ApiCallback(Listener<T> listener) {
        apiListener = listener;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful()) {
            apiListener.onSuccess(response.body());
        } else {
            try {
                apiListener.onError(new TaxjarException(response.errorBody().string()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        apiListener.onError(new ApiConnectionException(t.getMessage(), t));
    }
}

