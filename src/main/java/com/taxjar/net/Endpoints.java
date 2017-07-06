package com.taxjar.net;

import com.taxjar.model.categories.CategoryResponse;
import com.taxjar.model.nexus.RegionResponse;
import com.taxjar.model.rates.RateResponse;
import com.taxjar.model.summarized_rates.SummaryRateResponse;
import com.taxjar.model.taxes.TaxResponse;
import com.taxjar.model.transactions.OrderResponse;
import com.taxjar.model.transactions.OrdersResponse;
import com.taxjar.model.transactions.RefundResponse;
import com.taxjar.model.transactions.RefundsResponse;
import com.taxjar.model.validations.ValidationResponse;

import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

public interface Endpoints
{
    @GET("categories")
    Call<CategoryResponse> getCategories();

    @GET("rates/{zip}")
    Call<RateResponse> getRate(@Path("zip") String zip);

    @GET("rates/{zip}")
    Call<RateResponse> getRate(@Path("zip") String zip, @QueryMap Map<String, String> params);

    @POST("taxes")
    Call<TaxResponse> getTax(@Body Map<String, Object> params);

    @GET("transactions/orders")
    Call<OrdersResponse> getOrders();

    @GET("transactions/orders")
    Call<OrdersResponse> getOrders(@QueryMap Map<String, String> params);

    @GET("transactions/orders/{transactionId}")
    Call<OrderResponse> getOrder(@Path("transactionId") String transactionId);

    @POST("transactions/orders")
    Call<OrderResponse> createOrder(@Body Map<String, Object> params);

    @PUT("transactions/orders/{transactionId}")
    Call<OrderResponse> updateOrder(@Path("transactionId") String transactionId, @Body Map<String, Object> params);

    @DELETE("transactions/orders/{transactionId}")
    Call<OrderResponse> deleteOrder(@Path("transactionId") String transactionId);

    @GET("transactions/refunds")
    Call<RefundsResponse> getRefunds();

    @GET("transactions/refunds")
    Call<RefundsResponse> getRefunds(@QueryMap Map<String, String> params);

    @GET("transactions/refunds/{transactionId}")
    Call<RefundResponse> getRefund(@Path("transactionId") String transactionId);

    @POST("transactions/refunds")
    Call<RefundResponse> createRefund(@Body Map<String, Object> params);

    @PUT("transactions/refunds/{transactionId}")
    Call<RefundResponse> updateRefund(@Path("transactionId") String transactionId, @Body Map<String, Object> params);

    @DELETE("transactions/refunds/{transactionId}")
    Call<RefundResponse> deleteRefund(@Path("transactionId") String transactionId);

    @GET("nexus/regions")
    Call<RegionResponse> getRegions();

    @GET("validation")
    Call<ValidationResponse> getValidation(@QueryMap Map<String, String> params);

    @GET("summary_rates")
    Call<SummaryRateResponse> getSummaryRates();
}