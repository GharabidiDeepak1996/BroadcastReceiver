package com.example.broadcastrecivernotification;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
private static  final String THIS_IS_MY_BROADCAST= "jkg";
    MyNotification myNotification;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        IntentFilter intentFilter=new IntentFilter(THIS_IS_MY_BROADCAST );
        registerReceiver(myNotification,intentFilter  );
    }

    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver( myNotification );
    }

    public void start(View view) {
        Intent intent=new Intent( THIS_IS_MY_BROADCAST );

sendBroadcast( intent );
    }
}
