package com.example.asynctaskdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity  {

    public static final String BROADCAST_INTENT_ACTION="broad_cast_intent_action";
    private static final String TAG = "MyAsyncTask";

    private ProgressBar mProgressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressBar = findViewById(R.id.progress_bar);
        IntentFilter intentFilter = new IntentFilter(BROADCAST_INTENT_ACTION);
        registerReceiver(broadcastReceiver,intentFilter);
    }

    public void start(View view) {

        Log.d(TAG, "start: before");
        String url = "https://file-examples.com/wp-content/uploads/2017/04/file_example_MP4_1920_18MG.mp4";
        MyAsyncTask myAsyncTask = new MyAsyncTask(this);
        myAsyncTask.execute(url);
        Log.d(TAG, "start: after");

    }




    private BroadcastReceiver broadcastReceiver =new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Log.d(TAG, "onReceive: ");
            int progress = intent.getIntExtra("progress",0);
            mProgressBar.setProgress(progress);
        }
    };


}
