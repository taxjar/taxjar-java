package com.taxjar.model;

import com.taxjar.MockInterceptor;
import com.taxjar.TaxjarMock;
import com.taxjar.exception.TaxjarException;
import com.taxjar.model.summarized_rates.SummaryRateResponse;
import com.taxjar.net.Listener;
import junit.framework.TestCase;

public class SummaryRateTest extends TestCase {
    private TaxjarMock client;

    protected void setUp() {
        MockInterceptor interceptor = new MockInterceptor("summary_rates.json");
        client = new TaxjarMock("TEST", interceptor);
    }

    public void testSummaryRates() throws TaxjarException {
        SummaryRateResponse res = client.summaryRates();
        assertEquals("US", res.summaryRates.get(0).getCountryCode());
        assertEquals("United States", res.summaryRates.get(0).getCountry());
        assertEquals("CA", res.summaryRates.get(0).getRegionCode());
        assertEquals("California", res.summaryRates.get(0).getRegion());
        assertEquals("State Tax", res.summaryRates.get(0).getMinimumRate().getLabel());
        assertEquals(0.065f, res.summaryRates.get(0).getMinimumRate().getRate());
        assertEquals("Tax", res.summaryRates.get(0).getAverageRate().getLabel());
        assertEquals(0.0827f, res.summaryRates.get(0).getAverageRate().getRate());
    }

    public void testSummaryRatesAsync() throws TaxjarException {
        client.summaryRates(new Listener<SummaryRateResponse>() {
            @Override
            public void onSuccess(SummaryRateResponse res)
            {
                assertEquals("US", res.summaryRates.get(0).getCountryCode());
                assertEquals("United States", res.summaryRates.get(0).getCountry());
                assertEquals("CA", res.summaryRates.get(0).getRegionCode());
                assertEquals("California", res.summaryRates.get(0).getRegion());
                assertEquals("State Tax", res.summaryRates.get(0).getMinimumRate().getLabel());
                assertEquals(0.065f, res.summaryRates.get(0).getMinimumRate().getRate());
                assertEquals("Tax", res.summaryRates.get(0).getAverageRate().getLabel());
                assertEquals(0.0827f, res.summaryRates.get(0).getAverageRate().getRate());
            }

            @Override
            public void onError(TaxjarException error)
            {
            }
        });
    }
}
