package com.ayocodetest.network.util;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.ayocodetest.BuildConfig;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Victor on 8/27/16.
 */

public class GsonRequest<T> extends Request<T> {

    private Gson gson;
    private Type type;
    private Response.Listener<T> listener;
    private Map<String, String> headers;
    private Map<String, String> params;
    private String body;
    private String tag;

    /**
     * Make a request and return a parsed object from JSON.
     *
     * @param url  URL of the request to make
     * @param type Relevant class type, for Gson reflection
     */
    public GsonRequest(int method, String url, Type type, String tag, Response.Listener<T> listener,
                       Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.type = type;
        this.listener = listener;
        this.tag = tag;
    }

    public void setGson(Gson gson) {
        this.gson = gson;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    @Override
    public Map<String, String> getHeaders() {
        return headers != null ? headers : new HashMap<String, String>();
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        if (body != null) {
            return body.getBytes();
        }

        return super.getBody();
    }

    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        String json = "error";
        try {
            json = new String(
                                     response.data,
                                     HttpHeaderParser.parseCharset(response.headers));

            Log.d(tag, "raw response: " + json);

            if (gson == null) {
                gson = new Gson();
            }

            T result = gson.fromJson(json, type);

            return Response.success(result,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                Log.d(tag, "raw response: " + json);
            }
            return Response.error(new ParseError(e));
        }
    }

}
