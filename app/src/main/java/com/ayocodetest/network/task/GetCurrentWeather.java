package com.ayocodetest.network.task;

import android.support.annotation.NonNull;

import com.android.volley.Request;
import com.ayocodetest.model.WeatherData;
import com.ayocodetest.network.util.BaseTokenTask;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * Created by Victor on 9/19/17.
 */

public class GetCurrentWeather extends BaseTokenTask<WeatherData> {

    public GetCurrentWeather(LatLng location) {
        addQueryParam("lat",location.latitude);
        addQueryParam("lon",location.longitude);
        addQueryParam("units","imperial");
    }

    @NonNull
    @Override
    protected String getRelativePath() {
        return "weather";
    }

    @NonNull
    @Override
    protected Type getResponseType() {
        return new TypeToken<WeatherData>(){}.getType();
    }

    @Override
    protected int getRequestMethod() {
        return Request.Method.GET;
    }

}
