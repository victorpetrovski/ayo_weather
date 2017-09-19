package com.ayocodetest.util;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Date;

/**
 * Created by Victor on 9/2/16.
 */
public class DateMillisDeserializer implements JsonDeserializer<Date> {
    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {

        try {
            long time = json.getAsLong();
            return new Date(time);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }

        return new Date();
    }
}
