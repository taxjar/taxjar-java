package com.taxjar.model;

import com.taxjar.MockInterceptor;
import com.taxjar.TaxjarMock;
import com.taxjar.exception.TaxjarException;
import com.taxjar.model.validations.ValidationResponse;
import com.taxjar.net.Listener;
import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Map;

public class ValidationTest extends TestCase {
    private TaxjarMock client;

    protected void setUp() {
        MockInterceptor interceptor = new MockInterceptor("validation.json");
        client = new TaxjarMock("TEST", interceptor);
    }

    public void testValidate() throws TaxjarException {
        Map<String, String> params = new HashMap<>();
        params.put("vat", "FR40303265045");

        ValidationResponse res = client.validate(params);

        assertEquals((Boolean) true, res.validation.getValid());
        assertEquals((Boolean) true, res.validation.getExists());
        assertEquals((Boolean) true, res.validation.getViesAvailable());
        assertEquals("FR", res.validation.getViesResponse().getCountryCode());
        assertEquals("40303265045", res.validation.getViesResponse().getVatNumber());
        assertEquals("2016-02-10", res.validation.getViesResponse().getRequestDate());
        assertEquals((Boolean) true, res.validation.getViesResponse().getValid());
        assertEquals("SA SODIMAS", res.validation.getViesResponse().getName());
        assertEquals("11 RUE AMPERE\n26600 PONT DE L ISERE", res.validation.getViesResponse().getAddress());
    }

    public void testValidateAsync() throws TaxjarException {
        Map<String, String> params = new HashMap<>();
        params.put("vat", "FR40303265045");

        client.validate(params, new Listener<ValidationResponse>() {
            @Override
            public void onSuccess(ValidationResponse res)
            {
                assertEquals((Boolean) true, res.validation.getValid());
                assertEquals((Boolean) true, res.validation.getExists());
                assertEquals((Boolean) true, res.validation.getViesAvailable());
                assertEquals("FR", res.validation.getViesResponse().getCountryCode());
                assertEquals("40303265045", res.validation.getViesResponse().getVatNumber());
                assertEquals("2016-02-10", res.validation.getViesResponse().getRequestDate());
                assertEquals((Boolean) true, res.validation.getViesResponse().getValid());
                assertEquals("SA SODIMAS", res.validation.getViesResponse().getName());
                assertEquals("11 RUE AMPERE\n26600 PONT DE L ISERE", res.validation.getViesResponse().getAddress());
            }

            @Override
            public void onError(TaxjarException error)
            {
            }
        });
    }
}
