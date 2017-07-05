package com.taxjar;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.taxjar.net.Endpoints;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public final class TaxjarMock extends Taxjar {
    public TaxjarMock(final String apiToken, Interceptor interceptor) {
        super(apiToken);

        final OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request newRequest = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer " + apiToken)
                        .build();
                return chain.proceed(newRequest);
            }
        }).addInterceptor(interceptor).build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        apiService = retrofit.create(Endpoints.class);
    }
}
