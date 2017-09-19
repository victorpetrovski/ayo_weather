package com.ayocodetest.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Victor on 9/19/17.
 */

public class WeatherData {

    @SerializedName("name")
    private String cityName;

    @SerializedName("coord")
    private Coord coordinates;

    @SerializedName("main")
    private TemperatureModel temperature;

    @SerializedName("weather")
    private ArrayList<WeatherDescription> weatherDescription;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Coord getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coord coordinates) {
        this.coordinates = coordinates;
    }

    public TemperatureModel getTemperature() {
        return temperature;
    }

    public void setTemperature(TemperatureModel temperature) {
        this.temperature = temperature;
    }

    public WeatherDescription getWeatherDescription() {
        return weatherDescription.size() > 0 ? weatherDescription.get(0 ) : null;
    }

    public String getLocationCords() {
        String long_latFormat = "( lat: %1$.3f , lon: %2$.3f )";
        return String.format(long_latFormat,coordinates.getLat(),coordinates.getLon());
    }


    public String getIconCode() {
        if(getWeatherDescription() == null)
            return "0";
        return getWeatherDescription().getIcon();
    }

    public float getMaxTemp(){
        return temperature.getTempMax();
    }

    public float getMinTemp(){
        return temperature.getTempMin();
    }

    public float getHumidityTemp(){
        return temperature.getHumidity();
    }

    public String getTemperatureValue(){
        return String.valueOf(temperature.getTemp());
    }

}
