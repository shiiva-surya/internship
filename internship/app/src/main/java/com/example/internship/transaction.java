package com.example.internship;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.internship.R;

import com.example.internship.data.usercontract.UserEntry;
import com.example.internship.CatalogActivity;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;
import static com.example.internship.CatalogActivity.g;
import static com.example.internship.CatalogActivity.qu;

public class transaction extends AppCompatActivity  {


   public static  int k;
   String j=new String();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaction);
        Button but= (Button) findViewById(R.id.button3);


        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText gh=(EditText) findViewById(R.id.amount);
                j=gh.getText().toString();
                 k=Integer.parseInt(j);
                 Log.v("transaction",String.valueOf(k));
                 int o=Integer.parseInt(qu);
                if (k>o)
                {
                    print();
                }
                else {
                    Intent intent = new Intent(transaction.this, transfering.class);
                    startActivity(intent);
                }

            }
        });

            }

    private void print() {
        Toast.makeText(this,R.string.amount_high, LENGTH_SHORT).show();
    }


}
