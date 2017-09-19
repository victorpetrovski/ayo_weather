package com.ayocodetest.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.util.Date;

/**
 * Created by Victor on 9/2/16.
 */
public class DateMillisSerializer implements JsonSerializer<Date> {
    @Override
    public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {

        long time;

        if(src != null) {
            time = src.getTime();
        } else {
            time = System.currentTimeMillis();
        }

        return new JsonPrimitive(time);
    }
}