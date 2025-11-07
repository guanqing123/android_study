package com.example.server.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.example.server.database.UserDBHelper;

public class UserInfoProvider extends ContentProvider {

    private UserDBHelper dbHelper;
    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    private static final int USERS = 1;
    private static final int USER = 2;
    static {
        // 往Uri匹配器中添加指定的数据路径
        URI_MATCHER.addURI(UserInfoContent.AUTHORITIES, "/user", USERS);
        URI_MATCHER.addURI(UserInfoContent.AUTHORITIES, "/user/#", USER);
    }

    @Override
    public boolean onCreate() {
        Log.d("gq", "UserInfoProvider onCreate");
        dbHelper = UserDBHelper.getInstance(getContext());
        return true;
    }

    // content://com.example.server.provider.UserInfoProvider/user
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.d("gq", "UserInfoProvider insert");
        if (URI_MATCHER.match(uri) == USERS) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.insert(UserDBHelper.TABLE_NAME, null, values);
        }
        return uri;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Log.d("gq", "UserInfoProvider query");
        if (URI_MATCHER.match(uri) == USERS) {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            return db.query(UserDBHelper.TABLE_NAME, projection, selection,
                    selectionArgs, null, null, sortOrder);
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;
        switch (URI_MATCHER.match(uri)) {
            // content://com.example.server.provider.UserInfoProvider/user
            // 删除多行
            case USERS:{
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                count = db.delete(UserDBHelper.TABLE_NAME, selection, selectionArgs);
                db.close();
                break;
            }
            // content://com.example.server.provider.UserInfoProvider/user/2
            // 删除单行
            case USER: {
                String id = uri.getLastPathSegment();
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                count = db.delete(UserDBHelper.TABLE_NAME, UserInfoContent._ID + "=?", new String[]{id});
                db.close();
                break;
            }
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        return count;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}