package dev.alimansour.myservice.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class CounterService extends Service {
    private final CounterBinder counterBinder = new CounterBinder();
    private int counter = 0;

    public CounterService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("CounterService", "onCreate()");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return counterBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("CounterService", "onDestroy()");
    }

    public int getCounter() {
        return counter++;
    }

    public class CounterBinder extends Binder {
        public CounterService getCounterService() {
            return CounterService.this;
        }
    }

}