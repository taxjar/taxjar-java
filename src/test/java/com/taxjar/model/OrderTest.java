package com.taxjar.model;

import com.taxjar.MockInterceptor;
import com.taxjar.TaxjarMock;
import com.taxjar.exception.TaxjarException;
import com.taxjar.model.categories.CategoryResponse;
import com.taxjar.model.transactions.OrderResponse;
import com.taxjar.model.transactions.OrdersResponse;
import com.taxjar.net.Listener;
import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Map;

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
        assertEquals("US", res.order.getToCountry());
        assertEquals("90002", res.order.getToZip());
        assertEquals("CA", res.order.getToState());
        assertEquals("LOS ANGELES", res.order.getToCity());
        assertEquals("123 Palm Grove Ln", res.order.getToStreet());
        assertEquals(17.95f, res.order.getAmount());
        assertEquals(2f, res.order.getShipping());
        assertEquals(0.95f, res.order.getSalesTax());
        assertEquals("1", res.order.getLineItems().get(0).getId());
        assertEquals((Integer) 1, res.order.getLineItems().get(0).getQuantity());
        assertEquals("12-34243-0", res.order.getLineItems().get(0).getProductIdentifier());
        assertEquals("Heavy Widget", res.order.getLineItems().get(0).getDescription());
        assertEquals(15f, res.order.getLineItems().get(0).getUnitPrice());
        assertEquals(0f, res.order.getLineItems().get(0).getDiscount());
        assertEquals(0.95f, res.order.getLineItems().get(0).getSalesTax());
    }
}
