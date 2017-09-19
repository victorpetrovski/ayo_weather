package com.ayocodetest.network.util;

/**
 * Created by Victor on 8/27/16.
 */
public abstract class BaseTokenTask<T> extends NetworkTask<T> {

    public BaseTokenTask() {
        addQueryParam("appid", NetworkUtils.APP_ID);

    }
}
