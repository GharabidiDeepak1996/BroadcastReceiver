package com.example.asynctaskdemo;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.example.asynctaskdemo.MainActivity.BROADCAST_INTENT_ACTION;

public class MyAsyncTask extends AsyncTask<String, Integer, Long> {
    private static final String TAG = "MyAsyncTask1";

    private Context mContext;

    public MyAsyncTask(Context context) {
        mContext = context;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.d(TAG, "onPreExecute: ");
        Toast.makeText(mContext, "Downloading Started...", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Long doInBackground(String... urls) {
        InputStream input = null;
        OutputStream output = null;
        HttpURLConnection connection = null;
        String url1 = urls[0];
        long fileLength = 0;
        Log.d(TAG, "doInBackground: " + url1);
        try {
            URL url = new URL(url1);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            // expect HTTP 200 OK, so we don't mistakenly save error report
            // instead of the file
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                Log.d(TAG, "doInBackground: " + "Server returned HTTP " + connection.getResponseCode()
                        + " " + connection.getResponseMessage());
            }

            // this will be useful to display download percentage
            // might be -1: server did not report the length
            fileLength = connection.getContentLength();

            // download the file
            input = connection.getInputStream();
            //output = new FileOutputStream("/sdcard/file_name.extension");

            byte data[] = new byte[4096];
            long total = 0;
            int count;
            while ((count = input.read(data)) != -1) {
                total += count;
                // publishing the progress....
                if (fileLength > 0)  {
                    // only if total length is known
                    int progress = (int) (total * 100 / fileLength);
                    publishProgress(progress);
                }
                //output.write(data, 0, count);
            }
        } catch (Exception e) {
            Log.e(TAG, "doInBackground: " + e.getMessage());
        } finally {
            try {
                if (output != null)
                    output.close();
                if (input != null)
                    input.close();
            } catch (IOException ignored) {
                Log.e(TAG, "doInBackground: " + ignored.getMessage());
            }

            if (connection != null)
                connection.disconnect();
        }
        return fileLength;
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        super.onProgressUpdate(progress);
        Log.d(TAG, "onProgressUpdate: " + progress[0]);
     //   mListener.onProgressChanged(progress[0]);

        Intent intent =new Intent(BROADCAST_INTENT_ACTION);
         intent.putExtra("progress",progress[0]);
        mContext.sendBroadcast(intent);




    }

    @Override
    protected void onPostExecute(Long result) {
        super.onPostExecute(result);
        Toast.makeText(mContext, result + " size Downloaded completely ", Toast.LENGTH_SHORT).show();
    }
}
