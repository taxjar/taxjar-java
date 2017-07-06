package com.taxjar.functional;

import com.taxjar.MockInterceptor;
import com.taxjar.TaxjarMock;
import com.taxjar.exception.TaxjarException;
import com.taxjar.model.nexus.RegionResponse;
import com.taxjar.net.Listener;
import junit.framework.TestCase;

public class NexusTest extends TestCase {
    private TaxjarMock client;

    protected void setUp() {
        MockInterceptor interceptor = new MockInterceptor("nexus_regions.json");
        client = new TaxjarMock("TEST", interceptor);
    }

    public void testRegions() throws TaxjarException {
        RegionResponse res = client.nexusRegions();
        assertEquals("US", res.regions.get(0).getCountryCode());
        assertEquals("United States", res.regions.get(0).getCountry());
        assertEquals("CA", res.regions.get(0).getRegionCode());
        assertEquals("California", res.regions.get(0).getRegion());
    }

    public void testRegionsAsync() throws TaxjarException {
        client.nexusRegions(new Listener<RegionResponse>() {
            @Override
            public void onSuccess(RegionResponse res)
            {
                assertEquals("US", res.regions.get(0).getCountryCode());
                assertEquals("United States", res.regions.get(0).getCountry());
                assertEquals("CA", res.regions.get(0).getRegionCode());
                assertEquals("California", res.regions.get(0).getRegion());
            }

            @Override
            public void onError(TaxjarException error)
            {
            }
        });
    }
}
