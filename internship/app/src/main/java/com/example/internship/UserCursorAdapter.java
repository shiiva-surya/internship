package com.example.internship;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.internship.R;
import com.example.internship.data.usercontract.UserEntry;

import com.example.internship.data.usercontract.UserEntry;


public class  UserCursorAdapter extends CursorAdapter {


    public UserCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Inflate a list item view using the layout specified in list_item.xml
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }


    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find individual views that we want to modify in the list item layout
        TextView idTextView = (TextView) view.findViewById(R.id.iid);
        TextView nameTextView = (TextView) view.findViewById(R.id.name);
        TextView summaryTextView = (TextView) view.findViewById(R.id.summary);
        TextView creditTextView= (TextView) view.findViewById(R.id.credit);

        // Find the columns of pet attributes that we're interested in
        int idcolumnindex = cursor.getColumnIndex(UserEntry._ID);
        int nameColumnIndex = cursor.getColumnIndex(UserEntry.COLUMN_USER_NAME);
        int creditColumnIndex = cursor.getColumnIndex(UserEntry.COLUMN_USER_EMAIL);
        int credColumnIndex = cursor.getColumnIndex(UserEntry.COLUMN_USER_CREDIT);

        // Read the pet attributes from the Cursor for the current pet
        String userid = cursor.getString(idcolumnindex);
        String userName = cursor.getString(nameColumnIndex);
        String Useremail = cursor.getString(creditColumnIndex);
        String Usercredit = cursor.getString(credColumnIndex);


        // Update the TextViews with the attributes for the current pet
        idTextView.setText(userid);
        nameTextView.setText(userName);
        summaryTextView.setText(Useremail);
        creditTextView.setText(Usercredit);
    }
}