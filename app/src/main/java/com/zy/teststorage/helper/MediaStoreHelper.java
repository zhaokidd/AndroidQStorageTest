package com.zy.teststorage.helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.support.v4.content.FileProvider;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class MediaStoreHelper {
    private static final String TAG = "MediaStoreHelper";
    private static final int CREATE_DOC_CODE = 43;

    public void launchDocumentPicker(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("download/*");
        activity.startActivityForResult(intent, 0);
    }

    public void creatNewDocument(Activity activity, String mimeType, String fileName) {
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType(mimeType);
        intent.putExtra(Intent.EXTRA_TITLE, fileName);
        activity.startActivityForResult(intent,CREATE_DOC_CODE);
    }

    public void creatNewDownloadFile(Activity activity,String mimeType,String fileName){
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
    }

    public void createFileUsingProvider(Activity activity,String path) {
        String tag = "[FILE_PROVIDER_WRITE]: ";
        File file = new File(path);
        Uri fileUri = FileProvider.getUriForFile(activity, "com.zy.teststorage.fileprovider", file);
        activity.grantUriPermission(activity.getPackageName(), fileUri,
                Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);

        //write file descriptor
        try {
            ParcelFileDescriptor pfdWrite = activity.getContentResolver().openFileDescriptor(fileUri, "w");
            FileOutputStream fos = new FileOutputStream(pfdWrite.getFileDescriptor());
            fos.write("test fileprovider, write something using filedescriptor".getBytes());
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            Log.e(TAG, tag + "FileNotFoundException");
            e.printStackTrace();
        } catch (IOException e) {
            Log.d(TAG, tag + "IOException");
            e.printStackTrace();
        }


        String readTag = "[FILE_PROVIDER_READ]: ";
        //read file descriptor
        try {
            ParcelFileDescriptor pfdRead = activity.getContentResolver()
                    .openFileDescriptor(fileUri, "r");
            FileReader fileReader = new FileReader(pfdRead.getFileDescriptor());
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = null;
            while ((line = bufferedReader.readLine()) != null)
                Log.d(TAG, readTag + " line :  " + line + "\n");
        } catch (FileNotFoundException e) {
            Log.e(TAG, readTag + "FileNotFoundException");
            e.printStackTrace();
        } catch (IOException e) {
            Log.e(TAG, readTag + "IOException");
            e.printStackTrace();
        }

    }



}
