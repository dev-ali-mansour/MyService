package dev.alimansour.myservice.service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;
import dev.alimansour.myservice.R;

public class NotificationService extends Service {
    private boolean isRunning = true;
    private NotificationManager manager;
    private int notificationId;
    private String message;
    private String title;

    public NotificationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        title = intent.getStringExtra("title");
        message = intent.getStringExtra("message");
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Thread thread = new Thread(() -> {
            while (isRunning) {
                try {
                    Thread.sleep(15000);
                    createNotification();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isRunning = false;
    }

    private void createNotification() {
        createNotificationChannel();
        Intent intent = new Intent(this, NotificationService.class);
        @SuppressLint("WrongConstant")
        PendingIntent pendingIntent = PendingIntent.
                getActivity(this, 0, intent, Intent.FLAG_ACTIVITY_NEW_TASK);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, getString(R.string.app_name));
        builder.setContentTitle(title);
        builder.setContentText(message);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.addAction(android.R.drawable.ic_media_play, "Play", pendingIntent);
        builder.addAction(android.R.drawable.ic_media_pause, "Pause", pendingIntent);

        Notification notification = builder.build();

        manager.notify(notificationId++, notification);
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.app_name);
            String description = getString(R.string.app_name);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(getString(R.string.app_name), name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            manager.createNotificationChannel(channel);
        }
    }
}