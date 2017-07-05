package com.taxjar.model;

import com.taxjar.MockInterceptor;
import com.taxjar.TaxjarMock;
import com.taxjar.exception.TaxjarException;
import com.taxjar.model.taxes.TaxResponse;
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
}
