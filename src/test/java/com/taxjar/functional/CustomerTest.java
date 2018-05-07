package com.taxjar.functional;

import com.taxjar.MockInterceptor;
import com.taxjar.TaxjarMock;
import com.taxjar.exception.TaxjarException;
import com.taxjar.model.customers.CustomerResponse;
import com.taxjar.model.customers.CustomersResponse;
import com.taxjar.net.Listener;
import junit.framework.TestCase;

import java.util.*;

public class CustomerTest extends TestCase {
    private TaxjarMock client;

    public void testListCustomers() throws TaxjarException {
        MockInterceptor interceptor = new MockInterceptor("customers/list.json");
        client = new TaxjarMock("TEST", interceptor);

        CustomersResponse res = client.listCustomers();
        assertEquals("123", res.customers.get(0));
        assertEquals("456", res.customers.get(1));
    }

    public void testListCustomersAsync() throws TaxjarException {
        MockInterceptor interceptor = new MockInterceptor("customers/list.json");
        client = new TaxjarMock("TEST", interceptor);

        client.listCustomers(new Listener<CustomersResponse>() {
            @Override
            public void onSuccess(CustomersResponse res)
            {
                assertEquals("123", res.customers.get(0));
                assertEquals("456", res.customers.get(1));
            }

            @Override
            public void onError(TaxjarException error)
            {
            }
        });
    }

    public void testShowCustomer() throws TaxjarException {
        MockInterceptor interceptor = new MockInterceptor("customers/show.json");
        client = new TaxjarMock("TEST", interceptor);

        CustomerResponse res = client.showCustomer("123");
        assertEquals("123", res.customer.getCustomerId());
        assertEquals("wholesale", res.customer.getExemptionType());
        assertEquals("Dunder Mifflin Paper Company", res.customer.getName());
        assertEquals("US", res.customer.getCountry());
        assertEquals("PA", res.customer.getState());
        assertEquals("18504", res.customer.getZip());
        assertEquals("Scranton", res.customer.getCity());
        assertEquals("1725 Slough Avenue", res.customer.getStreet());
        assertEquals("US", res.customer.getExemptRegions().get(0).getCountry());
        assertEquals("FL", res.customer.getExemptRegions().get(0).getState());
    }

    public void testShowCustomerAsync() throws TaxjarException {
        MockInterceptor interceptor = new MockInterceptor("customers/show.json");
        client = new TaxjarMock("TEST", interceptor);

        client.showCustomer("123", new Listener<CustomerResponse>() {
            @Override
            public void onSuccess(CustomerResponse res)
            {
                assertEquals("123", res.customer.getCustomerId());
                assertEquals("wholesale", res.customer.getExemptionType());
                assertEquals("Dunder Mifflin Paper Company", res.customer.getName());
                assertEquals("US", res.customer.getCountry());
                assertEquals("PA", res.customer.getState());
                assertEquals("18504", res.customer.getZip());
                assertEquals("Scranton", res.customer.getCity());
                assertEquals("1725 Slough Avenue", res.customer.getStreet());
                assertEquals("US", res.customer.getExemptRegions().get(0).getCountry());
                assertEquals("FL", res.customer.getExemptRegions().get(0).getState());
            }

            @Override
            public void onError(TaxjarException error)
            {
            }
        });
    }

    public void testCreateCustomer() throws TaxjarException {
        MockInterceptor interceptor = new MockInterceptor("customers/show.json");
        client = new TaxjarMock("TEST", interceptor);

        Map<String, Object> params = new HashMap<>();
        params.put("customer_id", "123");
        params.put("exemption_type", "wholesale");
        params.put("name", "Dunder Mifflin Paper Company");
        params.put("country", "US");
        params.put("state", "PA");
        params.put("zip", "18504");
        params.put("city", "Scranton");
        params.put("street", "1725 Slough Avenue");

        List<Map> exemptRegions = new ArrayList();
        Map<String, String> exemptRegion = new HashMap<>();
        exemptRegion.put("country", "US");
        exemptRegion.put("state", "FL");
        Map<String, String> exemptRegion2 = new HashMap<>();
        exemptRegion.put("country", "US");
        exemptRegion.put("state", "PA");
        exemptRegions.add(exemptRegion);
        exemptRegions.add(exemptRegion2);

        params.put("exempt_regions", exemptRegions);

        CustomerResponse res = client.createCustomer(params);
        assertEquals("123", res.customer.getCustomerId());
        assertEquals("wholesale", res.customer.getExemptionType());
        assertEquals("Dunder Mifflin Paper Company", res.customer.getName());
        assertEquals("US", res.customer.getCountry());
        assertEquals("PA", res.customer.getState());
        assertEquals("18504", res.customer.getZip());
        assertEquals("Scranton", res.customer.getCity());
        assertEquals("1725 Slough Avenue", res.customer.getStreet());
        assertEquals("US", res.customer.getExemptRegions().get(0).getCountry());
        assertEquals("FL", res.customer.getExemptRegions().get(0).getState());
    }

    public void testCreateCustomerAsync() throws TaxjarException {
        MockInterceptor interceptor = new MockInterceptor("customers/show.json");
        client = new TaxjarMock("TEST", interceptor);

        Map<String, Object> params = new HashMap<>();
        params.put("customer_id", "123");
        params.put("exemption_type", "wholesale");
        params.put("name", "Dunder Mifflin Paper Company");
        params.put("country", "US");
        params.put("state", "PA");
        params.put("zip", "18504");
        params.put("city", "Scranton");
        params.put("street", "1725 Slough Avenue");

        List<Map> exemptRegions = new ArrayList();
        Map<String, String> exemptRegion = new HashMap<>();
        exemptRegion.put("country", "US");
        exemptRegion.put("state", "FL");
        Map<String, String> exemptRegion2 = new HashMap<>();
        exemptRegion.put("country", "US");
        exemptRegion.put("state", "PA");
        exemptRegions.add(exemptRegion);
        exemptRegions.add(exemptRegion2);

        params.put("exempt_regions", exemptRegions);

        client.createCustomer(params, new Listener<CustomerResponse>() {
            @Override
            public void onSuccess(CustomerResponse res)
            {
                assertEquals("123", res.customer.getCustomerId());
                assertEquals("wholesale", res.customer.getExemptionType());
                assertEquals("Dunder Mifflin Paper Company", res.customer.getName());
                assertEquals("US", res.customer.getCountry());
                assertEquals("PA", res.customer.getState());
                assertEquals("18504", res.customer.getZip());
                assertEquals("Scranton", res.customer.getCity());
                assertEquals("1725 Slough Avenue", res.customer.getStreet());
                assertEquals("US", res.customer.getExemptRegions().get(0).getCountry());
                assertEquals("FL", res.customer.getExemptRegions().get(0).getState());
            }

            @Override
            public void onError(TaxjarException error)
            {
            }
        });
    }

    public void testUpdateCustomer() throws TaxjarException {
        MockInterceptor interceptor = new MockInterceptor("customers/show.json");
        client = new TaxjarMock("TEST", interceptor);

        Map<String, Object> params = new HashMap<>();
        params.put("customer_id", "123");
        params.put("exemption_type", "wholesale");
        params.put("name", "Dunder Mifflin Paper Company");
        params.put("country", "US");
        params.put("state", "PA");
        params.put("zip", "18504");
        params.put("city", "Scranton");
        params.put("street", "1725 Slough Avenue");

        List<Map> exemptRegions = new ArrayList();
        Map<String, String> exemptRegion = new HashMap<>();
        exemptRegion.put("country", "US");
        exemptRegion.put("state", "FL");
        Map<String, String> exemptRegion2 = new HashMap<>();
        exemptRegion.put("country", "US");
        exemptRegion.put("state", "PA");
        exemptRegions.add(exemptRegion);
        exemptRegions.add(exemptRegion2);

        params.put("exempt_regions", exemptRegions);

        CustomerResponse res = client.updateCustomer("123", params);
        assertEquals("123", res.customer.getCustomerId());
        assertEquals("wholesale", res.customer.getExemptionType());
        assertEquals("Dunder Mifflin Paper Company", res.customer.getName());
        assertEquals("US", res.customer.getCountry());
        assertEquals("PA", res.customer.getState());
        assertEquals("18504", res.customer.getZip());
        assertEquals("Scranton", res.customer.getCity());
        assertEquals("1725 Slough Avenue", res.customer.getStreet());
        assertEquals("US", res.customer.getExemptRegions().get(0).getCountry());
        assertEquals("FL", res.customer.getExemptRegions().get(0).getState());
    }

    public void testUpdateCustomerAsync() throws TaxjarException {
        MockInterceptor interceptor = new MockInterceptor("customers/show.json");
        client = new TaxjarMock("TEST", interceptor);

        Map<String, Object> params = new HashMap<>();
        params.put("customer_id", "123");
        params.put("exemption_type", "wholesale");
        params.put("name", "Dunder Mifflin Paper Company");
        params.put("country", "US");
        params.put("state", "PA");
        params.put("zip", "18504");
        params.put("city", "Scranton");
        params.put("street", "1725 Slough Avenue");

        List<Map> exemptRegions = new ArrayList();
        Map<String, String> exemptRegion = new HashMap<>();
        exemptRegion.put("country", "US");
        exemptRegion.put("state", "FL");
        Map<String, String> exemptRegion2 = new HashMap<>();
        exemptRegion.put("country", "US");
        exemptRegion.put("state", "PA");
        exemptRegions.add(exemptRegion);
        exemptRegions.add(exemptRegion2);

        params.put("exempt_regions", exemptRegions);

        client.updateCustomer("123", params, new Listener<CustomerResponse>() {
            @Override
            public void onSuccess(CustomerResponse res)
            {
                assertEquals("123", res.customer.getCustomerId());
                assertEquals("wholesale", res.customer.getExemptionType());
                assertEquals("Dunder Mifflin Paper Company", res.customer.getName());
                assertEquals("US", res.customer.getCountry());
                assertEquals("PA", res.customer.getState());
                assertEquals("18504", res.customer.getZip());
                assertEquals("Scranton", res.customer.getCity());
                assertEquals("1725 Slough Avenue", res.customer.getStreet());
                assertEquals("US", res.customer.getExemptRegions().get(0).getCountry());
                assertEquals("FL", res.customer.getExemptRegions().get(0).getState());
            }

            @Override
            public void onError(TaxjarException error)
            {
            }
        });
    }

    public void testDeleteCustomer() throws TaxjarException {
        MockInterceptor interceptor = new MockInterceptor("customers/show.json");
        client = new TaxjarMock("TEST", interceptor);

        CustomerResponse res = client.deleteCustomer("123");
        assertEquals("123", res.customer.getCustomerId());
        assertEquals("wholesale", res.customer.getExemptionType());
        assertEquals("Dunder Mifflin Paper Company", res.customer.getName());
        assertEquals("US", res.customer.getCountry());
        assertEquals("PA", res.customer.getState());
        assertEquals("18504", res.customer.getZip());
        assertEquals("Scranton", res.customer.getCity());
        assertEquals("1725 Slough Avenue", res.customer.getStreet());
        assertEquals("US", res.customer.getExemptRegions().get(0).getCountry());
        assertEquals("FL", res.customer.getExemptRegions().get(0).getState());
    }

    public void testDeleteCustomerAsync() throws TaxjarException {
        MockInterceptor interceptor = new MockInterceptor("customers/delete.json");
        client = new TaxjarMock("TEST", interceptor);

        client.deleteCustomer("123", new Listener<CustomerResponse>() {
            @Override
            public void onSuccess(CustomerResponse res)
            {
                assertEquals("123", res.customer.getCustomerId());
                assertEquals("wholesale", res.customer.getExemptionType());
                assertEquals("Dunder Mifflin Paper Company", res.customer.getName());
                assertEquals("US", res.customer.getCountry());
                assertEquals("PA", res.customer.getState());
                assertEquals("18504", res.customer.getZip());
                assertEquals("Scranton", res.customer.getCity());
                assertEquals("1725 Slough Avenue", res.customer.getStreet());
                assertEquals("US", res.customer.getExemptRegions().get(0).getCountry());
                assertEquals("FL", res.customer.getExemptRegions().get(0).getState());
            }

            @Override
            public void onError(TaxjarException error)
            {
            }
        });
    }

}
