package com.ayocodetest.network.response;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Victor on 8/27/16.
 */
public class ErrorResponse {

    public static final String UNAUTHORIZED = "UNAUTHORIZED";

    public static final String MISSING_SECURITY = "MISSING_SECURITY";
    public static final String NETWORK_ERROR = "NETWORK_ERROR";
    public static final String INVALID_ID = "INVALID_ID";

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({UNAUTHORIZED, NETWORK_ERROR})
    @interface ErrorStatus{}

    public ErrorResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public ErrorResponse() {}

    @ErrorStatus
    private String status;

    private boolean success;

    private Object message;

    @ErrorStatus
    public String getStatus() {
        return status;
    }

    public boolean getIsSuccess() {
        return success;
    }

    public String getMessage() {
        return message != null ? message.toString() : status;
    }

}

