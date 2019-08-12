package com.example.broadcastmessage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText et;
    private static final String This_is_my="broasdcastreceiver";
    broadcast broadcast=new broadcast();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        et=findViewById( R.id.txtMsg );
        IntentFilter intentFilter=new IntentFilter(This_is_my );
        registerReceiver( broadcast,intentFilter);
    }
    public void onDestroy() {

        super.onDestroy();
        unregisterReceiver( broadcast );
    }

    public void onClickShowBroadcast(View view) {
        Intent intent=new Intent( This_is_my);  //fixed to pass the parameter and same has intentFilter
        String name=et.getText().toString();
        intent.putExtra( "data",name);
       // intent.setAction( This_is_my ); optional
        sendBroadcast( intent );
    }
}
