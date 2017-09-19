package com.ayocodetest.network.util;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Victor on 8/27/16.
 */
public class NetworkUtils {

    private static RequestQueue sRequestQueue;
    private static String sToken;

    private static final String DEV_URL  =  "http://api.openweathermap.org/data/2.5";


    public static String getServerUrl() {
        return DEV_URL;
    }

    public static RequestQueue getRequestQueue(Context context) {
        if (sRequestQueue == null)
            sRequestQueue = Volley.newRequestQueue(context);

        return sRequestQueue;
    }

    //Headers Keys
    public static final String APP_ID = "343017ba45617cc3f9d25067230a5b6f";

}