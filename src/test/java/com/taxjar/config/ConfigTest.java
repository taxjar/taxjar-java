package com.taxjar.config;

import com.jcabi.matchers.RegexMatchers;
import com.taxjar.Taxjar;

import junit.framework.TestCase;

import static org.junit.Assert.assertThat;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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

        Map<String, String> headers = new HashMap<>();
        headers.put("x-api-version", "2020-08-07");
        params.put("headers", headers);

        client = new Taxjar("TEST", params);
        assertEquals(client.getApiConfig("apiUrl"), Taxjar.SANDBOX_API_URL);
        assertEquals((long) client.getApiConfig("timeout"), 60000);
        assertEquals(client.getApiConfig("headers"), headers);

        client2 = new Taxjar("TEST2");
        assertEquals(client.getApiConfig("apiToken"), "TEST");
        assertEquals(client2.getApiConfig("apiToken"), "TEST2");
        assertEquals(client2.getApiConfig("headers"), null);
    }

    public void testGetApiConfig() {
        assertEquals(client.getApiConfig("apiUrl"), Taxjar.DEFAULT_API_URL);
    }

    public void testSetApiConfig() {
        client.setApiConfig("apiUrl", Taxjar.SANDBOX_API_URL);
        assertEquals(client.getApiConfig("apiUrl"), Taxjar.SANDBOX_API_URL);

        client.setApiConfig("apiToken", "foobar");
        assertEquals(client.getApiConfig("apiToken"), "foobar");

        client.setApiConfig("timeout", 60 * 1000);
        assertEquals((long) client.getApiConfig("timeout"), 60000);

        Map<String, String> headers = new HashMap<>();
        headers.put("x-api-version", "2020-08-07");

        client.setApiConfig("headers", headers);
        assertEquals(client.getApiConfig("headers"), headers);
    }

    public void testGetUserAgent() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Method getUserAgent = Taxjar.class.getDeclaredMethod("getUserAgent");
        getUserAgent.setAccessible(true);
        String userAgent = (String) getUserAgent.invoke(null);

        assertThat(userAgent, RegexMatchers.matchesPattern("^TaxJar/Java \\(.+\\) taxjar-java/\\d+\\.\\d+\\.\\d+$"));
    }
}
