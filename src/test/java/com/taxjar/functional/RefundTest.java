package com.taxjar.functional;

import com.taxjar.MockInterceptor;
import com.taxjar.TaxjarMock;
import com.taxjar.exception.TaxjarException;
import com.taxjar.model.transactions.RefundResponse;
import com.taxjar.model.transactions.RefundsResponse;
import com.taxjar.net.Listener;
import junit.framework.TestCase;

import java.util.*;

public class RefundTest extends TestCase {
    private TaxjarMock client;

    public void testListRefunds() throws TaxjarException {
        MockInterceptor interceptor = new MockInterceptor("refunds/list.json");
        client = new TaxjarMock("TEST", interceptor);

        RefundsResponse res = client.listRefunds();
        assertEquals("321", res.refunds.get(0));
        assertEquals("654", res.refunds.get(1));
    }

    public void testListRefundsWithParams() throws TaxjarException {
        MockInterceptor interceptor = new MockInterceptor("refunds/list.json");
        client = new TaxjarMock("TEST", interceptor);

        Map<String, String> params = new HashMap<>();
        params.put("from_transaction_date", "2015/05/01");
        params.put("to_transaction_date", "2015/05/31");
        params.put("provider", "api");

        RefundsResponse res = client.listRefunds(params);
        assertEquals("321", res.refunds.get(0));
        assertEquals("654", res.refunds.get(1));
    }

    public void testListRefundsAsync() throws TaxjarException {
        MockInterceptor interceptor = new MockInterceptor("refunds/list.json");
        client = new TaxjarMock("TEST", interceptor);

        client.listRefunds(new Listener<RefundsResponse>() {
            @Override
            public void onSuccess(RefundsResponse res)
            {
                assertEquals("321", res.refunds.get(0));
                assertEquals("654", res.refunds.get(1));
            }

            @Override
            public void onError(TaxjarException error)
            {
            }
        });
    }

    public void testListRefundsAsyncWithParams() throws TaxjarException {
        MockInterceptor interceptor = new MockInterceptor("refunds/list.json");
        client = new TaxjarMock("TEST", interceptor);

        Map<String, String> params = new HashMap<>();
        params.put("from_transaction_date", "2015/05/01");
        params.put("to_transaction_date", "2015/05/31");
        params.put("provider", "api");

        client.listRefunds(params, new Listener<RefundsResponse>() {
            @Override
            public void onSuccess(RefundsResponse res)
            {
                assertEquals("321", res.refunds.get(0));
                assertEquals("654", res.refunds.get(1));
            }

            @Override
            public void onError(TaxjarException error)
            {
            }
        });
    }

    public void testShowRefund() throws TaxjarException {
        MockInterceptor interceptor = new MockInterceptor("refunds/show.json");
        client = new TaxjarMock("TEST", interceptor);

        RefundResponse res = client.showRefund("321");
        assertEquals("321", res.refund.getTransactionId());
        assertEquals((Integer) 10649, res.refund.getUserId());
        assertEquals("2015-05-14T00:00:00Z", res.refund.getTransactionDate());
        assertEquals("123", res.refund.getTransactionReferenceId());
        assertEquals("api", res.refund.getProvider());
        assertEquals("non_exempt", res.refund.getExemptionType());
        assertEquals("US", res.refund.getToCountry());
        assertEquals("90002", res.refund.getToZip());
        assertEquals("CA", res.refund.getToState());
        assertEquals("LOS ANGELES", res.refund.getToCity());
        assertEquals("123 Palm Grove Ln", res.refund.getToStreet());
        assertEquals(17f, res.refund.getAmount());
        assertEquals(2f, res.refund.getShipping());
        assertEquals(0.95f, res.refund.getSalesTax());
        assertEquals("1", res.refund.getLineItems().get(0).getId());
        assertEquals((Integer) 1, res.refund.getLineItems().get(0).getQuantity());
        assertEquals("12-34243-0", res.refund.getLineItems().get(0).getProductIdentifier());
        assertEquals("Heavy Widget", res.refund.getLineItems().get(0).getDescription());
        assertEquals("20010", res.refund.getLineItems().get(0).getProductTaxCode());
        assertEquals(15f, res.refund.getLineItems().get(0).getUnitPrice());
        assertEquals(0f, res.refund.getLineItems().get(0).getDiscount());
        assertEquals(0.95f, res.refund.getLineItems().get(0).getSalesTax());
    }

    public void testShowRefundWithParams() throws TaxjarException {
        MockInterceptor interceptor = new MockInterceptor("refunds/show.json");
        client = new TaxjarMock("TEST", interceptor);

        Map<String, String> params = new HashMap<>();
        params.put("provider", "api");

        RefundResponse res = client.showRefund("321", params);
        assertEquals("321", res.refund.getTransactionId());
        assertEquals((Integer) 10649, res.refund.getUserId());
        assertEquals("2015-05-14T00:00:00Z", res.refund.getTransactionDate());
        assertEquals("123", res.refund.getTransactionReferenceId());
        assertEquals("api", res.refund.getProvider());
        assertEquals("non_exempt", res.refund.getExemptionType());
        assertEquals("US", res.refund.getToCountry());
        assertEquals("90002", res.refund.getToZip());
        assertEquals("CA", res.refund.getToState());
        assertEquals("LOS ANGELES", res.refund.getToCity());
        assertEquals("123 Palm Grove Ln", res.refund.getToStreet());
        assertEquals(17f, res.refund.getAmount());
        assertEquals(2f, res.refund.getShipping());
        assertEquals(0.95f, res.refund.getSalesTax());
        assertEquals("1", res.refund.getLineItems().get(0).getId());
        assertEquals((Integer) 1, res.refund.getLineItems().get(0).getQuantity());
        assertEquals("12-34243-0", res.refund.getLineItems().get(0).getProductIdentifier());
        assertEquals("Heavy Widget", res.refund.getLineItems().get(0).getDescription());
        assertEquals("20010", res.refund.getLineItems().get(0).getProductTaxCode());
        assertEquals(15f, res.refund.getLineItems().get(0).getUnitPrice());
        assertEquals(0f, res.refund.getLineItems().get(0).getDiscount());
        assertEquals(0.95f, res.refund.getLineItems().get(0).getSalesTax());
    }

    public void testShowRefundAsync() throws TaxjarException {
        MockInterceptor interceptor = new MockInterceptor("refunds/show.json");
        client = new TaxjarMock("TEST", interceptor);

        client.showRefund("321", new Listener<RefundResponse>() {
            @Override
            public void onSuccess(RefundResponse res)
            {
                assertEquals("321", res.refund.getTransactionId());
                assertEquals((Integer) 10649, res.refund.getUserId());
                assertEquals("2015-05-14T00:00:00Z", res.refund.getTransactionDate());
                assertEquals("123", res.refund.getTransactionReferenceId());
                assertEquals("api", res.refund.getProvider());
                assertEquals("non_exempt", res.refund.getExemptionType());
                assertEquals("US", res.refund.getToCountry());
                assertEquals("90002", res.refund.getToZip());
                assertEquals("CA", res.refund.getToState());
                assertEquals("LOS ANGELES", res.refund.getToCity());
                assertEquals("123 Palm Grove Ln", res.refund.getToStreet());
                assertEquals(17f, res.refund.getAmount());
                assertEquals(2f, res.refund.getShipping());
                assertEquals(0.95f, res.refund.getSalesTax());
                assertEquals("1", res.refund.getLineItems().get(0).getId());
                assertEquals((Integer) 1, res.refund.getLineItems().get(0).getQuantity());
                assertEquals("12-34243-0", res.refund.getLineItems().get(0).getProductIdentifier());
                assertEquals("Heavy Widget", res.refund.getLineItems().get(0).getDescription());
                assertEquals("20010", res.refund.getLineItems().get(0).getProductTaxCode());
                assertEquals(15f, res.refund.getLineItems().get(0).getUnitPrice());
                assertEquals(0f, res.refund.getLineItems().get(0).getDiscount());
                assertEquals(0.95f, res.refund.getLineItems().get(0).getSalesTax());
            }

            @Override
            public void onError(TaxjarException error)
            {
            }
        });
    }

    public void testShowRefundAsyncWithParams() throws TaxjarException {
        MockInterceptor interceptor = new MockInterceptor("refunds/show.json");
        client = new TaxjarMock("TEST", interceptor);

        Map<String, String> params = new HashMap<>();
        params.put("provider", "api");

        client.showRefund("321", params, new Listener<RefundResponse>() {
            @Override
            public void onSuccess(RefundResponse res) {
                assertEquals("321", res.refund.getTransactionId());
                assertEquals((Integer) 10649, res.refund.getUserId());
                assertEquals("2015-05-14T00:00:00Z", res.refund.getTransactionDate());
                assertEquals("123", res.refund.getTransactionReferenceId());
                assertEquals("api", res.refund.getProvider());
                assertEquals("non_exempt", res.refund.getExemptionType());
                assertEquals("US", res.refund.getToCountry());
                assertEquals("90002", res.refund.getToZip());
                assertEquals("CA", res.refund.getToState());
                assertEquals("LOS ANGELES", res.refund.getToCity());
                assertEquals("123 Palm Grove Ln", res.refund.getToStreet());
                assertEquals(17f, res.refund.getAmount());
                assertEquals(2f, res.refund.getShipping());
                assertEquals(0.95f, res.refund.getSalesTax());
                assertEquals("1", res.refund.getLineItems().get(0).getId());
                assertEquals((Integer) 1, res.refund.getLineItems().get(0).getQuantity());
                assertEquals("12-34243-0", res.refund.getLineItems().get(0).getProductIdentifier());
                assertEquals("Heavy Widget", res.refund.getLineItems().get(0).getDescription());
                assertEquals("20010", res.refund.getLineItems().get(0).getProductTaxCode());
                assertEquals(15f, res.refund.getLineItems().get(0).getUnitPrice());
                assertEquals(0f, res.refund.getLineItems().get(0).getDiscount());
                assertEquals(0.95f, res.refund.getLineItems().get(0).getSalesTax());
            }

            @Override
            public void onError(TaxjarException error) {
            }
        });
    }

    public void testCreateRefund() throws TaxjarException {
        MockInterceptor interceptor = new MockInterceptor("refunds/show.json");
        client = new TaxjarMock("TEST", interceptor);

        Map<String, Object> params = new HashMap<>();
        params.put("transaction_id", "321");
        params.put("transaction_date", "2015/05/04");
        params.put("provider", "api");
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

        RefundResponse res = client.createRefund(params);
        assertEquals("321", res.refund.getTransactionId());
        assertEquals((Integer) 10649, res.refund.getUserId());
        assertEquals("2015-05-14T00:00:00Z", res.refund.getTransactionDate());
        assertEquals("123", res.refund.getTransactionReferenceId());
        assertEquals("api", res.refund.getProvider());
        assertEquals("non_exempt", res.refund.getExemptionType());
        assertEquals("US", res.refund.getToCountry());
        assertEquals("90002", res.refund.getToZip());
        assertEquals("CA", res.refund.getToState());
        assertEquals("LOS ANGELES", res.refund.getToCity());
        assertEquals("123 Palm Grove Ln", res.refund.getToStreet());
        assertEquals(17f, res.refund.getAmount());
        assertEquals(2f, res.refund.getShipping());
        assertEquals(0.95f, res.refund.getSalesTax());
        assertEquals("1", res.refund.getLineItems().get(0).getId());
        assertEquals((Integer) 1, res.refund.getLineItems().get(0).getQuantity());
        assertEquals("12-34243-0", res.refund.getLineItems().get(0).getProductIdentifier());
        assertEquals("Heavy Widget", res.refund.getLineItems().get(0).getDescription());
        assertEquals("20010", res.refund.getLineItems().get(0).getProductTaxCode());
        assertEquals(15f, res.refund.getLineItems().get(0).getUnitPrice());
        assertEquals(0f, res.refund.getLineItems().get(0).getDiscount());
        assertEquals(0.95f, res.refund.getLineItems().get(0).getSalesTax());
    }

    public void testCreateRefundAsync() throws TaxjarException {
        MockInterceptor interceptor = new MockInterceptor("refunds/show.json");
        client = new TaxjarMock("TEST", interceptor);

        Map<String, Object> params = new HashMap<>();
        params.put("transaction_id", "321");
        params.put("transaction_date", "2015/05/04");
        params.put("provider", "api");
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

        client.createRefund(params, new Listener<RefundResponse>() {
            @Override
            public void onSuccess(RefundResponse res)
            {
                assertEquals("321", res.refund.getTransactionId());
                assertEquals((Integer) 10649, res.refund.getUserId());
                assertEquals("2015-05-14T00:00:00Z", res.refund.getTransactionDate());
                assertEquals("123", res.refund.getTransactionReferenceId());
                assertEquals("api", res.refund.getProvider());
                assertEquals("non_exempt", res.refund.getExemptionType());
                assertEquals("US", res.refund.getToCountry());
                assertEquals("90002", res.refund.getToZip());
                assertEquals("CA", res.refund.getToState());
                assertEquals("LOS ANGELES", res.refund.getToCity());
                assertEquals("123 Palm Grove Ln", res.refund.getToStreet());
                assertEquals(17f, res.refund.getAmount());
                assertEquals(2f, res.refund.getShipping());
                assertEquals(0.95f, res.refund.getSalesTax());
                assertEquals("1", res.refund.getLineItems().get(0).getId());
                assertEquals((Integer) 1, res.refund.getLineItems().get(0).getQuantity());
                assertEquals("12-34243-0", res.refund.getLineItems().get(0).getProductIdentifier());
                assertEquals("Heavy Widget", res.refund.getLineItems().get(0).getDescription());
                assertEquals("20010", res.refund.getLineItems().get(0).getProductTaxCode());
                assertEquals(15f, res.refund.getLineItems().get(0).getUnitPrice());
                assertEquals(0f, res.refund.getLineItems().get(0).getDiscount());
                assertEquals(0.95f, res.refund.getLineItems().get(0).getSalesTax());
            }

            @Override
            public void onError(TaxjarException error)
            {
            }
        });
    }

    public void testUpdateRefund() throws TaxjarException {
        MockInterceptor interceptor = new MockInterceptor("refunds/show.json");
        client = new TaxjarMock("TEST", interceptor);

        Map<String, Object> params = new HashMap<>();
        params.put("transaction_id", "321");
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

        RefundResponse res = client.updateRefund("321", params);
        assertEquals("321", res.refund.getTransactionId());
        assertEquals((Integer) 10649, res.refund.getUserId());
        assertEquals("2015-05-14T00:00:00Z", res.refund.getTransactionDate());
        assertEquals("123", res.refund.getTransactionReferenceId());
        assertEquals("api", res.refund.getProvider());
        assertEquals("non_exempt", res.refund.getExemptionType());
        assertEquals("US", res.refund.getToCountry());
        assertEquals("90002", res.refund.getToZip());
        assertEquals("CA", res.refund.getToState());
        assertEquals("LOS ANGELES", res.refund.getToCity());
        assertEquals("123 Palm Grove Ln", res.refund.getToStreet());
        assertEquals(17f, res.refund.getAmount());
        assertEquals(2f, res.refund.getShipping());
        assertEquals(0.95f, res.refund.getSalesTax());
        assertEquals("1", res.refund.getLineItems().get(0).getId());
        assertEquals((Integer) 1, res.refund.getLineItems().get(0).getQuantity());
        assertEquals("12-34243-0", res.refund.getLineItems().get(0).getProductIdentifier());
        assertEquals("Heavy Widget", res.refund.getLineItems().get(0).getDescription());
        assertEquals("20010", res.refund.getLineItems().get(0).getProductTaxCode());
        assertEquals(15f, res.refund.getLineItems().get(0).getUnitPrice());
        assertEquals(0f, res.refund.getLineItems().get(0).getDiscount());
        assertEquals(0.95f, res.refund.getLineItems().get(0).getSalesTax());
    }

    public void testUpdateRefundAsync() throws TaxjarException {
        MockInterceptor interceptor = new MockInterceptor("refunds/show.json");
        client = new TaxjarMock("TEST", interceptor);

        Map<String, Object> params = new HashMap<>();
        params.put("transaction_id", "321");
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

        client.updateRefund("321", params, new Listener<RefundResponse>() {
            @Override
            public void onSuccess(RefundResponse res)
            {
                assertEquals("321", res.refund.getTransactionId());
                assertEquals((Integer) 10649, res.refund.getUserId());
                assertEquals("2015-05-14T00:00:00Z", res.refund.getTransactionDate());
                assertEquals("123", res.refund.getTransactionReferenceId());
                assertEquals("api", res.refund.getProvider());
                assertEquals("non_exempt", res.refund.getExemptionType());
                assertEquals("US", res.refund.getToCountry());
                assertEquals("90002", res.refund.getToZip());
                assertEquals("CA", res.refund.getToState());
                assertEquals("LOS ANGELES", res.refund.getToCity());
                assertEquals("123 Palm Grove Ln", res.refund.getToStreet());
                assertEquals(17f, res.refund.getAmount());
                assertEquals(2f, res.refund.getShipping());
                assertEquals(0.95f, res.refund.getSalesTax());
                assertEquals("1", res.refund.getLineItems().get(0).getId());
                assertEquals((Integer) 1, res.refund.getLineItems().get(0).getQuantity());
                assertEquals("12-34243-0", res.refund.getLineItems().get(0).getProductIdentifier());
                assertEquals("Heavy Widget", res.refund.getLineItems().get(0).getDescription());
                assertEquals("20010", res.refund.getLineItems().get(0).getProductTaxCode());
                assertEquals(15f, res.refund.getLineItems().get(0).getUnitPrice());
                assertEquals(0f, res.refund.getLineItems().get(0).getDiscount());
                assertEquals(0.95f, res.refund.getLineItems().get(0).getSalesTax());
            }

            @Override
            public void onError(TaxjarException error)
            {
            }
        });
    }

    public void testDeleteRefund() throws TaxjarException {
        MockInterceptor interceptor = new MockInterceptor("refunds/delete.json");
        client = new TaxjarMock("TEST", interceptor);

        RefundResponse res = client.deleteRefund("321");
        assertEquals("321", res.refund.getTransactionId());
        assertEquals((Integer) 10649, res.refund.getUserId());
        assertEquals(null, res.refund.getTransactionDate());
        assertEquals("api", res.refund.getProvider());
        assertEquals(null, res.refund.getExemptionType());
        assertEquals(null, res.refund.getToCountry());
        assertEquals(null, res.refund.getToZip());
        assertEquals(null, res.refund.getToState());
        assertEquals(null, res.refund.getToCity());
        assertEquals(null, res.refund.getToStreet());
        assertEquals(null, res.refund.getAmount());
        assertEquals(null, res.refund.getShipping());
        assertEquals(null, res.refund.getSalesTax());
        assertEquals(Collections.emptyList(), res.refund.getLineItems());
    }

    public void testDeleteRefundWithParams() throws TaxjarException {
        MockInterceptor interceptor = new MockInterceptor("refunds/delete.json");
        client = new TaxjarMock("TEST", interceptor);

        Map<String, String> params = new HashMap<>();
        params.put("provider", "api");

        RefundResponse res = client.deleteRefund("321", params);
        assertEquals("321", res.refund.getTransactionId());
        assertEquals((Integer) 10649, res.refund.getUserId());
        assertEquals(null, res.refund.getTransactionDate());
        assertEquals("api", res.refund.getProvider());
        assertEquals(null, res.refund.getExemptionType());
        assertEquals(null, res.refund.getToCountry());
        assertEquals(null, res.refund.getToZip());
        assertEquals(null, res.refund.getToState());
        assertEquals(null, res.refund.getToCity());
        assertEquals(null, res.refund.getToStreet());
        assertEquals(null, res.refund.getAmount());
        assertEquals(null, res.refund.getShipping());
        assertEquals(null, res.refund.getSalesTax());
        assertEquals(Collections.emptyList(), res.refund.getLineItems());
    }

    public void testDeleteRefundAsync() throws TaxjarException {
        MockInterceptor interceptor = new MockInterceptor("refunds/delete.json");
        client = new TaxjarMock("TEST", interceptor);

        client.deleteRefund("321", new Listener<RefundResponse>() {
            @Override
            public void onSuccess(RefundResponse res)
            {
                assertEquals("321", res.refund.getTransactionId());
                assertEquals((Integer) 10649, res.refund.getUserId());
                assertEquals(null, res.refund.getTransactionDate());
                assertEquals("api", res.refund.getProvider());
                assertEquals(null, res.refund.getExemptionType());
                assertEquals(null, res.refund.getToCountry());
                assertEquals(null, res.refund.getToZip());
                assertEquals(null, res.refund.getToState());
                assertEquals(null, res.refund.getToCity());
                assertEquals(null, res.refund.getToStreet());
                assertEquals(null, res.refund.getAmount());
                assertEquals(null, res.refund.getShipping());
                assertEquals(null, res.refund.getSalesTax());
                assertEquals(Collections.emptyList(), res.refund.getLineItems());
            }

            @Override
            public void onError(TaxjarException error)
            {
            }
        });
    }

    public void testDeleteRefundAsyncWithParams() throws TaxjarException {
        MockInterceptor interceptor = new MockInterceptor("refunds/delete.json");
        client = new TaxjarMock("TEST", interceptor);

        Map<String, String> params = new HashMap<>();
        params.put("provider", "api");

        client.deleteRefund("321", params, new Listener<RefundResponse>() {
            @Override
            public void onSuccess(RefundResponse res) {
                assertEquals("321", res.refund.getTransactionId());
                assertEquals((Integer) 10649, res.refund.getUserId());
                assertEquals(null, res.refund.getTransactionDate());
                assertEquals("api", res.refund.getProvider());
                assertEquals(null, res.refund.getExemptionType());
                assertEquals(null, res.refund.getToCountry());
                assertEquals(null, res.refund.getToZip());
                assertEquals(null, res.refund.getToState());
                assertEquals(null, res.refund.getToCity());
                assertEquals(null, res.refund.getToStreet());
                assertEquals(null, res.refund.getAmount());
                assertEquals(null, res.refund.getShipping());
                assertEquals(null, res.refund.getSalesTax());
                assertEquals(Collections.emptyList(), res.refund.getLineItems());
            }

            @Override
            public void onError(TaxjarException error) {
            }
        });
    }
}
