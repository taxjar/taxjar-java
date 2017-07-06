package com.taxjar.functional;

import com.taxjar.MockInterceptor;
import com.taxjar.TaxjarMock;
import com.taxjar.exception.TaxjarException;
import com.taxjar.model.rates.RateResponse;
import com.taxjar.net.Listener;
import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Map;

public class RateTest extends TestCase {
    private TaxjarMock client;

    public void testRatesForLocation() throws TaxjarException {
        MockInterceptor interceptor = new MockInterceptor("rates.json");
        client = new TaxjarMock("TEST", interceptor);

        RateResponse res = client.ratesForLocation("90002");
        assertEquals("90002", res.rate.getZip());
        assertEquals("CA", res.rate.getState());
        assertEquals(0.065f, res.rate.getStateRate());
        assertEquals("LOS ANGELES", res.rate.getCounty());
        assertEquals(0.01f, res.rate.getCountyRate());
        assertEquals("WATTS", res.rate.getCity());
        assertEquals(0f, res.rate.getCityRate());
        assertEquals(0.015f, res.rate.getCombinedDistrictRate());
        assertEquals(0.09f, res.rate.getCombinedRate());
        assertEquals((Boolean) false, res.rate.getFreightTaxable());

        Map<String, String> params = new HashMap<>();
        params.put("country", "US");
        params.put("city", "Watts");
        params.put("street", "123 Test St");

        RateResponse res2 = client.ratesForLocation("90002", params);
        assertEquals("90002", res2.rate.getZip());
        assertEquals("CA", res2.rate.getState());
        assertEquals(0.065f, res2.rate.getStateRate());
        assertEquals("LOS ANGELES", res2.rate.getCounty());
        assertEquals(0.01f, res2.rate.getCountyRate());
        assertEquals("WATTS", res2.rate.getCity());
        assertEquals(0f, res2.rate.getCityRate());
        assertEquals(0.015f, res2.rate.getCombinedDistrictRate());
        assertEquals(0.09f, res2.rate.getCombinedRate());
        assertEquals((Boolean) false, res2.rate.getFreightTaxable());
    }

    public void testRatesForLocationAsync() throws TaxjarException {
        MockInterceptor interceptor = new MockInterceptor("rates.json");
        client = new TaxjarMock("TEST", interceptor);

        client.ratesForLocation("90002", new Listener<RateResponse>() {
            @Override
            public void onSuccess(RateResponse res)
            {
                assertEquals("90002", res.rate.getZip());
                assertEquals("CA", res.rate.getState());
                assertEquals(0.065f, res.rate.getStateRate());
                assertEquals("LOS ANGELES", res.rate.getCounty());
                assertEquals(0.01f, res.rate.getCountyRate());
                assertEquals("WATTS", res.rate.getCity());
                assertEquals(0f, res.rate.getCityRate());
                assertEquals(0.015f, res.rate.getCombinedDistrictRate());
                assertEquals(0.09f, res.rate.getCombinedRate());
                assertEquals((Boolean) false, res.rate.getFreightTaxable());
            }

            @Override
            public void onError(TaxjarException error)
            {
            }
        });

        Map<String, String> params = new HashMap<>();
        params.put("country", "US");
        params.put("city", "Watts");
        params.put("street", "123 Test St");

        client.ratesForLocation("90002", params, new Listener<RateResponse>() {
            @Override
            public void onSuccess(RateResponse res)
            {
                assertEquals("90002", res.rate.getZip());
                assertEquals("CA", res.rate.getState());
                assertEquals(0.065f, res.rate.getStateRate());
                assertEquals("LOS ANGELES", res.rate.getCounty());
                assertEquals(0.01f, res.rate.getCountyRate());
                assertEquals("WATTS", res.rate.getCity());
                assertEquals(0f, res.rate.getCityRate());
                assertEquals(0.015f, res.rate.getCombinedDistrictRate());
                assertEquals(0.09f, res.rate.getCombinedRate());
                assertEquals((Boolean) false, res.rate.getFreightTaxable());
            }

            @Override
            public void onError(TaxjarException error)
            {
            }
        });
    }

    public void testRatesForLocationAU() throws TaxjarException {
        MockInterceptor interceptor = new MockInterceptor("rates_au.json");
        client = new TaxjarMock("TEST", interceptor);

        Map<String, String> params = new HashMap<>();
        params.put("country", "AU");
        params.put("city", "Sydney");
        params.put("street", "483 George St");

        RateResponse res = client.ratesForLocation("2060", params);
        assertEquals("2060", res.rate.getZip());
        assertEquals("AU", res.rate.getCountry());
        assertEquals(0.1f, res.rate.getCountryRate());
        assertEquals(0.1f, res.rate.getCombinedRate());
        assertEquals((Boolean) true, res.rate.getFreightTaxable());
    }

    public void testRatesForLocationCA() throws TaxjarException {
        MockInterceptor interceptor = new MockInterceptor("rates_ca.json");
        client = new TaxjarMock("TEST", interceptor);

        Map<String, String> params = new HashMap<>();
        params.put("country", "CA");
        params.put("city", "Vancouver");
        params.put("street", "453 W 12th Ave");

        RateResponse res = client.ratesForLocation("V5K0A1", params);
        assertEquals("V5K0A1", res.rate.getZip());
        assertEquals("Vancouver", res.rate.getCity());
        assertEquals("BC", res.rate.getState());
        assertEquals("CA", res.rate.getCountry());
        assertEquals(0.12f, res.rate.getCombinedRate());
        assertEquals((Boolean) true, res.rate.getFreightTaxable());
    }

    public void testRatesForLocationEU() throws TaxjarException {
        MockInterceptor interceptor = new MockInterceptor("rates_eu.json");
        client = new TaxjarMock("TEST", interceptor);

        Map<String, String> params = new HashMap<>();
        params.put("country", "FI");
        params.put("city", "Helsinki");
        params.put("street", "Pohjoisesplanadi 11-13");

        RateResponse res = client.ratesForLocation("00150", params);
        assertEquals("FI", res.rate.getCountry());
        assertEquals("Finland", res.rate.getName());
        assertEquals(0.24f, res.rate.getStandardRate());
        assertEquals(0f, res.rate.getReducedRate());
        assertEquals(0f, res.rate.getSuperReducedRate());
        assertEquals(0f, res.rate.getParkingRate());
        assertEquals(0f, res.rate.getDistanceSaleThreshold());
        assertEquals((Boolean) true, res.rate.getFreightTaxable());
    }

    public void testRatesForLocationSST() throws TaxjarException {
        MockInterceptor interceptor = new MockInterceptor("rates_sst.json");
        client = new TaxjarMock("TEST", interceptor);

        Map<String, String> params = new HashMap<>();
        params.put("country", "US");
        params.put("city", "Williston");
        params.put("street", "7900 N Williston Rd");

        RateResponse res = client.ratesForLocation("05495-2086", params);
        assertEquals("05495-2086", res.rate.getZip());
        assertEquals("US", res.rate.getCountry());
        assertEquals(0f, res.rate.getCountryRate());
        assertEquals("VT", res.rate.getState());
        assertEquals(0.06f, res.rate.getStateRate());
        assertEquals("CHITTENDEN", res.rate.getCounty());
        assertEquals(0f, res.rate.getCountyRate());
        assertEquals("WILLISTON", res.rate.getCity());
        assertEquals(0f, res.rate.getCityRate());
        assertEquals(0.01f, res.rate.getCombinedDistrictRate());
        assertEquals(0.07f, res.rate.getCombinedRate());
        assertEquals((Boolean) true, res.rate.getFreightTaxable());
    }
}
