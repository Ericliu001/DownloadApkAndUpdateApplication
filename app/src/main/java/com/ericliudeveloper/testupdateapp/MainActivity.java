package com.ericliudeveloper.testupdateapp;

import android.app.DownloadManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.File;


public class MainActivity extends ActionBarActivity {

    public static final String PREFS_NAME = "MyPrefsFile";
    public static final String APK_ABSOLUTE_PATH = "apk absolute path";
    public static final String DOWNLOAD_ID = "download id";
    DownloadManager mDownloadManager;
    Uri uri = Uri.parse("https://docs.google.com/uc?export=download&id=0B_ApnWiMp4bVY0lwa0kxbWt5aFU");
    public static String UPDATE_APK_FILENAME = "update_apk_filename.apk";
    File destFile;
    private long downloadID = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDownloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
    }


    public void onButtonClicked(View view) {
        if (! isExternalStorageWritable()) {
            Toast.makeText(this, "External Storage not available. ", Toast.LENGTH_SHORT).show();
            return;
        }


        // Get the directory for the user's public download directory.
         destFile = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS), UPDATE_APK_FILENAME);






        DownloadManager.Request downloadRequest = new DownloadManager.Request(uri);
        downloadRequest.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI
                | DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false)
                .setTitle("Updating...")
                .setDescription("Updating Application.")
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,
                        UPDATE_APK_FILENAME);

       downloadID =  mDownloadManager.enqueue(downloadRequest);

        SharedPreferences downloadApkInfo = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = downloadApkInfo.edit();
        editor.putString(APK_ABSOLUTE_PATH , destFile.getAbsolutePath().toString());
        editor.putLong(DOWNLOAD_ID, downloadID);
        // do NOT forget to commit!!!!!
        editor.commit();
    }





    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
