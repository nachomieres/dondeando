package com.android.nacho.dondeando;

import com.firebase.client.Firebase;

/**
 * Created by cris on 19/5/16.
 */
public class DondeAndoApp extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}