package com.zy.teststorage.model;

import android.content.Context;
import android.os.Environment;

import com.zy.teststorage.model.PathInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 保存某个应用对应的应用信息.
 * */
public class AppStorageInfo {

    public String appPackageName;

    //app self path info
    public String internPath;
    public String internCachePath;
    public String externalPath;
    public String externalCachePath;
    public String externalMediaPath;


    //device environment path info
    public String envDataPath;
    public String envStorageDirectory;
    public String envStoragePublicPath;
    public String envStoragePublicVideoPath;


    public List<PathInfo> pathInfos;


    public AppStorageInfo(Context context) {
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
        envStorageDirectory = Environment.getExternalStorageDirectory().getPath();

        pathInfos.add(new PathInfo("AppInternPath", internPath));
        pathInfos.add(new PathInfo("AppInternCachePath", internCachePath));
        pathInfos.add(new PathInfo("AppExternalPath", externalPath));
        pathInfos.add(new PathInfo("AppExternalMediaPath", externalMediaPath));
        pathInfos.add(new PathInfo("EnvDataPath", envDataPath));
        pathInfos.add(new PathInfo("envStorageDirectory", envStorageDirectory));
        pathInfos.add(new PathInfo("envStoragePublicPath", envStoragePublicPath));
        pathInfos.add(new PathInfo("envStoragePublicMoviePath",envStoragePublicVideoPath));
    }

}
