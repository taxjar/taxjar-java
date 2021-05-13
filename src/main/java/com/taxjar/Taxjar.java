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
import com.taxjar.net.ApiCallback;
import com.taxjar.net.Endpoints;
import com.taxjar.net.Listener;
import com.taxjar.net.ApiRequest;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Taxjar {
    public static final String DEFAULT_API_URL = "https://api.taxjar.com";
    public static final String SANDBOX_API_URL = "https://api.sandbox.taxjar.com";
    public static final String API_VERSION = "v2";
    public static final String VERSION = "5.0.0";
    protected Endpoints apiService;
    protected String apiUrl;
    protected String apiToken;
    protected Map<String, String> headers;
    protected long timeout = 30000;

    public Taxjar(final String apiToken) {
        this(apiToken, null);
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

    protected static String getUserAgent() {
        String[] propertyNames = {"os.name", "os.version", "os.arch", "java.version", "java.vendor"};
        Set<String> properties = new LinkedHashSet<String>();

        for (String property : propertyNames) {
            properties.add(System.getProperty(property));
        }

        properties.add(VERSION);

        return String.format("TaxJar/Java (%s %s; %s; java %s; %s) taxjar-java/%s", properties.toArray());
    }

    public Object getApiConfig(String key) {
        try {
            return getClass().getDeclaredField(key).get(this);
        } catch (NoSuchFieldException | IllegalAccessException ex) {
            return null;
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
        return new ApiRequest<>(call).execute();
    }

    public void categories(final Listener<CategoryResponse> listener) {
        Call<CategoryResponse> call = apiService.getCategories();
        call.enqueue(new ApiCallback<>(listener));
    }

    public RateResponse ratesForLocation(String zip) throws TaxjarException {
        Call<RateResponse> call = apiService.getRate(encodePathParam(zip));
        return new ApiRequest<>(call).execute();
    }

    public RateResponse ratesForLocation(String zip, Map<String, String> params) throws TaxjarException {
        Call<RateResponse> call = apiService.getRate(encodePathParam(zip), params);
        return new ApiRequest<>(call).execute();
    }

    public void ratesForLocation(String zip, final Listener<RateResponse> listener) throws TaxjarException {
        Call<RateResponse> call = apiService.getRate(encodePathParam(zip));
        call.enqueue(new ApiCallback<>(listener));
    }

    public void ratesForLocation(String zip, Map<String, String> params, final Listener<RateResponse> listener) throws TaxjarException {
        Call<RateResponse> call = apiService.getRate(encodePathParam(zip), params);
        call.enqueue(new ApiCallback<>(listener));
    }

    public TaxResponse taxForOrder(Map<String, Object> params) throws TaxjarException {
        Call<TaxResponse> call = apiService.getTax(params);
        return new ApiRequest<>(call).execute();
    }

    public void taxForOrder(Map<String, Object> params, final Listener<TaxResponse> listener) {
        Call<TaxResponse> call = apiService.getTax(params);
        call.enqueue(new ApiCallback<>(listener));
    }

    public OrdersResponse listOrders() throws TaxjarException {
        Call<OrdersResponse> call = apiService.getOrders();
        return new ApiRequest<>(call).execute();
    }

    public OrdersResponse listOrders(Map<String, String> params) throws TaxjarException {
        Call<OrdersResponse> call = apiService.getOrders(params);
        return new ApiRequest<>(call).execute();
    }

    public void listOrders(final Listener<OrdersResponse> listener) {
        Call<OrdersResponse> call = apiService.getOrders();
        call.enqueue(new ApiCallback<>(listener));
    }

    public void listOrders(Map<String, String> params, final Listener<OrdersResponse> listener) {
        Call<OrdersResponse> call = apiService.getOrders(params);
        call.enqueue(new ApiCallback<>(listener));
    }

    public OrderResponse showOrder(String transactionId) throws TaxjarException {
        Call<OrderResponse> call = apiService.getOrder(encodePathParam(transactionId));
        return new ApiRequest<>(call).execute();
    }

    public OrderResponse showOrder(String transactionId, Map<String, String> params) throws TaxjarException {
        Call<OrderResponse> call = apiService.getOrder(encodePathParam(transactionId), params);
        return new ApiRequest<>(call).execute();
    }

    public void showOrder(String transactionId, final Listener<OrderResponse> listener) {
        Call<OrderResponse> call = apiService.getOrder(encodePathParam(transactionId));
        call.enqueue(new ApiCallback<>(listener));
    }

    public void showOrder(String transactionId, Map<String, String> params, final Listener<OrderResponse> listener) {
        Call<OrderResponse> call = apiService.getOrder(encodePathParam(transactionId), params);
        call.enqueue(new ApiCallback<>(listener));
    }

    public OrderResponse createOrder(Map<String, Object> params) throws TaxjarException {
        Call<OrderResponse> call = apiService.createOrder(params);
        return new ApiRequest<>(call).execute();
    }

    public void createOrder(Map<String, Object> params, final Listener<OrderResponse> listener) {
        Call<OrderResponse> call = apiService.createOrder(params);
        call.enqueue(new ApiCallback<>(listener));
    }

    public OrderResponse updateOrder(String transactionId, Map<String, Object> params) throws TaxjarException {
        Call<OrderResponse> call = apiService.updateOrder(encodePathParam(transactionId), params);
        return new ApiRequest<>(call).execute();
    }

    public void updateOrder(String transactionId, Map<String, Object> params, final Listener<OrderResponse> listener) {
        Call<OrderResponse> call = apiService.updateOrder(encodePathParam(transactionId), params);
        call.enqueue(new ApiCallback<>(listener));
    }

    public OrderResponse deleteOrder(String transactionId) throws TaxjarException {
        Call<OrderResponse> call = apiService.deleteOrder(encodePathParam(transactionId));
        return new ApiRequest<>(call).execute();
    }

    public OrderResponse deleteOrder(String transactionId, Map<String, String> params) throws TaxjarException {
        Call<OrderResponse> call = apiService.deleteOrder(encodePathParam(transactionId), params);
        return new ApiRequest<>(call).execute();
    }

    public void deleteOrder(String transactionId, final Listener<OrderResponse> listener) {
        Call<OrderResponse> call = apiService.deleteOrder(encodePathParam(transactionId));
        call.enqueue(new ApiCallback<>(listener));
    }

    public void deleteOrder(String transactionId, Map<String, String> params, final Listener<OrderResponse> listener) {
        Call<OrderResponse> call = apiService.deleteOrder(encodePathParam(transactionId), params);
        call.enqueue(new ApiCallback<>(listener));
    }

    public RefundsResponse listRefunds() throws TaxjarException {
        Call<RefundsResponse> call = apiService.getRefunds();
        return new ApiRequest<>(call).execute();
    }

    public RefundsResponse listRefunds(Map<String, String> params) throws TaxjarException {
        Call<RefundsResponse> call = apiService.getRefunds(params);
        return new ApiRequest<>(call).execute();
    }

    public void listRefunds(final Listener<RefundsResponse> listener) {
        Call<RefundsResponse> call = apiService.getRefunds();
        call.enqueue(new ApiCallback<>(listener));
    }

    public void listRefunds(Map<String, String> params, final Listener<RefundsResponse> listener) {
        Call<RefundsResponse> call = apiService.getRefunds(params);
        call.enqueue(new ApiCallback<>(listener));
    }

    public RefundResponse showRefund(String transactionId) throws TaxjarException {
        Call<RefundResponse> call = apiService.getRefund(encodePathParam(transactionId));
        return new ApiRequest<>(call).execute();
    }

    public RefundResponse showRefund(String transactionId, Map<String, String> params) throws TaxjarException {
        Call<RefundResponse> call = apiService.getRefund(encodePathParam(transactionId), params);
        return new ApiRequest<>(call).execute();
    }

    public void showRefund(String transactionId, final Listener<RefundResponse> listener) {
        Call<RefundResponse> call = apiService.getRefund(encodePathParam(transactionId));
        call.enqueue(new ApiCallback<>(listener));
    }

    public void showRefund(String transactionId, Map<String, String> params, final Listener<RefundResponse> listener) {
        Call<RefundResponse> call = apiService.getRefund(encodePathParam(transactionId), params);
        call.enqueue(new ApiCallback<>(listener));
    }

    public RefundResponse createRefund(Map<String, Object> params) throws TaxjarException {
        Call<RefundResponse> call = apiService.createRefund(params);
        return new ApiRequest<>(call).execute();
    }

    public void createRefund(Map<String, Object> params, final Listener<RefundResponse> listener) {
        Call<RefundResponse> call = apiService.createRefund(params);
        call.enqueue(new ApiCallback<>(listener));
    }

    public RefundResponse createRefund(String transactionId, Map<String, Object> params) throws TaxjarException {
        Call<RefundResponse> call = apiService.updateRefund(encodePathParam(transactionId), params);
        return new ApiRequest<>(call).execute();
    }

    public void createRefund(String transactionId, Map<String, Object> params, final Listener<RefundResponse> listener) {
        Call<RefundResponse> call = apiService.updateRefund(encodePathParam(transactionId), params);
        call.enqueue(new ApiCallback<>(listener));
    }

    public RefundResponse updateRefund(String transactionId, Map<String, Object> params) throws TaxjarException {
        Call<RefundResponse> call = apiService.updateRefund(encodePathParam(transactionId), params);
        return new ApiRequest<>(call).execute();
    }

    public void updateRefund(String transactionId, Map<String, Object> params, final Listener<RefundResponse> listener) {
        Call<RefundResponse> call = apiService.updateRefund(encodePathParam(transactionId), params);
        call.enqueue(new ApiCallback<>(listener));
    }

    public RefundResponse deleteRefund(String transactionId) throws TaxjarException {
        Call<RefundResponse> call = apiService.deleteRefund(encodePathParam(transactionId));
        return new ApiRequest<>(call).execute();
    }

    public RefundResponse deleteRefund(String transactionId, Map<String, String> params) throws TaxjarException {
        Call<RefundResponse> call = apiService.deleteRefund(encodePathParam(transactionId), params);
        return new ApiRequest<>(call).execute();
    }

    public void deleteRefund(String transactionId, final Listener<RefundResponse> listener) {
        Call<RefundResponse> call = apiService.deleteRefund(encodePathParam(transactionId));
        call.enqueue(new ApiCallback<>(listener));
    }

    public void deleteRefund(String transactionId, Map<String, String> params, final Listener<RefundResponse> listener) {
        Call<RefundResponse> call = apiService.deleteRefund(encodePathParam(transactionId), params);
        call.enqueue(new ApiCallback<>(listener));
    }

    public CustomersResponse listCustomers() throws TaxjarException {
        Call<CustomersResponse> call = apiService.getCustomers();
        return new ApiRequest<>(call).execute();
    }

    public CustomersResponse listCustomers(Map<String, String> params) throws TaxjarException {
        Call<CustomersResponse> call = apiService.getCustomers(params);
        return new ApiRequest<>(call).execute();
    }

    public void listCustomers(final Listener<CustomersResponse> listener) {
        Call<CustomersResponse> call = apiService.getCustomers();
        call.enqueue(new ApiCallback<>(listener));
    }

    public void listCustomers(Map<String, String> params, final Listener<CustomersResponse> listener) {
        Call<CustomersResponse> call = apiService.getCustomers(params);
        call.enqueue(new ApiCallback<>(listener));
    }

    public CustomerResponse showCustomer(String customerId) throws TaxjarException {
        Call<CustomerResponse> call = apiService.getCustomer(encodePathParam(customerId));
        return new ApiRequest<>(call).execute();
    }

    public void showCustomer(String customerId, final Listener<CustomerResponse> listener) {
        Call<CustomerResponse> call = apiService.getCustomer(encodePathParam(customerId));
        call.enqueue(new ApiCallback<>(listener));
    }

    public CustomerResponse createCustomer(Map<String, Object> params) throws TaxjarException {
        Call<CustomerResponse> call = apiService.createCustomer(params);
        return new ApiRequest<>(call).execute();
    }

    public void createCustomer(Map<String, Object> params, final Listener<CustomerResponse> listener) {
        Call<CustomerResponse> call = apiService.createCustomer(params);
        call.enqueue(new ApiCallback<>(listener));
    }

    public CustomerResponse updateCustomer(String customerId, Map<String, Object> params) throws TaxjarException {
        Call<CustomerResponse> call = apiService.updateCustomer(encodePathParam(customerId), params);
        return new ApiRequest<>(call).execute();
    }

    public void updateCustomer(String customerId, Map<String, Object> params, final Listener<CustomerResponse> listener) {
        Call<CustomerResponse> call = apiService.updateCustomer(encodePathParam(customerId), params);
        call.enqueue(new ApiCallback<>(listener));
    }

    public CustomerResponse deleteCustomer(String customerId) throws TaxjarException {
        Call<CustomerResponse> call = apiService.deleteCustomer(encodePathParam(customerId));
        return new ApiRequest<>(call).execute();
    }

    public void deleteCustomer(String customerId, final Listener<CustomerResponse> listener) {
        Call<CustomerResponse> call = apiService.deleteCustomer(encodePathParam(customerId));
        call.enqueue(new ApiCallback<>(listener));
    }

    public RegionResponse nexusRegions() throws TaxjarException {
        Call<RegionResponse> call = apiService.getRegions();
        return new ApiRequest<>(call).execute();
    }

    public void nexusRegions(final Listener<RegionResponse> listener) {
        Call<RegionResponse> call = apiService.getRegions();
        call.enqueue(new ApiCallback<>(listener));
    }

    public AddressResponse validateAddress(Map<String, Object> params) throws TaxjarException {
        Call<AddressResponse> call = apiService.getAddresses(params);
        return new ApiRequest<>(call).execute();
    }

    public void validateAddress(Map<String, Object> params, final Listener<AddressResponse> listener) {
        Call<AddressResponse> call = apiService.getAddresses(params);
        call.enqueue(new ApiCallback<>(listener));
    }

    public ValidationResponse validateVat(Map<String, String> params) throws TaxjarException {
        Call<ValidationResponse> call = apiService.getValidation(params);
        return new ApiRequest<>(call).execute();
    }

    public void validateVat(Map<String, String> params, final Listener<ValidationResponse> listener) {
        Call<ValidationResponse> call = apiService.getValidation(params);
        call.enqueue(new ApiCallback<>(listener));
    }

    public SummaryRateResponse summaryRates() throws TaxjarException {
        Call<SummaryRateResponse> call = apiService.getSummaryRates();
        return new ApiRequest<>(call).execute();
    }

    public void summaryRates(final Listener<SummaryRateResponse> listener) {
        Call<SummaryRateResponse> call = apiService.getSummaryRates();
        call.enqueue(new ApiCallback<>(listener));
    }

    private static String encodePathParam(String param) {
        try {
            param = new URI(null, null, param).getRawFragment();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return param;
    }
}
