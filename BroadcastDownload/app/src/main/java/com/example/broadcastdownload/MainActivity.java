package com.example.broadcastdownload;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {
public static final String THIS_BROADCAST="this is my broadcast";
ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        progressBar=findViewById( R.id.progressBar );
        IntentFilter intentFilter = new IntentFilter(THIS_BROADCAST);

        registerReceiver(broadcastReceiver, intentFilter);

    }

    public void start(View view) {
        String url = "https://file-examples.com/wp-content/uploads/2017/04/file_example_MP4_1920_18MG.mp4";
        mybroadcast mybroadcast = new mybroadcast(this);
        mybroadcast.execute(url);
    }
BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {
       int progress=intent.getIntExtra( "progress",1 );
        progressBar.setProgress(progress);
    }
};
public void onDestroy() {
    super.onDestroy();
    unregisterReceiver( broadcastReceiver );
}

}
