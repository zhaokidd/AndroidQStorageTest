package com.zy.teststorage.helper;

import android.os.Build;
import android.os.StatFs;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.zy.teststorage.model.AppStorageInfo;

public class StatsHelper {
    private static final String TAG = "StatsHelper";

    public void printStorageSpace(AppStorageInfo storageInfo) {
        String tag = "[Stats]: ";
        StatFs statFs = new StatFs(storageInfo.envStoragePublicPath);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            Log.d(TAG, tag + statFs.getAvailableBytes());
        }
    }
}
