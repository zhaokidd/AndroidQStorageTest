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
public class   StorageManager {
    private final static String TAG = "StorageManager";
    private final static String SUFFIX_TEST_FILE = "/suffix_test_file";


    private String mExternalStoragePath; //外部共享目录

    private final static StorageManager mInstance = new StorageManager();

    private StorageManager() {
        Log.d(TAG,"storagemanager init");
    }

    /**
     * 打印storage路径
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void printStoragePath(Context context) {
        Log.d(TAG, "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< print storage path start"
                + context.getPackageName());
        String internPath = context.getFilesDir().getPath();
        String internCachePath = context.getCacheDir().getPath();
        String externalPath = context.getExternalFilesDir("").getPath();
        String externalCachePath = context.getExternalCacheDir().getPath();
//        String obbFilePath = context.getObbDir().getPath();

        //environment
        String envDataDir = Environment.getDataDirectory().getPath();
        String envStorageDir = Environment.getExternalStorageDirectory().getPath();
        String envStoragePublicDir = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getPath();

        String envStorageMoviesDir = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES).getPath();

        File[] externalMediaFileArray = context.getExternalMediaDirs();

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Log.d(TAG, Environment.getExternalStorageState());
            File[] secondaryFileDirs = context.getExternalFilesDirs(null);
            for (int i = 0; i < secondaryFileDirs.length; i++) {
                if (secondaryFileDirs[i] != null) {
                    String externalPathSecondary = secondaryFileDirs[i].getPath();
                    printPath("[ExternalFileDirs]", "index:" + i
                            + " path:" + externalPathSecondary);
                    checkPathAccessible(externalPathSecondary.concat(SUFFIX_TEST_FILE));
                } else {
                    printPath("[ExternalFileDirs]", null);
                }
            }
        }
        Log.d(TAG, "----------- external public directory-------------------");
        String externalPublicDirectory = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
                .getPath();
        printPath("externalPublicDirectory", externalPublicDirectory);
        checkPathAccessible(externalPublicDirectory.concat(SUFFIX_TEST_FILE));

        Log.d(TAG, "----------- external path -------------------");
        printPath("external file path", externalPath.concat(SUFFIX_TEST_FILE));
        checkPathAccessible(externalPath.concat(SUFFIX_TEST_FILE));

        Log.d(TAG, "------------ external cache path ------------");
        printPath("external cache path", externalCachePath.concat(SUFFIX_TEST_FILE));
        checkPathAccessible(externalCachePath.concat(SUFFIX_TEST_FILE));

        Log.d(TAG, "----------- intern path -------------------");
        printPath("[InternPath]", internPath.concat(SUFFIX_TEST_FILE));
        checkPathAccessible(internPath.concat(SUFFIX_TEST_FILE));

        Log.d(TAG, "----------- interna cache path-------------------");
        printPath("[InternCachePath]", internCachePath.concat(SUFFIX_TEST_FILE));
        checkPathAccessible(internCachePath.concat(SUFFIX_TEST_FILE));

//        Log.d(TAG, "----------- obb path-------------------");
//        printPath("[ObbPath]", obbFilePath.concat(SUFFIX_TEST_FILE));
//        checkPathAccessible(obbFilePath.concat(SUFFIX_TEST_FILE));

        Log.d(TAG, "------------ external media dirs ----------");
        for (File file : externalMediaFileArray) {
            if (file != null) {
                printPath("[ExternalMediaFile]", file.getPath().concat(SUFFIX_TEST_FILE));
                checkPathAccessible(file.getPath().concat(SUFFIX_TEST_FILE));
            }
        }

        Log.d(TAG, "------------- external storage environment directory");
        printPath("[Env-External-Directory]:", envStorageDir.concat(SUFFIX_TEST_FILE));
        checkPathAccessible(envStorageDir.concat(SUFFIX_TEST_FILE));

        Log.d(TAG, "-------------- external storage environment public directory");
        printPath("[Env-External-Pub-Dir]:", envStoragePublicDir.concat(SUFFIX_TEST_FILE));
        checkPathAccessible(envStoragePublicDir.concat(SUFFIX_TEST_FILE));

        Log.d(TAG, "--------------- external storage environment movie directory");
        printPath("[Env-External-Movie-Dir]", envStorageMoviesDir.concat(SUFFIX_TEST_FILE));
        checkPathAccessible(envStorageMoviesDir.concat(SUFFIX_TEST_FILE));

        Log.d(TAG, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> print storage end."
                + context.getPackageName());
    }

    private void checkPathAccessible(String path) {
        readFile(path);
        writeFile(path);
        readFile(path);
    }

    private static void printPath(String type, String value) {
        Log.d(TAG, "[" + type + "]:" + value);
    }


    private void writeFile(String filePath) {
        File file = new File(filePath);

        if (!file.getParentFile().exists()) {
            Log.d(TAG, "parent path doesn't exits, try to mkdir");
            file.getParentFile().mkdirs();
        }

        if (file.exists()) {
            Log.d(TAG, "file exits, try to delete");
            boolean result = file.delete();
            Log.d(TAG, "delete file result :" + result);
        }
        boolean result = false;
        try {
            result = file.createNewFile();
        } catch (IOException e) {
            Log.e(TAG, "create file failed " + e.getMessage());
            e.printStackTrace();
        }
        Log.d(TAG, "create file  result:" + result);

        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(new File(filePath));
            fileWriter.write("test storageManager");
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

    private void readFile(String filePath) {
        try {
            FileReader fileReader = new FileReader(new File(filePath));
            BufferedReader br = new BufferedReader(fileReader);
            String firstLine = br.readLine();
            Log.d(TAG, "[READ_LINE]:" + firstLine);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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


    public void printConvertedPath() {
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
    }

    public void printStorageSpace(){

    }

    public static StorageManager getInstance() {
        return mInstance;
    }
}
