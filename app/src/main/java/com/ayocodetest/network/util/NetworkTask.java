package com.ayocodetest.network.util;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.RequestFuture;
import com.ayocodetest.BuildConfig;
import com.ayocodetest.R;
import com.ayocodetest.network.response.ErrorResponse;
import com.ayocodetest.network.response.ServerResponse;
import com.ayocodetest.util.DateMillisDeserializer;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by Victor on 8/27/16.
 */
public abstract class NetworkTask<T> implements
        Response.Listener<T>,
        Response.ErrorListener {
    private final String TAG;

    private Map<String, String> queryParams;
    private Map<String, String> postParams;
    private Map<String, String> headers;
    private String body;
    private OnTaskExecuted<T> listener;
    private Context context;

    private GsonRequest request;

    public interface OnTaskExecuted<T> {
        void onSuccess(T result);
        void onError(ErrorResponse error);
    }

    private static final int REQUEST_TIMEOUT = (int) TimeUnit.SECONDS.toMillis(25);

    //Request type annotation. If more request types are needed they should be added to the IntDef
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({Request.Method.GET, Request.Method.POST, Request.Method.DELETE, Request.Method.PUT})
    public @interface RequestMethod {}

    public NetworkTask() {
        TAG = getClass().getSimpleName();
    }

    /**
     * Relative path for this specific task without the leading foreslash.
     * @return the relative url path.
     */
    @NonNull
    protected abstract String getRelativePath();

    /**
     * Will be new TypeToken<ServerResponse<T>>(){}.getType();
     * @return the required type
     */
    @NonNull
    protected abstract Type getResponseType();

    /**
     * {@link Request.Method} to be user for the request
     * @return One of {@link Request.Method} types
     */
    @RequestMethod
    protected abstract int getRequestMethod();

    /**
     * Add a query parameter to the request url.
     * @param name parameter name.
     * @param value parameter value.
     */
    protected void addQueryParam(String name, Object value) {
        if(queryParams == null) {
            queryParams = new HashMap<>();
        }

        if(value == null) {
            queryParams.remove(name);
        }

        queryParams.put(name, String.valueOf(value));
    }


    protected void addPostParam(String name, Object value) {

        if(getRequestMethod() != Request.Method.POST) {
            throw new IllegalArgumentException("Not POST request");
        }

        if(postParams == null) {
            postParams = new HashMap<>();
        }

        if(value == null) {
            postParams.remove(name);
        }

        postParams.put(name, String.valueOf(value));
    }

    protected void addHeader(String name, String value) {
        if(headers == null) {
            headers = new HashMap<>();
        }

        if(value == null) {
            headers.remove(name);
        }

        headers.put(name, value);
    }

    protected void setBody(String body) {
        this.body = body;
    }

    @NonNull
    protected GsonBuilder getGson() {
        return new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateMillisDeserializer());
    }

    public void execute(final Context context) {
        execute(context, null);
    }

    /**
     * Execute this task.
     * @param context context.
     * @param listener listener
     */
    public void execute(Context context, final OnTaskExecuted<T> listener) {
        this.listener = listener;
        this.context = context;
        request = new GsonRequest<>(getRequestMethod(), getUrl(),
                getResponseType(),TAG, this, this);

        executeRequest(this.context);
    }

    public T executeSync(Context context, Response.ErrorListener errorListener) {
        RequestFuture<ServerResponse<T>> requestFuture = RequestFuture.newFuture();
        request = new GsonRequest<>(getRequestMethod(), getUrl(),
                getResponseType(), TAG, requestFuture, errorListener);
        executeRequest(context);

        try {
            ServerResponse<T> response = requestFuture.get(
                    REQUEST_TIMEOUT, TimeUnit.MILLISECONDS);
            if(response.isSuccess()) {
                return response.getResult();
            }
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
            if(BuildConfig.DEBUG) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG,"error: "+e.getMessage(),e);
                        if (NetworkTask.this.context != null)
                            Toast.makeText(NetworkTask.this.context, "error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
            if(errorListener != null) {
                errorListener.onErrorResponse(new VolleyError(e));
            }
        }

        return null;
    }

    private void executeRequest(Context context) {

        if(BuildConfig.DEBUG) {
            printRequest();
        }

        request.setGson(getGson().create());
        request.setHeaders(headers);
        request.setBody(body);
        request.setParams(postParams);

        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue queue = NetworkUtils.getRequestQueue(context);
        queue.add(request);
    }

    @Override
    public void onResponse(T response) {
        Log.d(TAG, "parsed response:\n" + response.toString());
        listener.onSuccess(response);
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        if(context == null) return;
        onError(new ErrorResponse(ErrorResponse.NETWORK_ERROR, context.getString(R.string.connection_problem)), volleyError);
    }

    private void onError(final ErrorResponse error, Exception e) {

        {
            if(listener != null) {
                listener.onError(error);
            }

            Log.d(TAG, "error: " + error.getMessage(), e);
            if(BuildConfig.DEBUG) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    public void cancel() {
        if(request != null) {
            request.cancel();
        }
    }

    /**
     * Concatenates all the pieces of the url. (com.mvctesting.base, path, relative path and parameters)
     * @return the final url.
     */
    protected String getUrl() {
        Uri.Builder builder = Uri.parse(NetworkUtils.getServerUrl()).buildUpon()
                //.appendPath("api")
                .appendEncodedPath(getRelativePath());

        if(queryParams != null) {
            for(String key : queryParams.keySet()) {
                builder.appendQueryParameter(key, queryParams.get(key));
            }
        }

        /*
        final String DROPBOX = "https://dl.dropboxusercontent.com/u/13432366/privac/%s.json";
        url = String.format(DROPBOX, getRelativePath());*/

        return builder.build().toString();
    }

    private void printRequest() {
        Log.d(TAG, "request method: " + parseRequestMethod());
        Log.d(TAG, "url: " + getUrl());
        if(postParams != null) {
            for(String key : postParams.keySet()) {
                Log.d(TAG, "post param -> " + key + "=" + postParams.get(key));
            }
        }
        if(body != null) {
            Log.d(TAG, "body:");
            //noinspection EmptyCatchBlock
            try {
                Log.d(TAG, new JSONObject(body).toString(4));
            } catch (Exception e){}
        }

        if(headers != null) {
            for(String key : headers.keySet()) {
                Log.d(TAG, "header -> " + key + "=" + headers.get(key));
            }
        }
    }

    private String parseRequestMethod() {
        switch (getRequestMethod()) {
            case Request.Method.GET:
                return "GET";
            case Request.Method.POST:
                return "POST";
            case Request.Method.DELETE:
                return "DELETE";
            case Request.Method.PUT:
                return "PUT";
        }
        return "UNKNOWN";
    }
}
