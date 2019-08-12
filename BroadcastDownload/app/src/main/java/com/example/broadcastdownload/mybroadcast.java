package com.example.broadcastdownload;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.example.broadcastdownload.MainActivity.THIS_BROADCAST; //this one important

public class mybroadcast extends AsyncTask<String,Integer,Long> {
    private static final String TAG = "mybroadcast";
private  Context mcontext;

    public mybroadcast(Context context){
        Log.d( TAG, "mybroadcast: "+context );
        mcontext=context;
    }
    @Override
    protected Long doInBackground(String... strings) {
        InputStream input = null;
        OutputStream output = null;
        HttpURLConnection connection = null;
        long fileLength = 0;
       String url1=strings[0];
        try {
            URL url = new URL( url1);
            connection = ( HttpURLConnection ) url.openConnection();
            connection.connect();

            // expect HTTP 200 OK, so we don't mistakenly save error report
            // instead of the file
           /* if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                Log.d(TAG, "doInBackground: " + "Server returned HTTP " + connection.getResponseCode()
                        + " " + connection.getResponseMessage());
            }*/

            // this will be useful to display download percentage
            // might be -1: server did not report the length
            fileLength = connection.getContentLength();

            // download the file
            input = connection.getInputStream();
            //output = new FileOutputStream("/sdcard/file_name.extension");

            byte data[] = new byte[4096];
            long total = 0;
            int count;
            while ((count = input.read( data )) != -1) {
                total += count;
                // publishing the progress....
                if (fileLength > 0) {
                    // only if total length is known
                    int progress = ( int ) (total * 100 / fileLength);
                    //updateNotification(progress);

Intent intent=new Intent( THIS_BROADCAST );
intent.putExtra( "progress",progress );
mcontext.sendBroadcast( intent );
                    Log.d( TAG, "onStartCommand: Progress : " + progress );
                }
                //output.write(data, 0, count);
            }
        } catch (Exception e) {
            Log.e( TAG, "onStartCommand: " + e.toString() );
        } finally {
            try {
                if (output != null)
                    output.close();
                if (input != null)
                    input.close();
            } catch (IOException ignored) {
                Log.e( TAG, "onStartCommand: " + ignored.toString() );
                connection.disconnect();
            }
        }

        return fileLength;
    }

}
