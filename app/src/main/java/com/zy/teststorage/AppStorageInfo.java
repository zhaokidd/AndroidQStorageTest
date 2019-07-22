package com.zy.teststorage;

import android.content.Context;
import android.os.Environment;

import java.util.ArrayList;
import java.util.List;

/**
 * 保存某个应用对应的应用信息.
 * */
public class AppStorageInfo {

    String appPackageName;

    //app self path info
    String internPath;
    String internCachePath;
    String externalPath;
    String externalCachePath;
    String externalMediaPath;


    //device environment path info
    String envDataPath;
    String envStoragePublicPath;
    String envStoragePublicVideoPath;


    List<PathInfo> pathInfos;


    AppStorageInfo(Context context) {
        pathInfos = new ArrayList<>();
        initPath(context);
    }

    private void initPath(Context context) {
        appPackageName = context.getPackageName();
        internPath = context.getFilesDir().getPath();
        internCachePath = context.getCacheDir().getPath();
        externalCachePath = context.getExternalCacheDir().getPath();
        externalPath = context.getExternalFilesDir(null).getPath();
        envDataPath = Environment.getDataDirectory().getPath();
        envStoragePublicPath = Environment.getExternalStoragePublicDirectory("").getPath();
        envStoragePublicVideoPath = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES)
                .getPath();

        pathInfos.add(new PathInfo("AppInternPath", internPath));
        pathInfos.add(new PathInfo("AppInternCachePath", internCachePath));
        pathInfos.add(new PathInfo("AppExternalPath", externalPath));
        pathInfos.add(new PathInfo("AppExternalMediaPath", externalMediaPath));
        pathInfos.add(new PathInfo("EnvDataPath", envDataPath));
        pathInfos.add(new PathInfo("envStoragePublicPath", envStoragePublicPath));
        pathInfos.add(new PathInfo("envStoragePublicMoviePath",envStoragePublicVideoPath));
    }

}
