package com.ayocodetest.misc;

import com.ayocodetest.model.HistoryData;
import com.ayocodetest.model.WeatherData;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by Victor on 9/19/17.
 */

public class DatabaseManager {

    Realm realm;

    private static final DatabaseManager ourInstance = new DatabaseManager();

    public static DatabaseManager getInstance() {
        return ourInstance;
    }

    private DatabaseManager() {
    }

    public void init() {
        RealmConfiguration config = new RealmConfiguration.Builder()
                                            .deleteRealmIfMigrationNeeded()
                                            .build();
        Realm.setDefaultConfiguration(config);
        realm = Realm.getDefaultInstance();
    }

    public RealmResults<HistoryData> getSearchHistory(){
        return realm.where(HistoryData.class).findAllSorted("timeSvaed", Sort.DESCENDING);
    }

    public void saveWeatherDataToHistory(WeatherData weatherData) {
        realm.beginTransaction();
        HistoryData historyData = new HistoryData();
        historyData.setCityName(weatherData.getCityName());
        historyData.setIconId(weatherData.getIconCode());
        historyData.setCurrentTemperature(weatherData.getTemperatureValue());
        historyData.setHumidty(weatherData.getHumidityTemp());
        historyData.setMaxTemp(weatherData.getMaxTemp());
        historyData.setMinTemp(weatherData.getMinTemp());
        historyData.setTimeSvaed(new Date().getTime());
        realm.copyToRealm(historyData);
        realm.commitTransaction();
    }
}
