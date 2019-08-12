package com.example.broadcastmessage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class broadcast extends BroadcastReceiver {
    private static final String TAG = "broadcast";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d( TAG, "onReceive: "+intent.getStringExtra( "data" ) );
       Toast.makeText( context,"this is form text:" +intent.getStringExtra( "data" ),Toast.LENGTH_LONG ).show();
    }
}
