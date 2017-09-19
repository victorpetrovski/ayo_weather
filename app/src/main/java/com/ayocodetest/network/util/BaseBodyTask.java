package com.ayocodetest.network.util;

import com.android.volley.Request;

/**
 * Created by Victor on 8/27/16.
 */
public abstract class BaseBodyTask<T> extends NetworkTask<T> {

    public BaseBodyTask() {
    }

    @Override
    protected int getRequestMethod() {
        return Request.Method.POST;
    }
}
