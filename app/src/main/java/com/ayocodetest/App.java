package com.ayocodetest;

import android.app.Application;

import com.ayocodetest.misc.DatabaseManager;

import io.realm.Realm;

/**
 * Created by Victor on 9/19/17.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        DatabaseManager.getInstance().init();
    }
}
