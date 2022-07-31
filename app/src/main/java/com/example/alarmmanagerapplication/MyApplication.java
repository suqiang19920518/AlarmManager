package com.example.alarmmanagerapplication;

import android.app.Application;
import android.content.Intent;
import android.content.IntentFilter;

public class MyApplication extends Application {

    private MyReceiver myReceiver;

    @Override
    public void onCreate() {
        super.onCreate();
        myReceiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_DATE_CHANGED);
        filter.addAction(MyReceiver.ACTION);
        registerReceiver(myReceiver, filter);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        unregisterReceiver(myReceiver);
    }
}
