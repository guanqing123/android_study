package com.example.client;

import android.net.Uri;
import android.provider.BaseColumns;

public class UserInfoContent implements BaseColumns {

    public static final String AUTHORITIES = "com.example.server.provider.UserInfoProvider";

    //content://com.example.server.provider.UserInfoProvider/user

    // 访问内容提供器的URI
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITIES);

    // 下面是该表的各个字段名称
    public static final String USER_NAME = "name";
    public static final String USER_AGE = "age";
    public static final String USER_HEIGHT = "height";
    public static final String USER_WEIGHT = "weight";
    public static final String USER_MARRIED = "married";
}
