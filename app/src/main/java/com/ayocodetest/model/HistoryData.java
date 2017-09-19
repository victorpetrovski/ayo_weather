package com.ayocodetest.model;

import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Victor on 9/19/17.
 */

public class HistoryData extends RealmObject {

    @PrimaryKey
    private String id = UUID.randomUUID().toString();

    private String cityName;

    private float currentTemperature;

    private String iconId;

    private float maxTemp;

    private float minTemp;

    private float humidty;

    private long timeSvaed;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public float getCurrentTemperature() {
        return currentTemperature;
    }

    public void setCurrentTemperature(float currentTemperature) {
        this.currentTemperature = currentTemperature;
    }

    public String getIconId() {
        return iconId;
    }

    public void setIconId(String iconId) {
        this.iconId = iconId;
    }

    public float getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(float maxTemp) {
        this.maxTemp = maxTemp;
    }

    public float getMinTemp() {
        return minTemp;
    }

    public void setTimeSvaed(long timeSvaed) {
        this.timeSvaed = timeSvaed;
    }

    public void setMinTemp(float minTemp) {
        this.minTemp = minTemp;
    }

    public float getHumidty() {
        return humidty;
    }

    public void setHumidty(float humidty) {
        this.humidty = humidty;
    }

}
