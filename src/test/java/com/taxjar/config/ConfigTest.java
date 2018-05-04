package com.taxjar.config;

import com.taxjar.Taxjar;
import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Map;

public class ConfigTest extends TestCase {
    private Taxjar client;

    protected void setUp() {
        client = new Taxjar("TEST");
    }

    public void testClientParams() {
        Map<String, String> params = new HashMap<>();
        params.put("apiUrl", Taxjar.SANDBOX_API_URL);

        client = new Taxjar("TEST", params);
        client.setApiConfig("apiUrl", Taxjar.SANDBOX_API_URL);
        assertEquals(client.getApiConfig("apiUrl"), Taxjar.SANDBOX_API_URL);
    }

    public void testGetApiConfig() {
        String apiUrl = client.getApiConfig("apiUrl");
        assertEquals(apiUrl, Taxjar.DEFAULT_API_URL);
    }

    public void testSetApiConfig() {
        client.setApiConfig("apiUrl", Taxjar.SANDBOX_API_URL);
        assertEquals(client.getApiConfig("apiUrl"), Taxjar.SANDBOX_API_URL);
    }
}
