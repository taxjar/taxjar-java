package com.taxjar.functional;

import com.taxjar.MockInterceptor;
import com.taxjar.TaxjarMock;
import com.taxjar.exception.TaxjarException;
import com.taxjar.model.validations.AddressResponse;
import com.taxjar.model.validations.ValidationResponse;
import com.taxjar.net.Listener;
import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Map;

public class ValidationTest extends TestCase {
    private TaxjarMock client;

    public void testValidateAddress() throws TaxjarException {
        MockInterceptor interceptor = new MockInterceptor("addresses.json");
        client = new TaxjarMock("TEST", interceptor);
        Map<String, Object> params = new HashMap<>();
        params.put("country", "US");
        params.put("state", "AZ");
        params.put("zip", "85297");
        params.put("city", "Gilbert");
        params.put("street", "3301 South Greenfield Rd");

        AddressResponse res = client.validateAddress(params);

        assertEquals("85297-2176", res.addresses.get(0).getZip());
        assertEquals("3301 S Greenfield Rd", res.addresses.get(0).getStreet());
        assertEquals("AZ", res.addresses.get(0).getState());
        assertEquals("US", res.addresses.get(0).getCountry());
        assertEquals("Gilbert", res.addresses.get(0).getCity());
    }

    public void testValidateAddressAsync() throws TaxjarException {
        MockInterceptor interceptor = new MockInterceptor("addresses.json");
        client = new TaxjarMock("TEST", interceptor);
        Map<String, Object> params = new HashMap<>();
        params.put("country", "US");
        params.put("state", "AZ");
        params.put("zip", "85297");
        params.put("city", "Gilbert");
        params.put("street", "3301 South Greenfield Rd");

        client.validateAddress(params, new Listener<AddressResponse>() {
            @Override
            public void onSuccess(AddressResponse res)
            {
                assertEquals("85297-2176", res.addresses.get(0).getZip());
                assertEquals("3301 S Greenfield Rd", res.addresses.get(0).getStreet());
                assertEquals("AZ", res.addresses.get(0).getState());
                assertEquals("US", res.addresses.get(0).getCountry());
                assertEquals("Gilbert", res.addresses.get(0).getCity());
            }

            @Override
            public void onError(TaxjarException error)
            {
            }
        });
    }

    public void testValidateAddressWithMultipleMatches() throws TaxjarException {
        MockInterceptor interceptor = new MockInterceptor("addresses_multiple.json");
        client = new TaxjarMock("TEST", interceptor);
        Map<String, Object> params = new HashMap<>();
        params.put("state", "AZ");
        params.put("city", "Phoenix");
        params.put("street", "1109 9th");

        AddressResponse res = client.validateAddress(params);

        assertEquals("85007-3646", res.addresses.get(0).getZip());
        assertEquals("1109 S 9th Ave", res.addresses.get(0).getStreet());
        assertEquals("AZ", res.addresses.get(0).getState());
        assertEquals("US", res.addresses.get(0).getCountry());
        assertEquals("Phoenix", res.addresses.get(0).getCity());

        assertEquals("85006-2734", res.addresses.get(1).getZip());
        assertEquals("1109 N 9th St", res.addresses.get(1).getStreet());
        assertEquals("AZ", res.addresses.get(1).getState());
        assertEquals("US", res.addresses.get(1).getCountry());
        assertEquals("Phoenix", res.addresses.get(1).getCity());
    }

    public void testValidateAddressWithMultipleMatchesAsync() throws TaxjarException {
        MockInterceptor interceptor = new MockInterceptor("addresses_multiple.json");
        client = new TaxjarMock("TEST", interceptor);
        Map<String, Object> params = new HashMap<>();
        params.put("state", "AZ");
        params.put("city", "Phoenix");
        params.put("street", "1109 9th");

        client.validateAddress(params, new Listener<AddressResponse>() {
            @Override
            public void onSuccess(AddressResponse res)
            {
                assertEquals("85007-3646", res.addresses.get(0).getZip());
                assertEquals("1109 S 9th Ave", res.addresses.get(0).getStreet());
                assertEquals("AZ", res.addresses.get(0).getState());
                assertEquals("US", res.addresses.get(0).getCountry());
                assertEquals("Phoenix", res.addresses.get(0).getCity());

                assertEquals("85006-2734", res.addresses.get(1).getZip());
                assertEquals("1109 N 9th St", res.addresses.get(1).getStreet());
                assertEquals("AZ", res.addresses.get(1).getState());
                assertEquals("US", res.addresses.get(1).getCountry());
                assertEquals("Phoenix", res.addresses.get(1).getCity());
            }

            @Override
            public void onError(TaxjarException error)
            {
            }
        });
    }

    public void testValidate() throws TaxjarException {
        MockInterceptor interceptor = new MockInterceptor("validation.json");
        client = new TaxjarMock("TEST", interceptor);
        Map<String, String> params = new HashMap<>();
        params.put("vat", "FR40303265045");

        ValidationResponse res = client.validateVat(params);

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
        MockInterceptor interceptor = new MockInterceptor("validation.json");
        client = new TaxjarMock("TEST", interceptor);
        Map<String, String> params = new HashMap<>();
        params.put("vat", "FR40303265045");

        client.validateVat(params, new Listener<ValidationResponse>() {
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
