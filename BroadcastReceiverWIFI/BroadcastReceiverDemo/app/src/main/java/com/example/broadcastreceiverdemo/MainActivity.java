package com.example.broadcastreceiverdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {
    Switch wifiSwitch;
    WifiManager wifiManager;

    public static final String BROADCAST_INTENT_ACTION = "broadcast_intent_action";
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        IntentFilter intentFilter = new IntentFilter( BROADCAST_INTENT_ACTION );
        registerReceiver( broadcastReceiver, intentFilter );

        wifiSwitch = findViewById( R.id.wifi_switch );
        Log.d( TAG, "onCreate: " + Context.WIFI_SERVICE );
        wifiManager = ( WifiManager ) getApplicationContext().getSystemService( Context.WIFI_SERVICE );

        wifiSwitch.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    wifiManager.setWifiEnabled( true );
                    wifiSwitch.setText( "WiFi is ON" );
                } else {
                    wifiManager.setWifiEnabled( false );
                    wifiSwitch.setText( "WiFi is OFF" );
                }
            }
        } );
    }





    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d( TAG, "onReceive context: "+context );
            Log.d( TAG, "onReceive intent: " +intent);

            int wifiStateExtra = intent.getIntExtra( WifiManager.EXTRA_WIFI_STATE,
                    WifiManager.WIFI_STATE_UNKNOWN );
            Log.d( TAG, "onReceive: " + wifiStateExtra );
            switch (wifiStateExtra) {
                case WifiManager.WIFI_STATE_ENABLED:
                    wifiSwitch.setChecked( true );
                    wifiSwitch.setText( "WiFi is ON" );
                    break;
                case WifiManager.WIFI_STATE_DISABLED:
                    wifiSwitch.setChecked( false );
                    wifiSwitch.setText( "WiFi is OFF" );
                    break;
            }
        }

    };
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver( broadcastReceiver );
    }
}

