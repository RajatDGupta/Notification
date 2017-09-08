package com.example.neeraj.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class NotificationViewActivity extends AppCompatActivity {
    TextView textView_title,textView_message;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_view);

        textView_title=(TextView)findViewById(R.id.title);
        textView_message=(TextView)findViewById(R.id.Message);
        imageView=(ImageView) findViewById(R.id.image);

        String title=getIntent().getStringExtra("title");
        String message=getIntent().getStringExtra("message");
        String icon=getIntent().getStringExtra("icon");


        textView_title.setText(title);
        textView_message.setText(message);
        Glide.with(this).load(icon).into(imageView);
        //http://neerajgupta.netne.net/NotificationApp/Icons/notification.png
    }
}
