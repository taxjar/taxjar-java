package com.taxjar.functional;

import com.taxjar.MockInterceptor;
import com.taxjar.TaxjarMock;
import com.taxjar.exception.TaxjarException;
import com.taxjar.model.taxes.TaxResponse;
import com.taxjar.net.Listener;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaxTest extends TestCase {
    private TaxjarMock client;

    public void testTaxForOrder() throws TaxjarException {
        MockInterceptor interceptor = new MockInterceptor("taxes.json");
        client = new TaxjarMock("TEST", interceptor);

        Map<String, Object> params = new HashMap<>();
        params.put("from_country", "US");
        params.put("from_zip", "92093");
        params.put("from_state", "CA");
        params.put("from_city", "La Jolla");
        params.put("from_street", "9500 Gilman Drive");
        params.put("to_zip", "90002");
        params.put("to_state", "CA");
        params.put("to_city", "Los Angeles");
        params.put("to_street", "1335 E 103rd St");
        params.put("amount", 15);
        params.put("shipping", 1.5);

        List<Map> nexusAddresses = new ArrayList();
        Map<String, Object> nexusAddress = new HashMap<>();
        nexusAddress.put("country", "US");
        nexusAddress.put("zip", "92093");
        nexusAddress.put("state", "CA");
        nexusAddress.put("city", "La Jolla");
        nexusAddress.put("street", "9500 Gilman Drive");
        nexusAddresses.add(nexusAddress);

        List<Map> lineItems = new ArrayList();
        Map<String, Object> lineItem = new HashMap<>();
        lineItem.put("id", 1);
        lineItem.put("quantity", 1);
        lineItem.put("product_tax_code", "20010");
        lineItem.put("unit_price", 15);
        lineItem.put("discount", 0);
        lineItems.add(lineItem);

        params.put("nexus_addresses", nexusAddresses);
        params.put("line_items", lineItems);

        TaxResponse res = client.taxForOrder(params);
        assertEquals(16.5f, res.tax.getOrderTotalAmount());
        assertEquals(1.5f, res.tax.getShipping());
        assertEquals(16.5f, res.tax.getTaxableAmount());
        assertEquals(1.16f, res.tax.getAmountToCollect());
        assertEquals(0.07f, res.tax.getRate());
        assertEquals((Boolean) true, res.tax.getHasNexus());
        assertEquals((Boolean) true, res.tax.getFreightTaxable());
        assertEquals("destination", res.tax.getTaxSource());
        assertEquals(16.5f, res.tax.getBreakdown().getTaxableAmount());
        assertEquals(1.16f, res.tax.getBreakdown().getTaxCollectable());
        assertEquals(0.07f, res.tax.getBreakdown().getCombinedTaxRate());
        assertEquals(1.5f, res.tax.getBreakdown().getShipping().getTaxableAmount());
        assertEquals(0.11f, res.tax.getBreakdown().getShipping().getTaxCollectable());
        assertEquals(0.07f, res.tax.getBreakdown().getShipping().getCombinedTaxRate());
        assertEquals(15f, res.tax.getBreakdown().getLineItems().get(0).getTaxableAmount());
        assertEquals(1.05f, res.tax.getBreakdown().getLineItems().get(0).getTaxCollectable());
        assertEquals(0.07f, res.tax.getBreakdown().getLineItems().get(0).getCombinedTaxRate());
    }

    public void testTaxForOrderAsync() throws TaxjarException {
        MockInterceptor interceptor = new MockInterceptor("taxes.json");
        client = new TaxjarMock("TEST", interceptor);

        Map<String, Object> params = new HashMap<>();
        params.put("from_country", "US");
        params.put("from_zip", "92093");
        params.put("from_state", "CA");
        params.put("from_city", "La Jolla");
        params.put("from_street", "9500 Gilman Drive");
        params.put("to_country", "US");
        params.put("to_zip", "90002");
        params.put("to_state", "CA");
        params.put("to_city", "Los Angeles");
        params.put("to_street", "1335 E 103rd St");
        params.put("amount", 15);
        params.put("shipping", 1.5);

        List<Map> nexusAddresses = new ArrayList();
        Map<String, Object> nexusAddress = new HashMap<>();
        nexusAddress.put("country", "US");
        nexusAddress.put("zip", "92093");
        nexusAddress.put("state", "CA");
        nexusAddress.put("city", "La Jolla");
        nexusAddress.put("street", "9500 Gilman Drive");
        nexusAddresses.add(nexusAddress);

        List<Map> lineItems = new ArrayList();
        Map<String, Object> lineItem = new HashMap<>();
        lineItem.put("id", 1);
        lineItem.put("quantity", 1);
        lineItem.put("product_tax_code", "20010");
        lineItem.put("unit_price", 15);
        lineItem.put("discount", 0);
        lineItems.add(lineItem);

        params.put("nexus_addresses", nexusAddresses);
        params.put("line_items", lineItems);

        client.taxForOrder(params, new Listener<TaxResponse>() {
            @Override
            public void onSuccess(TaxResponse res)
            {
                assertEquals(16.5f, res.tax.getOrderTotalAmount());
                assertEquals(1.5f, res.tax.getShipping());
                assertEquals(16.5f, res.tax.getTaxableAmount());
                assertEquals(1.16f, res.tax.getAmountToCollect());
                assertEquals(0.07f, res.tax.getRate());
                assertEquals((Boolean) true, res.tax.getHasNexus());
                assertEquals((Boolean) true, res.tax.getFreightTaxable());
                assertEquals("destination", res.tax.getTaxSource());
                assertEquals(16.5f, res.tax.getBreakdown().getTaxableAmount());
                assertEquals(1.16f, res.tax.getBreakdown().getTaxCollectable());
                assertEquals(0.07f, res.tax.getBreakdown().getCombinedTaxRate());
                assertEquals(1.5f, res.tax.getBreakdown().getShipping().getTaxableAmount());
                assertEquals(0.11f, res.tax.getBreakdown().getShipping().getTaxCollectable());
                assertEquals(0.07f, res.tax.getBreakdown().getShipping().getCombinedTaxRate());
                assertEquals(15f, res.tax.getBreakdown().getLineItems().get(0).getTaxableAmount());
                assertEquals(1.05f, res.tax.getBreakdown().getLineItems().get(0).getTaxCollectable());
                assertEquals(0.07f, res.tax.getBreakdown().getLineItems().get(0).getCombinedTaxRate());
            }

            @Override
            public void onError(TaxjarException error)
            {
            }
        });
    }

    public void testTaxForOrderCA() throws TaxjarException {
        MockInterceptor interceptor = new MockInterceptor("taxes_ca.json");
        client = new TaxjarMock("TEST", interceptor);

        Map<String, Object> params = new HashMap<>();
        params.put("from_country", "CA");
        params.put("from_zip", "V6G 3E2");
        params.put("from_state", "BC");
        params.put("from_city", "Vancouver");
        params.put("from_street", "845 Avison Way");
        params.put("to_country", "CA");
        params.put("to_zip", "M5V 2T6");
        params.put("to_state", "ON");
        params.put("to_city", "Toronto");
        params.put("to_street", "301 Front St W");
        params.put("amount", 15);
        params.put("shipping", 1.5);

        List<Map> nexusAddresses = new ArrayList();
        Map<String, Object> nexusAddress = new HashMap<>();
        nexusAddress.put("country", "CA");
        nexusAddress.put("zip", "V6G 3E2");
        nexusAddress.put("state", "BC");
        nexusAddress.put("city", "Vancouver");
        nexusAddress.put("street", "845 Avison Way");
        nexusAddresses.add(nexusAddress);

        List<Map> lineItems = new ArrayList();
        Map<String, Object> lineItem = new HashMap<>();
        lineItem.put("id", 1);
        lineItem.put("quantity", 1);
        lineItem.put("unit_price", 15);
        lineItem.put("discount", 0);
        lineItems.add(lineItem);

        params.put("nexus_addresses", nexusAddresses);
        params.put("line_items", lineItems);

        TaxResponse res = client.taxForOrder(params);
        assertEquals(26.95f, res.tax.getOrderTotalAmount());
        assertEquals(10f, res.tax.getShipping());
        assertEquals(26.95f, res.tax.getTaxableAmount());
        assertEquals(3.5f, res.tax.getAmountToCollect());
        assertEquals(0.13f, res.tax.getRate());
        assertEquals((Boolean) true, res.tax.getHasNexus());
        assertEquals((Boolean) true, res.tax.getFreightTaxable());
        assertEquals("destination", res.tax.getTaxSource());
        assertEquals(26.95f, res.tax.getBreakdown().getTaxableAmount());
        assertEquals(3.5f, res.tax.getBreakdown().getTaxCollectable());
        assertEquals(0.13f, res.tax.getBreakdown().getCombinedTaxRate());
        assertEquals(10f, res.tax.getBreakdown().getShipping().getTaxableAmount());
        assertEquals(1.3f, res.tax.getBreakdown().getShipping().getTaxCollectable());
        assertEquals(0.13f, res.tax.getBreakdown().getShipping().getCombinedTaxRate());
        assertEquals(16.95f, res.tax.getBreakdown().getLineItems().get(0).getTaxableAmount());
        assertEquals(2.2f, res.tax.getBreakdown().getLineItems().get(0).getTaxCollectable());
        assertEquals(0.13f, res.tax.getBreakdown().getLineItems().get(0).getCombinedTaxRate());
    }

    public void testTaxForOrderEU() throws TaxjarException {
        MockInterceptor interceptor = new MockInterceptor("taxes_eu.json");
        client = new TaxjarMock("TEST", interceptor);

        Map<String, Object> params = new HashMap<>();
        params.put("from_country", "FI");
        params.put("from_zip", "00150");
        params.put("to_country", "FI");
        params.put("to_zip", "00150");
        params.put("amount", 16.95);
        params.put("shipping", 10);

        List<Map> lineItems = new ArrayList();
        Map<String, Object> lineItem = new HashMap<>();
        lineItem.put("id", 1);
        lineItem.put("quantity", 1);
        lineItem.put("unit_price", 16.95);
        lineItems.add(lineItem);

        params.put("line_items", lineItems);

        TaxResponse res = client.taxForOrder(params);
        assertEquals(26.95f, res.tax.getOrderTotalAmount());
        assertEquals(10f, res.tax.getShipping());
        assertEquals(26.95f, res.tax.getTaxableAmount());
        assertEquals(6.47f, res.tax.getAmountToCollect());
        assertEquals(0.24f, res.tax.getRate());
        assertEquals((Boolean) true, res.tax.getHasNexus());
        assertEquals((Boolean) true, res.tax.getFreightTaxable());
        assertEquals("destination", res.tax.getTaxSource());
        assertEquals(26.95f, res.tax.getBreakdown().getTaxableAmount());
        assertEquals(6.47f, res.tax.getBreakdown().getTaxCollectable());
        assertEquals(0.24f, res.tax.getBreakdown().getCombinedTaxRate());
        assertEquals(10f, res.tax.getBreakdown().getShipping().getTaxableAmount());
        assertEquals(2.4f, res.tax.getBreakdown().getShipping().getTaxCollectable());
        assertEquals(0.24f, res.tax.getBreakdown().getShipping().getCombinedTaxRate());
        assertEquals(16.95f, res.tax.getBreakdown().getLineItems().get(0).getTaxableAmount());
        assertEquals(4.07f, res.tax.getBreakdown().getLineItems().get(0).getTaxCollectable());
        assertEquals(0.24f, res.tax.getBreakdown().getLineItems().get(0).getCombinedTaxRate());
    }
}
