package com.example.neeraj.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.neeraj.myapplication.NotificationPackage.NotificationPojo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    Button button;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.FCM_PREF), Context.MODE_PRIVATE);
                token = sharedPreferences.getString(getString(R.string.FCM_TOKEN), "");
                // Toast.makeText(MainActivity.this, tokent, Toast.LENGTH_SHORT).show();
                if (!token.isEmpty()) {
                    insertToken();
                }
            }
        });


    }

    private void insertToken() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<NotificationPojo> call = apiInterface.saveToken(token);
        call.enqueue(new Callback<NotificationPojo>() {
            @Override
            public void onResponse(Call<NotificationPojo> call, Response<NotificationPojo> response) {
                NotificationPojo notificationPojo = response.body();
                if (notificationPojo!= null) {
                    Toast.makeText(MainActivity.this, notificationPojo.getResponse(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NotificationPojo> call, Throwable t) {
                Toast.makeText(MainActivity.this, "PLEASE CHECK Connection", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
