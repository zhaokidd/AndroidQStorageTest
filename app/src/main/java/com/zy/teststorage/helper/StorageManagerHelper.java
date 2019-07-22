package com.zy.teststorage.helper;

import android.text.TextUtils;
import android.util.Log;

import com.zy.teststorage.model.AppStorageInfo;
import com.zy.teststorage.model.PathInfo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * StorageManager Helper class
 */
public class StorageManagerHelper {
    private static final String TAG = "StorageManagerHelper";

    public void printAppStorageInfo(AppStorageInfo appStorageInfo) {
        Log.d(TAG, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        printAppPackageInfo(appStorageInfo);
        for (PathInfo pathInfo : appStorageInfo.pathInfos) {
            Log.d(TAG,"\n\n\n"+"-------------------------");
            printPathAccessible(pathInfo);
            Log.d(TAG,"\n\n\n"+"-------------------------");
        }

        Log.d(TAG, "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<" + "\n\n\n");
    }

    private void printAppPackageInfo(AppStorageInfo storageInfo) {
        String tag = "[PackageName]: ";
        Log.d(TAG, tag + storageInfo.appPackageName);
    }

    private void printPathAccessible(PathInfo pathInfo) {
        printPath(pathInfo);
        if(TextUtils.isEmpty(pathInfo.path))
            return;
        testReadFile(pathInfo.path);
        testWriteFile(pathInfo.path);
        //test read the content that written just now
        testReadFile(pathInfo.path);
    }

    private static void printPath(PathInfo pathInfo) {
        Log.d(TAG, pathInfo.tagName + " :" + pathInfo.path);
    }


    private void testWriteFile(String filePath) {
        String tag = "[WRITE]:";
        File file = new File(filePath);

        if (!file.getParentFile().exists()) {
            Log.d(TAG, tag + "parent path doesn't exits, try to mkdir");
            file.getParentFile().mkdirs();
        }

        if (file.exists()) {
            Log.d(TAG, tag + "file exits, try to delete");
            boolean result = file.delete();
            Log.d(TAG, tag + "delete file result :" + result);
        }
        boolean result = false;
        try {
            result = file.createNewFile();
        } catch (IOException e) {
            Log.e(TAG, tag + "create file failed " + e.getMessage());
            e.printStackTrace();
        }
        Log.d(TAG, tag + "create file  result:" + result);

        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(new File(filePath));
            fileWriter.write("test storageManager,path:" + filePath);
            fileWriter.flush();
            fileWriter.close();
        } catch (FileNotFoundException e) {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            Log.e(TAG, "FileNotFoundException");
            e.printStackTrace();
        } catch (IOException e) {
            Log.e(TAG, "IOException");
            e.printStackTrace();
        }
    }

    /**
     * 测试能否使用java-file api 读取
     */
    private void testReadFile(String filePath) {
        String tag = "[READ]";
        try {
            FileReader fileReader = new FileReader(new File(filePath));
            BufferedReader br = new BufferedReader(fileReader);
            String firstLine = br.readLine();
            Log.d(TAG, tag + ":" + firstLine);
        } catch (FileNotFoundException e) {
            Log.e(TAG, tag + ":" + "FileNotFoundException," + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            Log.e(TAG, tag + ":" + "IOException," + e.getMessage());
            e.printStackTrace();
        }
    }
}
