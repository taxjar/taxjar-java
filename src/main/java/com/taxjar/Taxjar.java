package com.taxjar;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.taxjar.exception.TaxjarException;
import com.taxjar.model.categories.CategoryResponse;
import com.taxjar.model.customers.CustomerResponse;
import com.taxjar.model.customers.CustomersResponse;
import com.taxjar.model.nexus.RegionResponse;
import com.taxjar.model.rates.RateResponse;
import com.taxjar.model.summarized_rates.SummaryRateResponse;
import com.taxjar.model.taxes.TaxResponse;
import com.taxjar.model.transactions.OrderResponse;
import com.taxjar.model.transactions.OrdersResponse;
import com.taxjar.model.transactions.RefundResponse;
import com.taxjar.model.transactions.RefundsResponse;
import com.taxjar.model.validations.AddressResponse;
import com.taxjar.model.validations.ValidationResponse;
import com.taxjar.net.Endpoints;
import com.taxjar.net.Listener;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Taxjar {
    public static final String DEFAULT_API_URL = "https://api.taxjar.com";
    public static final String SANDBOX_API_URL = "https://api.sandbox.taxjar.com";
    public static final String API_VERSION = "v2";
    public static final String VERSION = "1.3.0";
    protected static Endpoints apiService;
    protected static String apiUrl;
    protected static String apiToken;
    protected static long timeout = 30000;

    public Taxjar(final String apiToken) {
        this.apiToken = apiToken;
        this.apiUrl = DEFAULT_API_URL;
        buildClient(null);
    }

    public Taxjar(final String apiToken, Map<String, Object> params) {
        this.apiToken = apiToken;
        this.apiUrl = DEFAULT_API_URL;
        buildClient(params);
    }

    public void buildClient(Map<String, Object> params) {
        final String apiToken = this.apiToken;

        if (params != null) {
            for (Map.Entry<String, Object> param : params.entrySet()) {
                try {
                    getClass().getDeclaredField(param.getKey()).set(this, param.getValue());
                } catch (NoSuchFieldException | IllegalAccessException ex) {
                    // No-op
                }
            }
        }

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request newRequest = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer " + apiToken)
                        .addHeader("User-Agent", "TaxJarJava/" + VERSION)
                        .build();
                return chain.proceed(newRequest);
            }
        })
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

    public String getApiConfig(String key) {
        try {
            return getClass().getDeclaredField(key).get(this).toString();
        } catch (NoSuchFieldException | IllegalAccessException ex) {
            return "";
        }
    }

    public void setApiConfig(String key, Object value) {
        try {
            getClass().getDeclaredField(key).set(this, value);
            buildClient(null);
        } catch (NoSuchFieldException | IllegalAccessException ex) {
            // No-op
        }
    }

    public CategoryResponse categories() throws TaxjarException {
        Call<CategoryResponse> call = apiService.getCategories();

        try {
            Response<CategoryResponse> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                throw new TaxjarException(response.errorBody().string());
            }
        } catch (IOException e) {
            throw new TaxjarException(e.getMessage(), e);
        }
    }

    public void categories(final Listener<CategoryResponse> listener) {
        Call<CategoryResponse> call = apiService.getCategories();

        call.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                if (response.isSuccessful()) {
                    listener.onSuccess(response.body());
                } else {
                    try {
                        listener.onError(new TaxjarException(response.errorBody().string()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                listener.onError(new TaxjarException(t.getMessage(), t));
            }
        });
    }

    public RateResponse ratesForLocation(String zip) throws TaxjarException {
        Call<RateResponse> call = apiService.getRate(zip);

        try {
            Response<RateResponse> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                throw new TaxjarException(response.errorBody().string());
            }
        } catch (IOException e) {
            throw new TaxjarException(e.getMessage(), e);
        }
    }

    public RateResponse ratesForLocation(String zip, Map<String, String> params) throws TaxjarException {
        Call<RateResponse> call = apiService.getRate(zip, params);

        try {
            Response<RateResponse> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                throw new TaxjarException(response.errorBody().string());
            }
        } catch (IOException e) {
            throw new TaxjarException(e.getMessage(), e);
        }
    }

    public void ratesForLocation(String zip, final Listener<RateResponse> listener) throws TaxjarException {
        Call<RateResponse> call = apiService.getRate(zip);

        call.enqueue(new Callback<RateResponse>() {
            @Override
            public void onResponse(Call<RateResponse> call, Response<RateResponse> response) {
                if (response.isSuccessful()) {
                    listener.onSuccess(response.body());
                } else {
                    try {
                        listener.onError(new TaxjarException(response.errorBody().string()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<RateResponse> call, Throwable t) {
                listener.onError(new TaxjarException(t.getMessage(), t));
            }
        });
    }

    public void ratesForLocation(String zip, Map<String, String> params, final Listener<RateResponse> listener) throws TaxjarException {
        Call<RateResponse> call = apiService.getRate(zip, params);

        call.enqueue(new Callback<RateResponse>() {
            @Override
            public void onResponse(Call<RateResponse> call, Response<RateResponse> response) {
                if (response.isSuccessful()) {
                    listener.onSuccess(response.body());
                } else {
                    try {
                        listener.onError(new TaxjarException(response.errorBody().string()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<RateResponse> call, Throwable t) {
               listener.onError(new TaxjarException(t.getMessage(), t));
            }
        });
    }

    public TaxResponse taxForOrder(Map<String, Object> params) throws TaxjarException {
        Call<TaxResponse> call = apiService.getTax(params);

        try {
            Response<TaxResponse> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                throw new TaxjarException(response.errorBody().string());
            }
        } catch (IOException e) {
            throw new TaxjarException(e.getMessage(), e);
        }
    }

    public void taxForOrder(Map<String, Object> params, final Listener<TaxResponse> listener) {
        Call<TaxResponse> call = apiService.getTax(params);

        call.enqueue(new Callback<TaxResponse>() {
            @Override
            public void onResponse(Call<TaxResponse> call, Response<TaxResponse> response) {
                if (response.isSuccessful()) {
                    listener.onSuccess(response.body());
                } else {
                    try {
                        listener.onError(new TaxjarException(response.errorBody().string()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<TaxResponse> call, Throwable t) {
               listener.onError(new TaxjarException(t.getMessage(), t));
            }
        });
    }

    public OrdersResponse listOrders() throws TaxjarException {
        Call<OrdersResponse> call = apiService.getOrders();

        try {
            Response<OrdersResponse> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                throw new TaxjarException(response.errorBody().string());
            }
        } catch (IOException e) {
            throw new TaxjarException(e.getMessage(), e);
        }
    }

    public OrdersResponse listOrders(Map<String, String> params) throws TaxjarException {
        Call<OrdersResponse> call = apiService.getOrders(params);

        try {
            Response<OrdersResponse> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                throw new TaxjarException(response.errorBody().string());
            }
        } catch (IOException e) {
            throw new TaxjarException(e.getMessage(), e);
        }
    }

    public void listOrders(final Listener<OrdersResponse> listener) {
        Call<OrdersResponse> call = apiService.getOrders();

        call.enqueue(new Callback<OrdersResponse>() {
            @Override
            public void onResponse(Call<OrdersResponse> call, Response<OrdersResponse> response) {
                if (response.isSuccessful()) {
                    listener.onSuccess(response.body());
                } else {
                    try {
                        listener.onError(new TaxjarException(response.errorBody().string()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<OrdersResponse> call, Throwable t) {
               listener.onError(new TaxjarException(t.getMessage(), t));
            }
        });
    }

    public void listOrders(Map<String, String> params, final Listener<OrdersResponse> listener) {
        Call<OrdersResponse> call = apiService.getOrders(params);

        call.enqueue(new Callback<OrdersResponse>() {
            @Override
            public void onResponse(Call<OrdersResponse> call, Response<OrdersResponse> response) {
                if (response.isSuccessful()) {
                    listener.onSuccess(response.body());
                } else {
                    try {
                        listener.onError(new TaxjarException(response.errorBody().string()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<OrdersResponse> call, Throwable t) {
               listener.onError(new TaxjarException(t.getMessage(), t));
            }
        });
    }

    public OrderResponse showOrder(String transactionId) throws TaxjarException {
        Call<OrderResponse> call = apiService.getOrder(transactionId);

        try {
            Response<OrderResponse> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                throw new TaxjarException(response.errorBody().string());
            }
        } catch (IOException e) {
            throw new TaxjarException(e.getMessage(), e);
        }
    }

    public void showOrder(String transactionId, final Listener<OrderResponse> listener) {
        Call<OrderResponse> call = apiService.getOrder(transactionId);

        call.enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                if (response.isSuccessful()) {
                    listener.onSuccess(response.body());
                } else {
                    try {
                        listener.onError(new TaxjarException(response.errorBody().string()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
               listener.onError(new TaxjarException(t.getMessage(), t));
            }
        });
    }

    public OrderResponse createOrder(Map<String, Object> params) throws TaxjarException {
        Call<OrderResponse> call = apiService.createOrder(params);

        try {
            Response<OrderResponse> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                throw new TaxjarException(response.errorBody().string());
            }
        } catch (IOException e) {
            throw new TaxjarException(e.getMessage(), e);
        }
    }

    public void createOrder(Map<String, Object> params, final Listener<OrderResponse> listener) {
        Call<OrderResponse> call = apiService.createOrder(params);

        call.enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                if (response.isSuccessful()) {
                    listener.onSuccess(response.body());
                } else {
                    try {
                        listener.onError(new TaxjarException(response.errorBody().string()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
               listener.onError(new TaxjarException(t.getMessage(), t));
            }
        });
    }

    public OrderResponse updateOrder(String transactionId, Map<String, Object> params) throws TaxjarException {
        Call<OrderResponse> call = apiService.updateOrder(transactionId, params);

        try {
            Response<OrderResponse> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                throw new TaxjarException(response.errorBody().string());
            }
        } catch (IOException e) {
            throw new TaxjarException(e.getMessage(), e);
        }
    }

    public void updateOrder(String transactionId, Map<String, Object> params, final Listener<OrderResponse> listener) {
        Call<OrderResponse> call = apiService.updateOrder(transactionId, params);

        call.enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                if (response.isSuccessful()) {
                    listener.onSuccess(response.body());
                } else {
                    try {
                        listener.onError(new TaxjarException(response.errorBody().string()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
               listener.onError(new TaxjarException(t.getMessage(), t));
            }
        });
    }

    public OrderResponse deleteOrder(String transactionId) throws TaxjarException {
        Call<OrderResponse> call = apiService.deleteOrder(transactionId);

        try {
            Response<OrderResponse> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                throw new TaxjarException(response.errorBody().string());
            }
        } catch (IOException e) {
            throw new TaxjarException(e.getMessage(), e);
        }
    }

    public void deleteOrder(String transactionId, final Listener<OrderResponse> listener) {
        Call<OrderResponse> call = apiService.deleteOrder(transactionId);

        call.enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                if (response.isSuccessful()) {
                    listener.onSuccess(response.body());
                } else {
                    try {
                        listener.onError(new TaxjarException(response.errorBody().string()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
               listener.onError(new TaxjarException(t.getMessage(), t));
            }
        });
    }

    public RefundsResponse listRefunds() throws TaxjarException {
        Call<RefundsResponse> call = apiService.getRefunds();

        try {
            Response<RefundsResponse> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                throw new TaxjarException(response.errorBody().string());
            }
        } catch (IOException e) {
            throw new TaxjarException(e.getMessage(), e);
        }
    }

    public RefundsResponse listRefunds(Map<String, String> params) throws TaxjarException {
        Call<RefundsResponse> call = apiService.getRefunds(params);

        try {
            Response<RefundsResponse> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                throw new TaxjarException(response.errorBody().string());
            }
        } catch (IOException e) {
            throw new TaxjarException(e.getMessage(), e);
        }
    }

    public void listRefunds(final Listener<RefundsResponse> listener) {
        Call<RefundsResponse> call = apiService.getRefunds();

        call.enqueue(new Callback<RefundsResponse>() {
            @Override
            public void onResponse(Call<RefundsResponse> call, Response<RefundsResponse> response) {
                if (response.isSuccessful()) {
                    listener.onSuccess(response.body());
                } else {
                    try {
                        listener.onError(new TaxjarException(response.errorBody().string()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<RefundsResponse> call, Throwable t) {
               listener.onError(new TaxjarException(t.getMessage(), t));
            }
        });
    }

    public void listRefunds(Map<String, String> params, final Listener<RefundsResponse> listener) {
        Call<RefundsResponse> call = apiService.getRefunds(params);

        call.enqueue(new Callback<RefundsResponse>() {
            @Override
            public void onResponse(Call<RefundsResponse> call, Response<RefundsResponse> response) {
                if (response.isSuccessful()) {
                    listener.onSuccess(response.body());
                } else {
                    try {
                        listener.onError(new TaxjarException(response.errorBody().string()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<RefundsResponse> call, Throwable t) {
               listener.onError(new TaxjarException(t.getMessage(), t));
            }
        });
    }

    public RefundResponse showRefund(String transactionId) throws TaxjarException {
        Call<RefundResponse> call = apiService.getRefund(transactionId);

        try {
            Response<RefundResponse> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                throw new TaxjarException(response.errorBody().string());
            }
        } catch (IOException e) {
            throw new TaxjarException(e.getMessage(), e);
        }
    }

    public void showRefund(String transactionId, final Listener<RefundResponse> listener) {
        Call<RefundResponse> call = apiService.getRefund(transactionId);

        call.enqueue(new Callback<RefundResponse>() {
            @Override
            public void onResponse(Call<RefundResponse> call, Response<RefundResponse> response) {
                if (response.isSuccessful()) {
                    listener.onSuccess(response.body());
                } else {
                    try {
                        listener.onError(new TaxjarException(response.errorBody().string()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<RefundResponse> call, Throwable t) {
               listener.onError(new TaxjarException(t.getMessage(), t));
            }
        });
    }

    public RefundResponse createRefund(Map<String, Object> params) throws TaxjarException {
        Call<RefundResponse> call = apiService.createRefund(params);

        try {
            Response<RefundResponse> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                throw new TaxjarException(response.errorBody().string());
            }
        } catch (IOException e) {
            throw new TaxjarException(e.getMessage(), e);
        }
    }

    public void createRefund(Map<String, Object> params, final Listener<RefundResponse> listener) {
        Call<RefundResponse> call = apiService.createRefund(params);

        call.enqueue(new Callback<RefundResponse>() {
            @Override
            public void onResponse(Call<RefundResponse> call, Response<RefundResponse> response) {
                if (response.isSuccessful()) {
                    listener.onSuccess(response.body());
                } else {
                    try {
                        listener.onError(new TaxjarException(response.errorBody().string()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<RefundResponse> call, Throwable t) {
               listener.onError(new TaxjarException(t.getMessage(), t));
            }
        });
    }

    public RefundResponse createRefund(String transactionId, Map<String, Object> params) throws TaxjarException {
        Call<RefundResponse> call = apiService.updateRefund(transactionId, params);

        try {
            Response<RefundResponse> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                throw new TaxjarException(response.errorBody().string());
            }
        } catch (IOException e) {
            throw new TaxjarException(e.getMessage(), e);
        }
    }

    public void createRefund(String transactionId, Map<String, Object> params, final Listener<RefundResponse> listener) {
        Call<RefundResponse> call = apiService.updateRefund(transactionId, params);

        call.enqueue(new Callback<RefundResponse>() {
            @Override
            public void onResponse(Call<RefundResponse> call, Response<RefundResponse> response) {
                if (response.isSuccessful()) {
                    listener.onSuccess(response.body());
                } else {
                    try {
                        listener.onError(new TaxjarException(response.errorBody().string()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<RefundResponse> call, Throwable t) {
               listener.onError(new TaxjarException(t.getMessage(), t));
            }
        });
    }

    public RefundResponse updateRefund(String transactionId, Map<String, Object> params) throws TaxjarException {
        Call<RefundResponse> call = apiService.updateRefund(transactionId, params);

        try {
            Response<RefundResponse> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                throw new TaxjarException(response.errorBody().string());
            }
        } catch (IOException e) {
            throw new TaxjarException(e.getMessage(), e);
        }
    }

    public void updateRefund(String transactionId, Map<String, Object> params, final Listener<RefundResponse> listener) {
        Call<RefundResponse> call = apiService.updateRefund(transactionId, params);

        call.enqueue(new Callback<RefundResponse>() {
            @Override
            public void onResponse(Call<RefundResponse> call, Response<RefundResponse> response) {
                if (response.isSuccessful()) {
                    listener.onSuccess(response.body());
                } else {
                    try {
                        listener.onError(new TaxjarException(response.errorBody().string()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<RefundResponse> call, Throwable t) {
               listener.onError(new TaxjarException(t.getMessage(), t));
            }
        });
    }

    public RefundResponse deleteRefund(String transactionId) throws TaxjarException {
        Call<RefundResponse> call = apiService.deleteRefund(transactionId);

        try {
            Response<RefundResponse> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                throw new TaxjarException(response.errorBody().string());
            }
        } catch (IOException e) {
            throw new TaxjarException(e.getMessage(), e);
        }
    }

    public void deleteRefund(String transactionId, final Listener<RefundResponse> listener) {
        Call<RefundResponse> call = apiService.deleteRefund(transactionId);

        call.enqueue(new Callback<RefundResponse>() {
            @Override
            public void onResponse(Call<RefundResponse> call, Response<RefundResponse> response) {
                if (response.isSuccessful()) {
                    listener.onSuccess(response.body());
                } else {
                    try {
                        listener.onError(new TaxjarException(response.errorBody().string()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<RefundResponse> call, Throwable t) {
               listener.onError(new TaxjarException(t.getMessage(), t));
            }
        });
    }

    public CustomersResponse listCustomers() throws TaxjarException {
        Call<CustomersResponse> call = apiService.getCustomers();

        try {
            Response<CustomersResponse> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                throw new TaxjarException(response.errorBody().string());
            }
        } catch (IOException e) {
            throw new TaxjarException(e.getMessage(), e);
        }
    }

    public CustomersResponse listCustomers(Map<String, String> params) throws TaxjarException {
        Call<CustomersResponse> call = apiService.getCustomers(params);

        try {
            Response<CustomersResponse> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                throw new TaxjarException(response.errorBody().string());
            }
        } catch (IOException e) {
            throw new TaxjarException(e.getMessage(), e);
        }
    }

    public void listCustomers(final Listener<CustomersResponse> listener) {
        Call<CustomersResponse> call = apiService.getCustomers();

        call.enqueue(new Callback<CustomersResponse>() {
            @Override
            public void onResponse(Call<CustomersResponse> call, Response<CustomersResponse> response) {
                if (response.isSuccessful()) {
                    listener.onSuccess(response.body());
                } else {
                    try {
                        listener.onError(new TaxjarException(response.errorBody().string()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<CustomersResponse> call, Throwable t) {
               listener.onError(new TaxjarException(t.getMessage(), t));
            }
        });
    }

    public void listCustomers(Map<String, String> params, final Listener<CustomersResponse> listener) {
        Call<CustomersResponse> call = apiService.getCustomers(params);

        call.enqueue(new Callback<CustomersResponse>() {
            @Override
            public void onResponse(Call<CustomersResponse> call, Response<CustomersResponse> response) {
                if (response.isSuccessful()) {
                    listener.onSuccess(response.body());
                } else {
                    try {
                        listener.onError(new TaxjarException(response.errorBody().string()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<CustomersResponse> call, Throwable t) {
               listener.onError(new TaxjarException(t.getMessage(), t));
            }
        });
    }

    public CustomerResponse showCustomer(String customerId) throws TaxjarException {
        Call<CustomerResponse> call = apiService.getCustomer(customerId);

        try {
            Response<CustomerResponse> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                throw new TaxjarException(response.errorBody().string());
            }
        } catch (IOException e) {
            throw new TaxjarException(e.getMessage(), e);
        }
    }

    public void showCustomer(String customerId, final Listener<CustomerResponse> listener) {
        Call<CustomerResponse> call = apiService.getCustomer(customerId);

        call.enqueue(new Callback<CustomerResponse>() {
            @Override
            public void onResponse(Call<CustomerResponse> call, Response<CustomerResponse> response) {
                if (response.isSuccessful()) {
                    listener.onSuccess(response.body());
                } else {
                    try {
                        listener.onError(new TaxjarException(response.errorBody().string()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<CustomerResponse> call, Throwable t) {
               listener.onError(new TaxjarException(t.getMessage(), t));
            }
        });
    }

    public CustomerResponse createCustomer(Map<String, Object> params) throws TaxjarException {
        Call<CustomerResponse> call = apiService.createCustomer(params);

        try {
            Response<CustomerResponse> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                throw new TaxjarException(response.errorBody().string());
            }
        } catch (IOException e) {
            throw new TaxjarException(e.getMessage(), e);
        }
    }

    public void createCustomer(Map<String, Object> params, final Listener<CustomerResponse> listener) {
        Call<CustomerResponse> call = apiService.createCustomer(params);

        call.enqueue(new Callback<CustomerResponse>() {
            @Override
            public void onResponse(Call<CustomerResponse> call, Response<CustomerResponse> response) {
                if (response.isSuccessful()) {
                    listener.onSuccess(response.body());
                } else {
                    try {
                        listener.onError(new TaxjarException(response.errorBody().string()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<CustomerResponse> call, Throwable t) {
               listener.onError(new TaxjarException(t.getMessage(), t));
            }
        });
    }

    public CustomerResponse updateCustomer(String customerId, Map<String, Object> params) throws TaxjarException {
        Call<CustomerResponse> call = apiService.updateCustomer(customerId, params);

        try {
            Response<CustomerResponse> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                throw new TaxjarException(response.errorBody().string());
            }
        } catch (IOException e) {
            throw new TaxjarException(e.getMessage(), e);
        }
    }

    public void updateCustomer(String customerId, Map<String, Object> params, final Listener<CustomerResponse> listener) {
        Call<CustomerResponse> call = apiService.updateCustomer(customerId, params);

        call.enqueue(new Callback<CustomerResponse>() {
            @Override
            public void onResponse(Call<CustomerResponse> call, Response<CustomerResponse> response) {
                if (response.isSuccessful()) {
                    listener.onSuccess(response.body());
                } else {
                    try {
                        listener.onError(new TaxjarException(response.errorBody().string()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<CustomerResponse> call, Throwable t) {
               listener.onError(new TaxjarException(t.getMessage(), t));
            }
        });
    }

    public CustomerResponse deleteCustomer(String customerId) throws TaxjarException {
        Call<CustomerResponse> call = apiService.deleteCustomer(customerId);

        try {
            Response<CustomerResponse> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                throw new TaxjarException(response.errorBody().string());
            }
        } catch (IOException e) {
            throw new TaxjarException(e.getMessage(), e);
        }
    }

    public void deleteCustomer(String customerId, final Listener<CustomerResponse> listener) {
        Call<CustomerResponse> call = apiService.deleteCustomer(customerId);

        call.enqueue(new Callback<CustomerResponse>() {
            @Override
            public void onResponse(Call<CustomerResponse> call, Response<CustomerResponse> response) {
                if (response.isSuccessful()) {
                    listener.onSuccess(response.body());
                } else {
                    try {
                        listener.onError(new TaxjarException(response.errorBody().string()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<CustomerResponse> call, Throwable t) {
               listener.onError(new TaxjarException(t.getMessage(), t));
            }
        });
    }

    public RegionResponse nexusRegions() throws TaxjarException {
        Call<RegionResponse> call = apiService.getRegions();

        try {
            Response<RegionResponse> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                throw new TaxjarException(response.errorBody().string());
            }
        } catch (IOException e) {
            throw new TaxjarException(e.getMessage(), e);
        }
    }

    public void nexusRegions(final Listener<RegionResponse> listener) {
        Call<RegionResponse> call = apiService.getRegions();

        call.enqueue(new Callback<RegionResponse>() {
            @Override
            public void onResponse(Call<RegionResponse> call, Response<RegionResponse> response) {
                if (response.isSuccessful()) {
                    listener.onSuccess(response.body());
                } else {
                    try {
                        listener.onError(new TaxjarException(response.errorBody().string()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<RegionResponse> call, Throwable t) {
               listener.onError(new TaxjarException(t.getMessage(), t));
            }
        });
    }

    public AddressResponse validateAddress(Map<String, Object> params) throws TaxjarException {
        Call<AddressResponse> call = apiService.getAddresses(params);

        try {
            Response<AddressResponse> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                throw new TaxjarException(response.errorBody().string());
            }
        } catch (IOException e) {
            throw new TaxjarException(e.getMessage(), e);
        }
    }

    public void validateAddress(Map<String, Object> params, final Listener<AddressResponse> listener) {
        Call<AddressResponse> call = apiService.getAddresses(params);

        call.enqueue(new Callback<AddressResponse>() {
            @Override
            public void onResponse(Call<AddressResponse> call, Response<AddressResponse> response) {
                if (response.isSuccessful()) {
                    listener.onSuccess(response.body());
                } else {
                    try {
                        listener.onError(new TaxjarException(response.errorBody().string()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<AddressResponse> call, Throwable t) {
               listener.onError(new TaxjarException(t.getMessage(), t));
            }
        });
    }

    public ValidationResponse validateVat(Map<String, String> params) throws TaxjarException {
        Call<ValidationResponse> call = apiService.getValidation(params);

        try {
            Response<ValidationResponse> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                throw new TaxjarException(response.errorBody().string());
            }
        } catch (IOException e) {
            throw new TaxjarException(e.getMessage(), e);
        }
    }

    public void validateVat(Map<String, String> params, final Listener<ValidationResponse> listener) {
        Call<ValidationResponse> call = apiService.getValidation(params);

        call.enqueue(new Callback<ValidationResponse>() {
            @Override
            public void onResponse(Call<ValidationResponse> call, Response<ValidationResponse> response) {
                if (response.isSuccessful()) {
                    listener.onSuccess(response.body());
                } else {
                    try {
                        listener.onError(new TaxjarException(response.errorBody().string()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ValidationResponse> call, Throwable t) {
               listener.onError(new TaxjarException(t.getMessage(), t));
            }
        });
    }

    public SummaryRateResponse summaryRates() throws TaxjarException {
        Call<SummaryRateResponse> call = apiService.getSummaryRates();

        try {
            Response<SummaryRateResponse> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                throw new TaxjarException(response.errorBody().string());
            }
        } catch (IOException e) {
            throw new TaxjarException(e.getMessage(), e);
        }
    }

    public void summaryRates(final Listener<SummaryRateResponse> listener) {
        Call<SummaryRateResponse> call = apiService.getSummaryRates();

        call.enqueue(new Callback<SummaryRateResponse>() {
            @Override
            public void onResponse(Call<SummaryRateResponse> call, Response<SummaryRateResponse> response) {
                if (response.isSuccessful()) {
                    listener.onSuccess(response.body());
                } else {
                    try {
                        listener.onError(new TaxjarException(response.errorBody().string()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<SummaryRateResponse> call, Throwable t) {
               listener.onError(new TaxjarException(t.getMessage(), t));
            }
        });
    }
}
