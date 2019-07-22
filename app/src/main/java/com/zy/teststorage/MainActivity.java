package com.zy.teststorage;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private StorageManager mStorageManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mStorageManager = StorageManager.getInstance();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(
                    new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    0);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

/*    private void printStorage(){
        //打印当前应用的路径
        mStorageManager.printStoragePath(this);

        //打印stock-robot的路径
        try {
            Context context =
                    createPackageContext("com.example.wiipu.stockrobot",
                            CONTEXT_IGNORE_SECURITY | CONTEXT_INCLUDE_CODE);
            if (context != null) {
                mStorageManager.printStoragePath(context);
                mStorageManager.printMediaStoreInfo(context);
                mStorageManager.printConvertedPath();
            }
        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(this, "找不到包名", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }*/

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        onRequestPermissionComplete();
    }

    private void onRequestPermissionComplete(){
        mStorageManager.printApplicationStoragePathInfo(this);
    }
}
