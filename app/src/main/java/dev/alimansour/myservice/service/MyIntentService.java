package dev.alimansour.myservice.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class MyIntentService extends IntentService {

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("MyIntentService", "onCreate()");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            String fullName = bundle.getString("fullName");
            int age = bundle.getInt("age");
            char gender = bundle.getChar("gender");
            boolean isAdmin = bundle.getBoolean("isAdmin");
            int x = 1;
            while (x <= 3) {
                String mGender = (gender == 'm' || gender == 'M') ? "Male" : "Female";
                Log.d("MyIntentService", x+" Full Name: " + fullName + ", Age: " + age + ", Gender: " + mGender + ", Is Admin: " + isAdmin);
                x++;
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("MyIntentService", "onDestroy()");
    }
}