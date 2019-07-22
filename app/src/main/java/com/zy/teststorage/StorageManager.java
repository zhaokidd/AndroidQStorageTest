package com.zy.teststorage;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 一.
 * Android 4.4之后,所有的external storage 路径,
 * 除了应用的专属路径,其它应用的路径及公共目录都需要权限.
 * WRITE_EXTERNAL_STORAGE READ_EXTERNAL_STORAGE.
 * READ_EXTERNAL权限和WRITE_EXTERNAL权限只要授予了一个，另外一个就会自动拥有。权限组的概念。
 * <p>
 * 二.
 * AndroidQ :
 * １．应用沙箱：所有的应用都被隔离在单独的空间内。
 */
public class StorageManager {
    private final static String TAG = "StorageManager";

    private List<AppStorageInfo> infos;

    private StorageManagerHelper helper;

    private static class InstanceHolder {
        private final static StorageManager mInstance = new StorageManager();

    }

    public static StorageManager getInstance() {
        return InstanceHolder.mInstance;
    }

    private StorageManager() {
        infos = new ArrayList<>();
        helper = new StorageManagerHelper();
    }

    /**
     * application context
     */
    public void printApplicationStoragePathInfo(Context context) {
        AppStorageInfo appStorageInfo = new AppStorageInfo(context);
        infos.add(appStorageInfo);
        helper.printAppStorageInfo(appStorageInfo);
    }

    private void d(){

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void printMediaStoreInfo(Context context) {

        Uri mediaUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        Log.d(TAG, "[MediaStore-media-uri]:" + mediaUri);
        Cursor mediaCursor = context.getContentResolver().query(mediaUri,
                null,
                null,
                null,
                null,
                null);
        Log.d(TAG, "[video count]:" + mediaCursor.getCount());
        mediaCursor.close();

        Uri externalUri = MediaStore.Files.getContentUri("external");
        Cursor cursor = context.getContentResolver().query(externalUri, null, null, null, null);
        Log.d(TAG, "[All files count]" + cursor.getCount());
        cursor.close();

    }


/*    public void printConvertedPath() {
        File externalPubPath = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File picPath = new File(externalPubPath, "image");

        Log.d(TAG, "[Before-converted-pic-path]:" + picPath.getPath());

        Uri uri = null;
        if (!picPath.exists()) {
            try {
                picPath.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

//        if(picPath.exists()){
        uri = Uri.fromFile(picPath);
//        }
        Log.d(TAG, "[Converted-pic-path]:" + uri.getPath());
    }*/

}
