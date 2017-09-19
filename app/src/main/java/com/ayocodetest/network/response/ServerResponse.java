package com.ayocodetest.network.response;

import com.google.gson.GsonBuilder;

/**
 * Created by Victor on 8/27/16.
 */
public class ServerResponse<T> extends ErrorResponse {

    public ServerResponse(String status, String message) {
        super(status, message);
    }

    public ServerResponse() {
    }

    private T data;

    public T getResult() {
        return data;
    }

    private static final String SUCCESS = "OK";

    public boolean isSuccess() {
        return getIsSuccess();
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson(this);
    }

}

