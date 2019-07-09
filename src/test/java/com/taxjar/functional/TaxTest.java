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
        params.put("exemption_type", "non_exempt");

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
        assertEquals("non_exempt", res.tax.getExemptionType());
        assertEquals("US", res.tax.getJurisdictions().getCountry());
        assertEquals("CA", res.tax.getJurisdictions().getState());
        assertEquals("LOS ANGELES", res.tax.getJurisdictions().getCounty());
        assertEquals("LOS ANGELES", res.tax.getJurisdictions().getCity());
        assertEquals(16.5f, res.tax.getBreakdown().getTaxableAmount());
        assertEquals(1.16f, res.tax.getBreakdown().getTaxCollectable());
        assertEquals(0.07f, res.tax.getBreakdown().getCombinedTaxRate());
        assertEquals(16.5f, res.tax.getBreakdown().getStateTaxableAmount());
        assertEquals(0.07f, res.tax.getBreakdown().getStateTaxRate());
        assertEquals(1.16f, res.tax.getBreakdown().getStateTaxCollectable());
        assertEquals(0.0f, res.tax.getBreakdown().getCountyTaxableAmount());
        assertEquals(0.0f, res.tax.getBreakdown().getCountyTaxRate());
        assertEquals(0.0f, res.tax.getBreakdown().getCountyTaxCollectable());
        assertEquals(0.0f, res.tax.getBreakdown().getCityTaxableAmount());
        assertEquals(0.0f, res.tax.getBreakdown().getCityTaxRate());
        assertEquals(0.0f, res.tax.getBreakdown().getCityTaxCollectable());
        assertEquals(0.0f, res.tax.getBreakdown().getSpecialDistrictTaxableAmount());
        assertEquals(0.0f, res.tax.getBreakdown().getSpecialDistrictTaxRate());
        assertEquals(0.0f, res.tax.getBreakdown().getSpecialDistrictTaxCollectable());
        assertEquals(1.5f, res.tax.getBreakdown().getShipping().getTaxableAmount());
        assertEquals(0.11f, res.tax.getBreakdown().getShipping().getTaxCollectable());
        assertEquals(0.07f, res.tax.getBreakdown().getShipping().getCombinedTaxRate());
        assertEquals(1.5f, res.tax.getBreakdown().getShipping().getStateTaxableAmount());
        assertEquals(0.07f, res.tax.getBreakdown().getShipping().getStateTaxRate());
        assertEquals(0.11f, res.tax.getBreakdown().getShipping().getStateAmount());
        assertEquals(0.0f, res.tax.getBreakdown().getShipping().getCountyTaxableAmount());
        assertEquals(0.0f, res.tax.getBreakdown().getShipping().getCountyTaxRate());
        assertEquals(0.0f, res.tax.getBreakdown().getShipping().getCountyAmount());
        assertEquals(0.0f, res.tax.getBreakdown().getShipping().getCityTaxableAmount());
        assertEquals(0.0f, res.tax.getBreakdown().getShipping().getCityTaxRate());
        assertEquals(0.0f, res.tax.getBreakdown().getShipping().getCityAmount());
        assertEquals(0.0f, res.tax.getBreakdown().getShipping().getSpecialDistrictTaxableAmount());
        assertEquals(0.0f, res.tax.getBreakdown().getShipping().getSpecialDistrictTaxRate());
        assertEquals(0.0f, res.tax.getBreakdown().getShipping().getSpecialDistrictAmount());
        assertEquals(15f, res.tax.getBreakdown().getLineItems().get(0).getTaxableAmount());
        assertEquals(1.05f, res.tax.getBreakdown().getLineItems().get(0).getTaxCollectable());
        assertEquals(0.07f, res.tax.getBreakdown().getLineItems().get(0).getCombinedTaxRate());
        assertEquals(15f, res.tax.getBreakdown().getLineItems().get(0).getStateTaxableAmount());
        assertEquals(0.07f, res.tax.getBreakdown().getLineItems().get(0).getStateTaxRate());
        assertEquals(1.05f, res.tax.getBreakdown().getLineItems().get(0).getStateAmount());
        assertEquals(0.0f, res.tax.getBreakdown().getLineItems().get(0).getCountyTaxableAmount());
        assertEquals(0.0f, res.tax.getBreakdown().getLineItems().get(0).getCountyTaxRate());
        assertEquals(0.0f, res.tax.getBreakdown().getLineItems().get(0).getCountyAmount());
        assertEquals(0.0f, res.tax.getBreakdown().getLineItems().get(0).getCityTaxableAmount());
        assertEquals(0.0f, res.tax.getBreakdown().getLineItems().get(0).getCityTaxRate());
        assertEquals(0.0f, res.tax.getBreakdown().getLineItems().get(0).getCityAmount());
        assertEquals(0.0f, res.tax.getBreakdown().getLineItems().get(0).getSpecialDistrictTaxableAmount());
        assertEquals(0.0f, res.tax.getBreakdown().getLineItems().get(0).getSpecialDistrictTaxRate());
        assertEquals(0.0f, res.tax.getBreakdown().getLineItems().get(0).getSpecialDistrictAmount());
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
        params.put("exemption_type", "non_exempt");

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
                assertEquals("non_exempt", res.tax.getExemptionType());
                assertEquals("US", res.tax.getJurisdictions().getCountry());
                assertEquals("CA", res.tax.getJurisdictions().getState());
                assertEquals("LOS ANGELES", res.tax.getJurisdictions().getCounty());
                assertEquals("LOS ANGELES", res.tax.getJurisdictions().getCity());
                assertEquals(16.5f, res.tax.getBreakdown().getTaxableAmount());
                assertEquals(1.16f, res.tax.getBreakdown().getTaxCollectable());
                assertEquals(0.07f, res.tax.getBreakdown().getCombinedTaxRate());
                assertEquals(16.5f, res.tax.getBreakdown().getStateTaxableAmount());
                assertEquals(0.07f, res.tax.getBreakdown().getStateTaxRate());
                assertEquals(1.16f, res.tax.getBreakdown().getStateTaxCollectable());
                assertEquals(0.0f, res.tax.getBreakdown().getCountyTaxableAmount());
                assertEquals(0.0f, res.tax.getBreakdown().getCountyTaxRate());
                assertEquals(0.0f, res.tax.getBreakdown().getCountyTaxCollectable());
                assertEquals(0.0f, res.tax.getBreakdown().getCityTaxableAmount());
                assertEquals(0.0f, res.tax.getBreakdown().getCityTaxRate());
                assertEquals(0.0f, res.tax.getBreakdown().getCityTaxCollectable());
                assertEquals(0.0f, res.tax.getBreakdown().getSpecialDistrictTaxableAmount());
                assertEquals(0.0f, res.tax.getBreakdown().getSpecialDistrictTaxRate());
                assertEquals(0.0f, res.tax.getBreakdown().getSpecialDistrictTaxCollectable());
                assertEquals(1.5f, res.tax.getBreakdown().getShipping().getTaxableAmount());
                assertEquals(0.11f, res.tax.getBreakdown().getShipping().getTaxCollectable());
                assertEquals(0.07f, res.tax.getBreakdown().getShipping().getCombinedTaxRate());
                assertEquals(1.5f, res.tax.getBreakdown().getShipping().getStateTaxableAmount());
                assertEquals(0.07f, res.tax.getBreakdown().getShipping().getStateTaxRate());
                assertEquals(0.11f, res.tax.getBreakdown().getShipping().getStateAmount());
                assertEquals(0.0f, res.tax.getBreakdown().getShipping().getCountyTaxableAmount());
                assertEquals(0.0f, res.tax.getBreakdown().getShipping().getCountyTaxRate());
                assertEquals(0.0f, res.tax.getBreakdown().getShipping().getCountyAmount());
                assertEquals(0.0f, res.tax.getBreakdown().getShipping().getCityTaxableAmount());
                assertEquals(0.0f, res.tax.getBreakdown().getShipping().getCityTaxRate());
                assertEquals(0.0f, res.tax.getBreakdown().getShipping().getCityAmount());
                assertEquals(0.0f, res.tax.getBreakdown().getShipping().getSpecialDistrictTaxableAmount());
                assertEquals(0.0f, res.tax.getBreakdown().getShipping().getSpecialDistrictTaxRate());
                assertEquals(0.0f, res.tax.getBreakdown().getShipping().getSpecialDistrictAmount());
                assertEquals(15f, res.tax.getBreakdown().getLineItems().get(0).getTaxableAmount());
                assertEquals(1.05f, res.tax.getBreakdown().getLineItems().get(0).getTaxCollectable());
                assertEquals(0.07f, res.tax.getBreakdown().getLineItems().get(0).getCombinedTaxRate());
                assertEquals(15f, res.tax.getBreakdown().getLineItems().get(0).getStateTaxableAmount());
                assertEquals(0.07f, res.tax.getBreakdown().getLineItems().get(0).getStateTaxRate());
                assertEquals(1.05f, res.tax.getBreakdown().getLineItems().get(0).getStateAmount());
                assertEquals(0.0f, res.tax.getBreakdown().getLineItems().get(0).getCountyTaxableAmount());
                assertEquals(0.0f, res.tax.getBreakdown().getLineItems().get(0).getCountyTaxRate());
                assertEquals(0.0f, res.tax.getBreakdown().getLineItems().get(0).getCountyAmount());
                assertEquals(0.0f, res.tax.getBreakdown().getLineItems().get(0).getCityTaxableAmount());
                assertEquals(0.0f, res.tax.getBreakdown().getLineItems().get(0).getCityTaxRate());
                assertEquals(0.0f, res.tax.getBreakdown().getLineItems().get(0).getCityAmount());
                assertEquals(0.0f, res.tax.getBreakdown().getLineItems().get(0).getSpecialDistrictTaxableAmount());
                assertEquals(0.0f, res.tax.getBreakdown().getLineItems().get(0).getSpecialDistrictTaxRate());
                assertEquals(0.0f, res.tax.getBreakdown().getLineItems().get(0).getSpecialDistrictAmount());
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
        params.put("exemption_type", "non_exempt");

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
        assertEquals("non_exempt", res.tax.getExemptionType());
        assertEquals("CA", res.tax.getJurisdictions().getCountry());
        assertEquals("ON", res.tax.getJurisdictions().getState());
        assertEquals(26.95f, res.tax.getBreakdown().getTaxableAmount());
        assertEquals(3.5f, res.tax.getBreakdown().getTaxCollectable());
        assertEquals(0.13f, res.tax.getBreakdown().getCombinedTaxRate());
        assertEquals(26.95f, res.tax.getBreakdown().getGstTaxableAmount());
        assertEquals(0.05f, res.tax.getBreakdown().getGstTaxRate());
        assertEquals(1.35f, res.tax.getBreakdown().getGst());
        assertEquals(26.95f, res.tax.getBreakdown().getPstTaxableAmount());
        assertEquals(0.08f, res.tax.getBreakdown().getPstTaxRate());
        assertEquals(2.16f, res.tax.getBreakdown().getPst());
        assertEquals(0.0f, res.tax.getBreakdown().getQstTaxableAmount());
        assertEquals(0.0f, res.tax.getBreakdown().getQstTaxRate());
        assertEquals(0.0f, res.tax.getBreakdown().getQst());
        assertEquals(10f, res.tax.getBreakdown().getShipping().getTaxableAmount());
        assertEquals(1.3f, res.tax.getBreakdown().getShipping().getTaxCollectable());
        assertEquals(0.13f, res.tax.getBreakdown().getShipping().getCombinedTaxRate());
        assertEquals(10f, res.tax.getBreakdown().getShipping().getGstTaxableAmount());
        assertEquals(0.05f, res.tax.getBreakdown().getShipping().getGstTaxRate());
        assertEquals(0.5f, res.tax.getBreakdown().getShipping().getGst());
        assertEquals(10f, res.tax.getBreakdown().getShipping().getPstTaxableAmount());
        assertEquals(0.08f, res.tax.getBreakdown().getShipping().getPstTaxRate());
        assertEquals(0.8f, res.tax.getBreakdown().getShipping().getPst());
        assertEquals(0.0f, res.tax.getBreakdown().getShipping().getQstTaxableAmount());
        assertEquals(0.0f, res.tax.getBreakdown().getShipping().getQstTaxRate());
        assertEquals(0.0f, res.tax.getBreakdown().getShipping().getQst());
        assertEquals(16.95f, res.tax.getBreakdown().getLineItems().get(0).getTaxableAmount());
        assertEquals(2.2f, res.tax.getBreakdown().getLineItems().get(0).getTaxCollectable());
        assertEquals(0.13f, res.tax.getBreakdown().getLineItems().get(0).getCombinedTaxRate());
        assertEquals(16.95f, res.tax.getBreakdown().getLineItems().get(0).getGstTaxableAmount());
        assertEquals(0.05f, res.tax.getBreakdown().getLineItems().get(0).getGstTaxRate());
        assertEquals(0.85f, res.tax.getBreakdown().getLineItems().get(0).getGst());
        assertEquals(16.95f, res.tax.getBreakdown().getLineItems().get(0).getPstTaxableAmount());
        assertEquals(0.08f, res.tax.getBreakdown().getLineItems().get(0).getPstTaxRate());
        assertEquals(1.36f, res.tax.getBreakdown().getLineItems().get(0).getPst());
        assertEquals(0.0f, res.tax.getBreakdown().getLineItems().get(0).getQstTaxableAmount());
        assertEquals(0.0f, res.tax.getBreakdown().getLineItems().get(0).getQstTaxRate());
        assertEquals(0.0f, res.tax.getBreakdown().getLineItems().get(0).getQst());
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
        params.put("exemption_type", "non_exempt");

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
        assertEquals("non_exempt", res.tax.getExemptionType());
        assertEquals("FI", res.tax.getJurisdictions().getCountry());
        assertEquals(26.95f, res.tax.getBreakdown().getTaxableAmount());
        assertEquals(6.47f, res.tax.getBreakdown().getTaxCollectable());
        assertEquals(0.24f, res.tax.getBreakdown().getCombinedTaxRate());
        assertEquals(26.95f, res.tax.getBreakdown().getCountryTaxableAmount());
        assertEquals(0.24f, res.tax.getBreakdown().getCountryTaxRate());
        assertEquals(6.47f, res.tax.getBreakdown().getCountryTaxCollectable());
        assertEquals(10f, res.tax.getBreakdown().getShipping().getTaxableAmount());
        assertEquals(2.4f, res.tax.getBreakdown().getShipping().getTaxCollectable());
        assertEquals(0.24f, res.tax.getBreakdown().getShipping().getCombinedTaxRate());
        assertEquals(10f, res.tax.getBreakdown().getShipping().getCountryTaxableAmount());
        assertEquals(0.24f, res.tax.getBreakdown().getShipping().getCountryTaxRate());
        assertEquals(2.4f, res.tax.getBreakdown().getShipping().getCountryTaxCollectable());
        assertEquals(16.95f, res.tax.getBreakdown().getLineItems().get(0).getTaxableAmount());
        assertEquals(4.07f, res.tax.getBreakdown().getLineItems().get(0).getTaxCollectable());
        assertEquals(0.24f, res.tax.getBreakdown().getLineItems().get(0).getCombinedTaxRate());
        assertEquals(16.95f, res.tax.getBreakdown().getLineItems().get(0).getCountryTaxableAmount());
        assertEquals(0.24f, res.tax.getBreakdown().getLineItems().get(0).getCountryTaxRate());
        assertEquals(4.07f, res.tax.getBreakdown().getLineItems().get(0).getCountryTaxCollectable());
    }
}
