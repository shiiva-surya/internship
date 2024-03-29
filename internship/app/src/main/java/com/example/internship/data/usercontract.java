package com.example.internship.data;

import android.net.Uri;
import android.content.ContentResolver;
import android.provider.BaseColumns;


public final class usercontract {


    private usercontract() {}


    public static final String CONTENT_AUTHORITY = "com.example.internship";


    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);


    public static final String PATH_USERS = "users";


    public static final class UserEntry implements BaseColumns {


        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_USERS);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_USERS;


        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_USERS;


        public final static String TABLE_NAME = "users";
        public final static String TABLE_NAME2 = "tansaction";


        public final static String _ID = BaseColumns._ID;


        public final static String COLUMN_USER_NAME ="name";


        public final static String COLUMN_USER_EMAIL = "email";


        public final static String COLUMN_USER_GENDER = "gender";


        public final static String COLUMN_USER_CREDIT = "credit";
        public  final static String COLUMN_USER_FROMUSER = "from";
        public final static String COLUMN_USER_TOUSER = "to";
        public  final static  String COLUMN_USER_CREDITAMOUNT="trasfering amount";


        public static final int GENDER_UNKNOWN = 0;
        public static final int GENDER_MALE = 1;
        public static final int GENDER_FEMALE = 2;


        public static boolean isValidGender(int gender) {
            if (gender == GENDER_UNKNOWN || gender == GENDER_MALE || gender == GENDER_FEMALE) {
                return true;
            }
            return false;
        }
    }

}