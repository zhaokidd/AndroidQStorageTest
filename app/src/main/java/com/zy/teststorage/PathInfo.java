package com.zy.teststorage;

import android.text.TextUtils;

public class PathInfo {
    private final static String SUFFIX_TEST_FILE = "/suffix_test_file";


    String path;
    String tagName;

    public PathInfo(String tagName, String path) {
        if (!TextUtils.isEmpty(path)) {
            this.path = path.concat(SUFFIX_TEST_FILE);
        } else {
            this.path = path;
        }
        this.tagName = tagName;
    }
}
