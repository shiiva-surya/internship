package com.example.internship;

import android.content.Intent;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.internship.data.usercontract.UserEntry;

import static android.os.Build.VERSION_CODES.M;
import static android.support.v4.content.ContextCompat.startActivity;

public class selection extends AppCompatActivity {
    private Uri mcurrenturi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selection);
        Intent inte=getIntent();
        mcurrenturi=inte.getData();


        Button fab1 = (Button) findViewById(R.id.button);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(selection.this, EditorActivity.class);
                intent.setData(mcurrenturi);
                startActivity(intent);
            }
        });

        Button fab2 = (Button) findViewById(R.id.button2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(selection.this, transaction.class);
                startActivity(intent3);
            }
        });

    }


}
