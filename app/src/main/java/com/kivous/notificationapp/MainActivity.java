package com.kivous.notificationapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.res.ResourcesCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;

import com.bumptech.glide.Glide;

import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.kivous.notificationapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private static final String CHANNEL_ID = "One Channel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.bell2, null);
        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
        Bitmap largeIcon = bitmapDrawable.getBitmap();

        binding.backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.btnNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri soundUri = Uri.parse("android.resource://" + getApplicationContext().getPackageName() + "/" + R.raw.quite_impressed);

                createNotificationChannel(CHANNEL_ID, "One Channel", soundUri);

                NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_android).setContentTitle("Notification Title")
                        .setContentText("Notification Content")
                        .setSubText("New Message")
                        .setPriority(NotificationCompat.PRIORITY_HIGH);

                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(MainActivity.this);
                notificationManager.notify(99, builder.build());
            }
        });

        binding.btnNotification2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri soundUri = Uri.parse("android.resource://" + getApplicationContext().getPackageName() + "/" + R.raw.possible);

                createNotificationChannel("Two Channel", "Two Channel", soundUri);

                NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, "Two Channel")
                        .setSmallIcon(R.drawable.ic_android).setContentTitle("Notification Title").setLargeIcon(largeIcon)
                        .setContentText("Notification Content")
                        .setSubText("New Message")
                        .setPriority(NotificationCompat.PRIORITY_HIGH);

                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(MainActivity.this);
                notificationManager.notify(100, builder.build());

            }
        });

        binding.btnNotificationIntent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, NewActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                PendingIntent pendingIntent = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
                }

                Uri soundUri = Uri.parse("android.resource://" + getApplicationContext().getPackageName() + "/" + R.raw.surprise);

                createNotificationChannel("Intent Channel", "Intent Channel", soundUri);

                //Big picture Style
                Drawable drawable2 = ResourcesCompat.getDrawable(getResources(), R.drawable.img, null);
                BitmapDrawable bitmapDrawable2 = (BitmapDrawable) drawable2;
                Bitmap largeIcon2 = bitmapDrawable2.getBitmap();
                NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle()
                        .bigPicture(largeIcon2).setBigContentTitle("Big content title")
                        .setSummaryText("Summery text");

                NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, "Intent Channel")
                        .setSmallIcon(R.drawable.ic_android)
                        .setLargeIcon(largeIcon)
                        .setContentTitle("Intent Notification Title")
                        .setContentText("Content Text of Intent Notification")
                        .setSubText("New Message")
                        .setStyle(bigPictureStyle)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true);

                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(MainActivity.this);
                notificationManager.notify(101, builder.build());
            }
        });

        binding.btnCustomNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RemoteViews lay = new RemoteViews(getPackageName(), R.layout.custom_notification_layout);

                Uri soundUri = Uri.parse("android.resource://" + getApplicationContext().getPackageName() + "/" + R.raw.twinkle);

                createNotificationChannel("Custom Channel", "Custom Channel", soundUri);

                NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, "Custom Channel")
                        .setSmallIcon(R.drawable.ic_android).
                        setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                        .setCustomContentView(lay)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setAutoCancel(false);

                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(MainActivity.this);
                notificationManager.notify(102, builder.build());
            }
        });

        binding.loadImageFromUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notification_url_layout);

                String gtc = "https://geartocare.com/wp-content/uploads/2019/10/logo-min-300x300.png";

                Glide.with(MainActivity.this).asBitmap().load(binding.et.getText().toString()).into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        remoteViews.setImageViewBitmap(R.id.iv, resource);

                        Uri soundUri = Uri.parse("android.resource://" + getApplicationContext().getPackageName() + "/" + R.raw.twinkle);

                        createNotificationChannel("Custom Channel", "Custom Channel", soundUri);

                        NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, "Custom Channel")
                                .setSmallIcon(R.drawable.ic_android)
                                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                                .setCustomContentView(remoteViews)
                                .setPriority(NotificationCompat.PRIORITY_HIGH)
                                .setAutoCancel(false);

                        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(MainActivity.this);
                        notificationManager.notify(103, builder.build());
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
            }
        });

    }

    private void createNotificationChannel(String CHANNEL_ID, String channelName, Uri soundUri) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).setUsage(AudioAttributes.USAGE_NOTIFICATION).build();

            String description = "Channel description";
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription(description);
            channel.setSound(soundUri, audioAttributes);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}