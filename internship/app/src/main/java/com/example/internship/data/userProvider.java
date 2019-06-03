package com.example.internship.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;
import android.widget.TextView;
import android.view.View;

import com.example.internship.R;
import com.example.internship.UserCursorAdapter;
import com.example.internship.data.usercontract.UserEntry;


import static com.example.internship.CatalogActivity.g;
import static com.example.internship.transaction.k;
import static com.example.internship.transfering.vb;


public class userProvider extends ContentProvider {
    private static userDbhelper mDbHelper;




    public static final String LOG_TAG = userProvider.class.getSimpleName();


    private static final int USERS = 120;


    private static final int USER_ID = 121;


    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);


    static {

        sUriMatcher.addURI(usercontract.CONTENT_AUTHORITY, usercontract.PATH_USERS, USERS);

        sUriMatcher.addURI(usercontract.CONTENT_AUTHORITY, usercontract.PATH_USERS + "/#", USER_ID);
    }



    @Override
    public boolean onCreate() {
        mDbHelper = new userDbhelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {

       SQLiteDatabase database = mDbHelper.getReadableDatabase();


        Cursor cursor;


        int match = sUriMatcher.match(uri);
        switch (match) {
            case USERS:
                cursor = database.query(UserEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case USER_ID:
                selection = UserEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };


                cursor = database.query(UserEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }


        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case USERS:
                return insertUser(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }


    private Uri insertUser(Uri uri, ContentValues values) {

        String name = values.getAsString(UserEntry.COLUMN_USER_NAME);
        if (name == null) {
            throw new IllegalArgumentException("Pet requires a name");
        }

        Integer gender = values.getAsInteger(UserEntry.COLUMN_USER_GENDER);
        if (gender == null || !UserEntry.isValidGender(gender)) {
            throw new IllegalArgumentException("Pet requires valid gender");
        }

        Integer weight = values.getAsInteger(UserEntry.COLUMN_USER_CREDIT);
        if (weight != null && weight < 0) {
            throw new IllegalArgumentException("Pet requires valid weight");
        }

        SQLiteDatabase database = mDbHelper.getWritableDatabase();


        long id = database.insert(UserEntry.TABLE_NAME, null, values);

        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }


        getContext().getContentResolver().notifyChange(uri, null);

        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection,
                      String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case USERS:
                return updateUser(uri, contentValues, selection, selectionArgs);
            case USER_ID:

                selection = UserEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return updateUser(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }


    private int updateUser(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        if (values.containsKey(UserEntry.COLUMN_USER_NAME)) {
            String name = values.getAsString(UserEntry.COLUMN_USER_NAME);
            if (name == null) {
                throw new IllegalArgumentException("Pet requires a name");
            }
        }


        if (values.containsKey(UserEntry.COLUMN_USER_GENDER)) {
            Integer gender = values.getAsInteger(UserEntry.COLUMN_USER_GENDER);
            if (gender == null || !UserEntry.isValidGender(gender)) {
                throw new IllegalArgumentException("Pet requires valid gender");
            }
        }


        if (values.containsKey(UserEntry.COLUMN_USER_CREDIT)) {

            Integer weight = values.getAsInteger(UserEntry.COLUMN_USER_CREDIT);
            if (weight != null && weight < 0) {
                throw new IllegalArgumentException("Pet requires valid weight");
            }
        }


        if (values.size() == 0) {
            return 0;
        }


        SQLiteDatabase database = mDbHelper.getWritableDatabase();


        int rowsUpdated = database.update(UserEntry.TABLE_NAME, values, selection, selectionArgs);


        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }


        return rowsUpdated;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        SQLiteDatabase database = mDbHelper.getWritableDatabase();


        int rowsDeleted;

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case USERS:

                rowsDeleted = database.delete(UserEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case USER_ID:

                selection = UserEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                rowsDeleted = database.delete(UserEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }


        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsDeleted;
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case USERS:
                return UserEntry.CONTENT_LIST_TYPE;
            case USER_ID:
                return UserEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }
    public static void upg(String r)
    {
        SQLiteDatabase database=mDbHelper.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("credit",r);
        database.update(UserEntry.TABLE_NAME,cv,"_id = "+vb,null);
    }
    public static void upg1(String r2)
    {
        SQLiteDatabase database=mDbHelper.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("credit",r2);
        database.update(UserEntry.TABLE_NAME,cv,"_id = "+g,null);
    }

    public static void ipg()
    {
        SQLiteDatabase database=mDbHelper.getWritableDatabase();
        String s="insert into "+UserEntry.TABLE_NAME2+" values ("+g+"," + vb+","+k+" );";
        database.execSQL(s);
    }
    public static String lpg(int h)
    {

        SQLiteDatabase database=mDbHelper.getReadableDatabase();
        String[] projection={UserEntry._ID,UserEntry.COLUMN_USER_CREDIT};
        Cursor c=database.query(UserEntry.TABLE_NAME,projection,null,null,null,null,null);
        c.moveToPosition(h);
        int col=c.getColumnIndex(UserEntry.COLUMN_USER_CREDIT);
        String col1;
        col1 = c.getString(col);
        Log.v("userProvider",col1);
        c.close();
        return col1;


    }

}