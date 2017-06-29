package com.taxjar.net;

import com.taxjar.exception.TaxjarException;

public interface Listener<T> {
    public void onSuccess(T object);
    public void onError(TaxjarException error);
}
