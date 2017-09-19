package com.ayocodetest.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ayocodetest.R;
import com.ayocodetest.base.BaseActivity;
import com.ayocodetest.model.WeatherData;
import com.ayocodetest.network.response.ErrorResponse;
import com.ayocodetest.network.task.GetCurrentWeather;
import com.ayocodetest.network.util.NetworkTask;
import com.bumptech.glide.Glide;
import com.google.android.gms.maps.model.LatLng;

import butterknife.BindView;

public class MainActivity extends BaseActivity {


    @BindView(R.id.iv_weather_icon)
    ImageView ivWeatherIcon;

    @BindView(R.id.tv_weather_results)
    TextView tvWeatherResults;

    @BindView(R.id.tv_weather_details)
    TextView tvWeatherDetails;

    @BindView(R.id.tv_weather_min)
    TextView tvWeatherMin;

    @BindView(R.id.tv_weather_humidty)
    TextView tvWeatherHumidty;

    @BindView(R.id.tv_weather_max)
    TextView tvWeatherMax;


    WeatherData currentData;

    public final static String LOCATION_BUNDLE_EXTRAS = "com.ayocodetest.activities.LOCATION_BUNDLE_EXTRAS";

    public static  void startMainActivity(Context context,LatLng location) {
        Intent intent = new Intent(context,MainActivity.class);
        intent.putExtra(LOCATION_BUNDLE_EXTRAS, location);
        context.startActivity(intent);
    }

    LatLng currentLocation;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        if (!intent.hasExtra(LOCATION_BUNDLE_EXTRAS)) {
            Toast.makeText(this, R.string.intent_location_bundle_error, Toast.LENGTH_LONG).show();
            finish();
        }

        currentLocation = intent.getParcelableExtra(LOCATION_BUNDLE_EXTRAS);
        loadWeatherData();
    }

    private void loadWeatherData() {
        if (currentLocation == null) {
            Toast.makeText(this, R.string.intent_location_bundle_error, Toast.LENGTH_LONG).show();
            return;
        }

        new GetCurrentWeather(currentLocation).execute(this, new NetworkTask.OnTaskExecuted<WeatherData>() {
            @Override
            public void onSuccess(WeatherData result) {
                currentData = result;
                updateUI();
            }

            @Override
            public void onError(ErrorResponse error) {
                Toast.makeText(MainActivity.this, String.format(getString(R.string.error_retriving_location), error.getMessage()), Toast.LENGTH_LONG).show();
            }
        });

    }

    private void updateUI() {
        if (currentData == null)
            return;

        Glide.with(this).load(String.format(getString(R.string.weather_icon), currentData.getIconCode())).into(ivWeatherIcon);
        tvWeatherResults.setText(String.format(getString(R.string.weather_result_in_celsius), currentData.getTemperatureValue()));
        tvWeatherDetails.setText(String.format(getString(R.string.weather_details), currentData.getCityName(), currentData.getLocationCords()));
        tvWeatherHumidty.setText(String.format(getString(R.string.weather_humidty), currentData.getHumidityTemp()));
        tvWeatherMax.setText(String.format(getString(R.string.weather_max), currentData.getMaxTemp()));
        tvWeatherMin.setText(String.format(getString(R.string.weather_min), currentData.getMinTemp()));
    }

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }
}
