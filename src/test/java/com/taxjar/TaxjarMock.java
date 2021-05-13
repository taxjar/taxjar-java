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
import java.util.Map;
import java.util.concurrent.TimeUnit;

public final class TaxjarMock extends Taxjar {
    public TaxjarMock(final String apiToken, Interceptor interceptor) {
        this(apiToken, null, interceptor);
    }

    public TaxjarMock(final String apiToken, Map<String, Object> params, Interceptor interceptor) {
        super(apiToken, params);

        if (params != null) {
            for (Map.Entry<String, Object> param : params.entrySet()) {
                try {
                    getClass().getDeclaredField(param.getKey()).set(this, param.getValue());
                } catch (NoSuchFieldException | IllegalAccessException ex) {
                    // No-op
                }
            }
        }

        final OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request.Builder requestBuilder = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer " + apiToken)
                        .addHeader("User-Agent", getUserAgent());

                if (headers != null) {
                    for (Map.Entry<String, String> header : headers.entrySet()) {
                        requestBuilder.addHeader(header.getKey(), header.getValue());
                    }
                }

                return chain.proceed(requestBuilder.build());
            }
        }).addInterceptor(interceptor)
                .connectTimeout(this.timeout, TimeUnit.MILLISECONDS)
                .writeTimeout(this.timeout, TimeUnit.MILLISECONDS)
                .readTimeout(this.timeout, TimeUnit.MILLISECONDS)
                .build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(this.apiUrl + "/" + API_VERSION + "/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        apiService = retrofit.create(Endpoints.class);
    }
}
