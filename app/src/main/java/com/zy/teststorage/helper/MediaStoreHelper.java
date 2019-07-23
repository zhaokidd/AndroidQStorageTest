package com.zy.teststorage.helper;

import android.app.Activity;
import android.content.Intent;

public class MediaStoreHelper {
    private static final int CREATE_DOC_CODE = 43;

    public void launchDocumentPicker(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        activity.startActivityForResult(intent, 0);
    }

    public void creatNewDocument(Activity activity, String mimeType, String fileName) {
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType(mimeType);
        intent.putExtra(Intent.EXTRA_TITLE, fileName);
        activity.startActivityForResult(intent,CREATE_DOC_CODE);
    }
}
