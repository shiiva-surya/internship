package com.example.internship;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;





import com.example.internship.R;
import com.example.internship.data.userDbhelper;
import com.example.internship.data.userProvider;
import com.example.internship.data.usercontract.UserEntry;

import static com.example.internship.CatalogActivity.g;
import static com.example.internship.CatalogActivity.qu;
import static com.example.internship.data.userProvider.ipg;
import static com.example.internship.data.userProvider.lpg;
import static com.example.internship.data.userProvider.upg;
import static com.example.internship.data.userProvider.upg1;
import static com.example.internship.transaction.k;

public class transfering extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int USER_LOADER = 0;
    UserCursorAdapter mCursorAdapter;
    String s=new String();
    public static String vb=new String();
    userDbhelper mDbhelper;
    ContentValues cv=new ContentValues();
    //final SQLiteDatabase db=mDbhelper.getWritableDatabase();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transfering);

        ListView list1 = (ListView) findViewById(R.id.list2);
        View emptyView = findViewById(R.id.empty_view);
        list1.setEmptyView(emptyView);

        // Setup an Adapter to create a list item for each row of pet data in the Cursor.
        // There is no pet data yet (until the loader finishes) so pass in null for the Cursor.
        mCursorAdapter = new UserCursorAdapter(this, null);
        list1.setAdapter(mCursorAdapter);

        list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // Create new intent to go to {@link EditorActivity}
              String e=lpg(position);
              Log.v("transfering",e+" transaction");
              vb=String.valueOf(id);
                int l=Integer.parseInt(qu);
               int n=Integer.parseInt(e);
                int r=l-k;
                int r2=n+k;
                String v= String.valueOf(r);
                String v1=String.valueOf(r2);
                upg(v1);
                upg1(v);
                Log.v("transfering",v1);
                Log.v("transfering",v);
                ipg();


               Intent intent=new Intent(transfering.this,CatalogActivity.class);
                startActivity(intent);




            }
        });


        getLoaderManager().initLoader(USER_LOADER, null, this);
    }



    @Override
    public android.content.Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        // Define a projection that specifies the columns from the table we care about.
        String[] projection = {
                UserEntry._ID,
                UserEntry.COLUMN_USER_NAME,
                UserEntry.COLUMN_USER_EMAIL,
        UserEntry.COLUMN_USER_CREDIT};

        // This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(this,   // Parent activity context
                UserEntry.CONTENT_URI,   // Provider content URI to query
                projection,             // Columns to include in the resulting Cursor
                UserEntry._ID+" != "+g,                   // No selection clause
                null,                   // No selection arguments
                null);                  // Default sort order
    }

    @Override
    public void onLoadFinished(android.content.Loader<Cursor> loader, Cursor data) {
        // Update {@link PetCursorAdapter} with this new cursor containing updated pet data
        mCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Callback called when the data needs to be deleted
        mCursorAdapter.swapCursor(null);
    }



}

