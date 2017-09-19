package com.ayocodetest;

import android.app.Application;

/**
 * Created by Victor on 9/19/17.
 */

public class App extends Application {
    private static final App ourInstance = new App();

    public static App getInstance() {
        return ourInstance;
    }

    private App() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
