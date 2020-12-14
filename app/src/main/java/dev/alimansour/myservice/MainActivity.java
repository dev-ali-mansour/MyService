package dev.alimansour.myservice;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import dev.alimansour.myservice.service.NotificationService;

public class MainActivity extends AppCompatActivity {
    private Button startButton, stopButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = findViewById(R.id.startButton);
        stopButton = findViewById(R.id.stopButton);

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

    }
}