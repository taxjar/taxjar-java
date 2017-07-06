package com.taxjar.exception;

import com.taxjar.MockInterceptor;
import com.taxjar.TaxjarMock;
import com.taxjar.model.categories.CategoryResponse;
import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Map;

public class ExceptionTest extends TestCase {
    private TaxjarMock client;

    protected void setUp() {
        Map<String, Object> opts = new HashMap<>();
        opts.put("code", 400);
        MockInterceptor interceptor = new MockInterceptor("error.json", opts);
        client = new TaxjarMock("TEST", interceptor);
    }

    public void testException() {
        TaxjarException e = null;

        try {
            CategoryResponse res = client.categories();
        } catch (TaxjarException ex) {
            e = ex;
        }

        assertTrue(e instanceof TaxjarException);
        assertEquals("Bad Request - Your request format is bad.", e.getMessage());
        assertEquals((Integer) 400, e.getStatusCode());
    }
}
