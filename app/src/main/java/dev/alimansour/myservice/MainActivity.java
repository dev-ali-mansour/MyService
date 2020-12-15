package dev.alimansour.myservice;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import dev.alimansour.myservice.service.CounterService;
import dev.alimansour.myservice.service.MyIntentService;
import dev.alimansour.myservice.service.NotificationService;

public class MainActivity extends AppCompatActivity implements ServiceConnection {
    private Button startButton, stopButton, bindServiceButton, countButton, unbindServiceButton, startIntentServiceButton;
    private CounterService.CounterBinder counterBinder;
    private CounterService counterService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = findViewById(R.id.startButton);
        stopButton = findViewById(R.id.stopButton);
        bindServiceButton = findViewById(R.id.bindServiceButton);
        countButton = findViewById(R.id.countButton);
        unbindServiceButton = findViewById(R.id.unBindServiceButton);
        startIntentServiceButton = findViewById(R.id.startIntentServiceButton);

        startButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, NotificationService.class);
            intent.putExtra("title", "Android Service");
            intent.putExtra("message", "Welcome to Android Service!");
            startService(intent);
        });
        stopButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, NotificationService.class);
            stopService(intent);
        });

        bindServiceButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, CounterService.class);
            bindService(intent, this, BIND_AUTO_CREATE);
        });

        countButton.setOnClickListener(v -> {
            if (counterService != null) {
                int counter = counterService.getCounter();
                Toast.makeText(this, "Counter Value: " + counter, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Please bind service first!", Toast.LENGTH_LONG).show();
            }
        });

        unbindServiceButton.setOnClickListener(v -> unbindService(this));

        startIntentServiceButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, MyIntentService.class);
            intent.putExtra("fullName", "Sara Sami");
            intent.putExtra("age", 24);
            intent.putExtra("gender", 'f');
            intent.putExtra("isAdmin", false);
            startService(intent);
        });
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        counterBinder = (CounterService.CounterBinder) service;
        counterService = counterBinder.getCounterService();
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        Toast.makeText(this, "onServiceDisconnected()", Toast.LENGTH_LONG).show();
    }
}