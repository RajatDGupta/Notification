package com.example.neeraj.myapplication;

import com.example.neeraj.myapplication.NotificationPackage.NotificationPojo;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Neeraj on 9/6/2017.
 */

public interface ApiInterface {

    @FormUrlEncoded
    @POST("NotificationApp/fcm_insert.php")
    Call<NotificationPojo> saveToken(@Field("fcm_token") String fcm_token);
}
