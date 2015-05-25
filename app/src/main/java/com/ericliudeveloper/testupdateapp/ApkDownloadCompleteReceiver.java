package com.ericliudeveloper.testupdateapp;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.widget.Toast;

import java.io.File;

public class ApkDownloadCompleteReceiver extends BroadcastReceiver {
    public ApkDownloadCompleteReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);

        SharedPreferences savedInfo = context.getSharedPreferences(MainActivity.PREFS_NAME, 0);
        long downloadID = savedInfo.getLong(MainActivity.DOWNLOAD_ID, -1);
        String apkPath = savedInfo.getString(MainActivity.APK_ABSOLUTE_PATH, "");
        File file = new File(apkPath);

        if ( id >=0 && id == downloadID &&  file.exists() &&  DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(intent.getAction())) {
            Toast.makeText(context, "Download complete!", Toast.LENGTH_SHORT).show();


            Intent intentInstall = new Intent(Intent.ACTION_VIEW);
            intentInstall.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intentInstall.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intentInstall.setDataAndType(Uri.fromFile(file),
                    "application/vnd.android.package-archive");

            context.startActivity(intentInstall);
        }
    }



}
