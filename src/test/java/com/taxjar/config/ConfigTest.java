package com.taxjar.config;

import com.taxjar.Taxjar;
import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Map;

public class ConfigTest extends TestCase {
    private Taxjar client;
    private Taxjar client2;

    protected void setUp() {
        client = new Taxjar("TEST");
    }

    public void testClientParams() {
        Map<String, Object> params = new HashMap<>();
        params.put("apiUrl", Taxjar.SANDBOX_API_URL);
        params.put("timeout", 60 * 1000);

        client = new Taxjar("TEST", params);
        assertEquals(client.getApiConfig("apiUrl"), Taxjar.SANDBOX_API_URL);
        assertEquals(client.getApiConfig("timeout"), "60000");

        client2 = new Taxjar("TEST2");
        assertEquals(client.getApiConfig("apiToken"), "TEST");
        assertEquals(client2.getApiConfig("apiToken"), "TEST2");
    }

    public void testGetApiConfig() {
        String apiUrl = client.getApiConfig("apiUrl");
        assertEquals(apiUrl, Taxjar.DEFAULT_API_URL);
    }

    public void testSetApiConfig() {
        client.setApiConfig("apiUrl", Taxjar.SANDBOX_API_URL);
        assertEquals(client.getApiConfig("apiUrl"), Taxjar.SANDBOX_API_URL);

        client.setApiConfig("apiToken", "foobar");
        assertEquals(client.getApiConfig("apiToken"), "foobar");

        client.setApiConfig("timeout", 60 * 1000);
        assertEquals(client.getApiConfig("timeout"), "60000");
    }
}
