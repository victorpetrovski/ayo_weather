package com.ayocodetest.network.request;

import com.google.gson.Gson;

/**
 * Created by Victor on 8/27/16.
 */
public abstract class BaseRequestBody implements RequestBody {

    @Override
    public String toJsonString() {
        return new Gson().toJson(this);
    }
}
