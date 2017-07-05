package com.taxjar.model;

import com.taxjar.MockInterceptor;
import com.taxjar.TaxjarMock;
import com.taxjar.exception.TaxjarException;
import com.taxjar.model.categories.CategoryResponse;
import com.taxjar.net.Listener;
import junit.framework.TestCase;

public class CategoryTest extends TestCase {
    private TaxjarMock client;

    protected void setUp() {
        MockInterceptor interceptor = new MockInterceptor("categories.json");
        client = new TaxjarMock("TEST", interceptor);
    }

    public void testCategories() throws TaxjarException {
        CategoryResponse res = client.categories();
        assertEquals("Digital Goods", res.categories.get(0).getName());
        assertEquals("31000", res.categories.get(0).getProductTaxCode());
        assertEquals("Digital products transferred electronically, meaning obtained by the purchaser by means other than tangible storage media.", res.categories.get(0).getDescription());
    }

    public void testCategoriesAsync() throws TaxjarException {
        client.categories(new Listener<CategoryResponse>() {
            @Override
            public void onSuccess(CategoryResponse res)
            {
                assertEquals("Clothing", res.categories.get(1).getName());
                assertEquals("20010", res.categories.get(1).getProductTaxCode());
                assertEquals("All human wearing apparel suitable for general use", res.categories.get(1).getDescription());
            }

            @Override
            public void onError(TaxjarException error)
            {
            }
        });
    }
}