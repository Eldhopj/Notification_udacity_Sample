package com.example.eldho.notification_udacity_sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
/**Manifest add vibration permission
 *          android:launchMode="singleTop" -> for performance improvements*/
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void testNotification(View view) {
        NotificationUtils.remindUserBecauseCharging(getApplicationContext());
    }
}
