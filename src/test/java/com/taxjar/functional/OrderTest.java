package com.taxjar.functional;

import com.taxjar.MockInterceptor;
import com.taxjar.TaxjarMock;
import com.taxjar.exception.TaxjarException;
import com.taxjar.model.transactions.OrderResponse;
import com.taxjar.model.transactions.OrdersResponse;
import com.taxjar.net.Listener;
import junit.framework.TestCase;

import java.util.*;

public class OrderTest extends TestCase {
    private TaxjarMock client;

    public void testListOrders() throws TaxjarException {
        MockInterceptor interceptor = new MockInterceptor("orders/list.json");
        client = new TaxjarMock("TEST", interceptor);

        OrdersResponse res = client.listOrders();
        assertEquals("123", res.orders.get(0));
        assertEquals("456", res.orders.get(1));
    }

    public void testListOrdersWithParams() throws TaxjarException {
        MockInterceptor interceptor = new MockInterceptor("orders/list.json");
        client = new TaxjarMock("TEST", interceptor);

        Map<String, String> params = new HashMap<>();
        params.put("from_transaction_date", "2015/05/01");
        params.put("to_transaction_date", "2015/05/31");

        OrdersResponse res = client.listOrders(params);
        assertEquals("123", res.orders.get(0));
        assertEquals("456", res.orders.get(1));
    }

    public void testListOrdersAsync() throws TaxjarException {
        MockInterceptor interceptor = new MockInterceptor("orders/list.json");
        client = new TaxjarMock("TEST", interceptor);

        client.listOrders(new Listener<OrdersResponse>() {
            @Override
            public void onSuccess(OrdersResponse res)
            {
                assertEquals("123", res.orders.get(0));
                assertEquals("456", res.orders.get(1));
            }

            @Override
            public void onError(TaxjarException error)
            {
            }
        });
    }

    public void testListOrdersAsyncWithParams() throws TaxjarException {
        MockInterceptor interceptor = new MockInterceptor("orders/list.json");
        client = new TaxjarMock("TEST", interceptor);

        Map<String, String> params = new HashMap<>();
        params.put("from_transaction_date", "2015/05/01");
        params.put("to_transaction_date", "2015/05/31");

        client.listOrders(params, new Listener<OrdersResponse>() {
            @Override
            public void onSuccess(OrdersResponse res)
            {
                assertEquals("123", res.orders.get(0));
                assertEquals("456", res.orders.get(1));
            }

            @Override
            public void onError(TaxjarException error)
            {
            }
        });
    }

    public void testShowOrder() throws TaxjarException {
        MockInterceptor interceptor = new MockInterceptor("orders/show.json");
        client = new TaxjarMock("TEST", interceptor);

        OrderResponse res = client.showOrder("123");
        assertEquals("123", res.order.getTransactionId());
        assertEquals((Integer) 10649, res.order.getUserId());
        assertEquals("2015-05-14T00:00:00Z", res.order.getTransactionDate());
        assertEquals("non_exempt", res.order.getExemptionType());
        assertEquals("US", res.order.getToCountry());
        assertEquals("90002", res.order.getToZip());
        assertEquals("CA", res.order.getToState());
        assertEquals("LOS ANGELES", res.order.getToCity());
        assertEquals("123 Palm Grove Ln", res.order.getToStreet());
        assertEquals(17f, res.order.getAmount());
        assertEquals(2f, res.order.getShipping());
        assertEquals(0.95f, res.order.getSalesTax());
        assertEquals("1", res.order.getLineItems().get(0).getId());
        assertEquals((Integer) 1, res.order.getLineItems().get(0).getQuantity());
        assertEquals("12-34243-0", res.order.getLineItems().get(0).getProductIdentifier());
        assertEquals("Heavy Widget", res.order.getLineItems().get(0).getDescription());
        assertEquals("20010", res.order.getLineItems().get(0).getProductTaxCode());
        assertEquals(15f, res.order.getLineItems().get(0).getUnitPrice());
        assertEquals(0f, res.order.getLineItems().get(0).getDiscount());
        assertEquals(0.95f, res.order.getLineItems().get(0).getSalesTax());
    }

    public void testShowOrderAsync() throws TaxjarException {
        MockInterceptor interceptor = new MockInterceptor("orders/show.json");
        client = new TaxjarMock("TEST", interceptor);

        client.showOrder("123", new Listener<OrderResponse>() {
            @Override
            public void onSuccess(OrderResponse res)
            {
                assertEquals("123", res.order.getTransactionId());
                assertEquals((Integer) 10649, res.order.getUserId());
                assertEquals("2015-05-14T00:00:00Z", res.order.getTransactionDate());
                assertEquals("non_exempt", res.order.getExemptionType());
                assertEquals("US", res.order.getToCountry());
                assertEquals("90002", res.order.getToZip());
                assertEquals("CA", res.order.getToState());
                assertEquals("LOS ANGELES", res.order.getToCity());
                assertEquals("123 Palm Grove Ln", res.order.getToStreet());
                assertEquals(17f, res.order.getAmount());
                assertEquals(2f, res.order.getShipping());
                assertEquals(0.95f, res.order.getSalesTax());
                assertEquals("1", res.order.getLineItems().get(0).getId());
                assertEquals((Integer) 1, res.order.getLineItems().get(0).getQuantity());
                assertEquals("12-34243-0", res.order.getLineItems().get(0).getProductIdentifier());
                assertEquals("Heavy Widget", res.order.getLineItems().get(0).getDescription());
                assertEquals("20010", res.order.getLineItems().get(0).getProductTaxCode());
                assertEquals(15f, res.order.getLineItems().get(0).getUnitPrice());
                assertEquals(0f, res.order.getLineItems().get(0).getDiscount());
                assertEquals(0.95f, res.order.getLineItems().get(0).getSalesTax());
            }

            @Override
            public void onError(TaxjarException error)
            {
            }
        });
    }

    public void testCreateOrder() throws TaxjarException {
        MockInterceptor interceptor = new MockInterceptor("orders/show.json");
        client = new TaxjarMock("TEST", interceptor);

        Map<String, Object> params = new HashMap<>();
        params.put("transaction_id", "123");
        params.put("transaction_date", "2015/05/04");
        params.put("exemption_type", "non_exempt");
        params.put("to_country", "US");
        params.put("to_zip", "90002");
        params.put("to_city", "Los Angeles");
        params.put("to_street", "123 Palm Grove Ln");
        params.put("amount", 15);
        params.put("shipping", 1.5);
        params.put("sales_tax", 0.95);

        List<Map> lineItems = new ArrayList();
        Map<String, Object> lineItem = new HashMap<>();
        lineItem.put("quantity", 1);
        lineItem.put("product_identifier", "12-342-43-0");
        lineItem.put("description", "Heavy Widget");
        lineItem.put("unit_price", 15);
        lineItem.put("sales_tax", 0.95);
        lineItems.add(lineItem);

        params.put("line_items", lineItems);

        OrderResponse res = client.createOrder(params);
        assertEquals("123", res.order.getTransactionId());
        assertEquals((Integer) 10649, res.order.getUserId());
        assertEquals("2015-05-14T00:00:00Z", res.order.getTransactionDate());
        assertEquals("non_exempt", res.order.getExemptionType());
        assertEquals("US", res.order.getToCountry());
        assertEquals("90002", res.order.getToZip());
        assertEquals("CA", res.order.getToState());
        assertEquals("LOS ANGELES", res.order.getToCity());
        assertEquals("123 Palm Grove Ln", res.order.getToStreet());
        assertEquals(17f, res.order.getAmount());
        assertEquals(2f, res.order.getShipping());
        assertEquals(0.95f, res.order.getSalesTax());
        assertEquals("1", res.order.getLineItems().get(0).getId());
        assertEquals((Integer) 1, res.order.getLineItems().get(0).getQuantity());
        assertEquals("12-34243-0", res.order.getLineItems().get(0).getProductIdentifier());
        assertEquals("Heavy Widget", res.order.getLineItems().get(0).getDescription());
        assertEquals("20010", res.order.getLineItems().get(0).getProductTaxCode());
        assertEquals(15f, res.order.getLineItems().get(0).getUnitPrice());
        assertEquals(0f, res.order.getLineItems().get(0).getDiscount());
        assertEquals(0.95f, res.order.getLineItems().get(0).getSalesTax());
    }

    public void testCreateOrderAsync() throws TaxjarException {
        MockInterceptor interceptor = new MockInterceptor("orders/show.json");
        client = new TaxjarMock("TEST", interceptor);

        Map<String, Object> params = new HashMap<>();
        params.put("transaction_id", "123");
        params.put("transaction_date", "2015/05/04");
        params.put("exemption_type", "non_exempt");
        params.put("to_country", "US");
        params.put("to_zip", "90002");
        params.put("to_city", "Los Angeles");
        params.put("to_street", "123 Palm Grove Ln");
        params.put("amount", 15);
        params.put("shipping", 1.5);
        params.put("sales_tax", 0.95);

        List<Map> lineItems = new ArrayList();
        Map<String, Object> lineItem = new HashMap<>();
        lineItem.put("quantity", 1);
        lineItem.put("product_identifier", "12-342-43-0");
        lineItem.put("description", "Heavy Widget");
        lineItem.put("unit_price", 15);
        lineItem.put("sales_tax", 0.95);
        lineItems.add(lineItem);

        params.put("line_items", lineItems);

        client.createOrder(params, new Listener<OrderResponse>() {
            @Override
            public void onSuccess(OrderResponse res)
            {
                assertEquals("123", res.order.getTransactionId());
                assertEquals((Integer) 10649, res.order.getUserId());
                assertEquals("2015-05-14T00:00:00Z", res.order.getTransactionDate());
                assertEquals("non_exempt", res.order.getExemptionType());
                assertEquals("US", res.order.getToCountry());
                assertEquals("90002", res.order.getToZip());
                assertEquals("CA", res.order.getToState());
                assertEquals("LOS ANGELES", res.order.getToCity());
                assertEquals("123 Palm Grove Ln", res.order.getToStreet());
                assertEquals(17f, res.order.getAmount());
                assertEquals(2f, res.order.getShipping());
                assertEquals(0.95f, res.order.getSalesTax());
                assertEquals("1", res.order.getLineItems().get(0).getId());
                assertEquals((Integer) 1, res.order.getLineItems().get(0).getQuantity());
                assertEquals("12-34243-0", res.order.getLineItems().get(0).getProductIdentifier());
                assertEquals("Heavy Widget", res.order.getLineItems().get(0).getDescription());
                assertEquals("20010", res.order.getLineItems().get(0).getProductTaxCode());
                assertEquals(15f, res.order.getLineItems().get(0).getUnitPrice());
                assertEquals(0f, res.order.getLineItems().get(0).getDiscount());
                assertEquals(0.95f, res.order.getLineItems().get(0).getSalesTax());
            }

            @Override
            public void onError(TaxjarException error)
            {
            }
        });
    }

    public void testUpdateOrder() throws TaxjarException {
        MockInterceptor interceptor = new MockInterceptor("orders/show.json");
        client = new TaxjarMock("TEST", interceptor);

        Map<String, Object> params = new HashMap<>();
        params.put("transaction_id", "123");
        params.put("transaction_date", "2015/05/04");
        params.put("exemption_type", "non_exempt");
        params.put("to_country", "US");
        params.put("to_zip", "90002");
        params.put("to_city", "Los Angeles");
        params.put("to_street", "123 Palm Grove Ln");
        params.put("amount", 15);
        params.put("shipping", 1.5);
        params.put("sales_tax", 0.95);

        List<Map> lineItems = new ArrayList();
        Map<String, Object> lineItem = new HashMap<>();
        lineItem.put("quantity", 1);
        lineItem.put("product_identifier", "12-342-43-0");
        lineItem.put("description", "Heavy Widget");
        lineItem.put("unit_price", 15);
        lineItem.put("sales_tax", 0.95);
        lineItems.add(lineItem);

        params.put("line_items", lineItems);

        OrderResponse res = client.updateOrder("123", params);
        assertEquals("123", res.order.getTransactionId());
        assertEquals((Integer) 10649, res.order.getUserId());
        assertEquals("2015-05-14T00:00:00Z", res.order.getTransactionDate());
        assertEquals("non_exempt", res.order.getExemptionType());
        assertEquals("US", res.order.getToCountry());
        assertEquals("90002", res.order.getToZip());
        assertEquals("CA", res.order.getToState());
        assertEquals("LOS ANGELES", res.order.getToCity());
        assertEquals("123 Palm Grove Ln", res.order.getToStreet());
        assertEquals(17f, res.order.getAmount());
        assertEquals(2f, res.order.getShipping());
        assertEquals(0.95f, res.order.getSalesTax());
        assertEquals("1", res.order.getLineItems().get(0).getId());
        assertEquals((Integer) 1, res.order.getLineItems().get(0).getQuantity());
        assertEquals("12-34243-0", res.order.getLineItems().get(0).getProductIdentifier());
        assertEquals("Heavy Widget", res.order.getLineItems().get(0).getDescription());
        assertEquals("20010", res.order.getLineItems().get(0).getProductTaxCode());
        assertEquals(15f, res.order.getLineItems().get(0).getUnitPrice());
        assertEquals(0f, res.order.getLineItems().get(0).getDiscount());
        assertEquals(0.95f, res.order.getLineItems().get(0).getSalesTax());
    }

    public void testUpdateOrderAsync() throws TaxjarException {
        MockInterceptor interceptor = new MockInterceptor("orders/show.json");
        client = new TaxjarMock("TEST", interceptor);

        Map<String, Object> params = new HashMap<>();
        params.put("transaction_id", "123");
        params.put("transaction_date", "2015/05/04");
        params.put("exemption_type", "non_exempt");
        params.put("to_country", "US");
        params.put("to_zip", "90002");
        params.put("to_city", "Los Angeles");
        params.put("to_street", "123 Palm Grove Ln");
        params.put("amount", 15);
        params.put("shipping", 1.5);
        params.put("sales_tax", 0.95);

        List<Map> lineItems = new ArrayList();
        Map<String, Object> lineItem = new HashMap<>();
        lineItem.put("quantity", 1);
        lineItem.put("product_identifier", "12-342-43-0");
        lineItem.put("description", "Heavy Widget");
        lineItem.put("unit_price", 15);
        lineItem.put("sales_tax", 0.95);
        lineItems.add(lineItem);

        params.put("line_items", lineItems);

        client.updateOrder("123", params, new Listener<OrderResponse>() {
            @Override
            public void onSuccess(OrderResponse res)
            {
                assertEquals("123", res.order.getTransactionId());
                assertEquals((Integer) 10649, res.order.getUserId());
                assertEquals("2015-05-14T00:00:00Z", res.order.getTransactionDate());
                assertEquals("non_exempt", res.order.getExemptionType());
                assertEquals("US", res.order.getToCountry());
                assertEquals("90002", res.order.getToZip());
                assertEquals("CA", res.order.getToState());
                assertEquals("LOS ANGELES", res.order.getToCity());
                assertEquals("123 Palm Grove Ln", res.order.getToStreet());
                assertEquals(17f, res.order.getAmount());
                assertEquals(2f, res.order.getShipping());
                assertEquals(0.95f, res.order.getSalesTax());
                assertEquals("1", res.order.getLineItems().get(0).getId());
                assertEquals((Integer) 1, res.order.getLineItems().get(0).getQuantity());
                assertEquals("12-34243-0", res.order.getLineItems().get(0).getProductIdentifier());
                assertEquals("Heavy Widget", res.order.getLineItems().get(0).getDescription());
                assertEquals("20010", res.order.getLineItems().get(0).getProductTaxCode());
                assertEquals(15f, res.order.getLineItems().get(0).getUnitPrice());
                assertEquals(0f, res.order.getLineItems().get(0).getDiscount());
                assertEquals(0.95f, res.order.getLineItems().get(0).getSalesTax());
            }

            @Override
            public void onError(TaxjarException error)
            {
            }
        });
    }

    public void testDeleteOrder() throws TaxjarException {
        MockInterceptor interceptor = new MockInterceptor("orders/delete.json");
        client = new TaxjarMock("TEST", interceptor);

        OrderResponse res = client.deleteOrder("123");
        assertEquals("123", res.order.getTransactionId());
        assertEquals((Integer) 10649, res.order.getUserId());
        assertEquals(null, res.order.getTransactionDate());
        assertEquals(null, res.order.getExemptionType());
        assertEquals(null, res.order.getToCountry());
        assertEquals(null, res.order.getToZip());
        assertEquals(null, res.order.getToState());
        assertEquals(null, res.order.getToCity());
        assertEquals(null, res.order.getToStreet());
        assertEquals(null, res.order.getAmount());
        assertEquals(null, res.order.getShipping());
        assertEquals(null, res.order.getSalesTax());
        assertEquals(Collections.emptyList(), res.order.getLineItems());
    }

    public void testDeleteOrderAsync() throws TaxjarException {
        MockInterceptor interceptor = new MockInterceptor("orders/delete.json");
        client = new TaxjarMock("TEST", interceptor);

        client.deleteOrder("123", new Listener<OrderResponse>() {
            @Override
            public void onSuccess(OrderResponse res)
            {
                assertEquals("123", res.order.getTransactionId());
                assertEquals((Integer) 10649, res.order.getUserId());
                assertEquals(null, res.order.getTransactionDate());
                assertEquals(null, res.order.getExemptionType());
                assertEquals(null, res.order.getToCountry());
                assertEquals(null, res.order.getToZip());
                assertEquals(null, res.order.getToState());
                assertEquals(null, res.order.getToCity());
                assertEquals(null, res.order.getToStreet());
                assertEquals(null, res.order.getAmount());
                assertEquals(null, res.order.getShipping());
                assertEquals(null, res.order.getSalesTax());
                assertEquals(Collections.emptyList(), res.order.getLineItems());
            }

            @Override
            public void onError(TaxjarException error)
            {
            }
        });
    }
}
