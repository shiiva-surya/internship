package com.example.internship;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.internship.R;
import com.example.internship.data.usercontract.UserEntry;



public class EditorActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {


    private static final int EXISTING_USER_LOADER = 0;


    private Uri mCurrentUserUri;


    private EditText mNameEditText;


    private EditText memailEditText;


    private EditText mcreditEditText;
    private Spinner mGenderSpinner;


    private int mGender = UserEntry.GENDER_UNKNOWN;


    private boolean muserHasChanged = false;


    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            muserHasChanged = true;
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editor);


        Intent intent = getIntent();
        mCurrentUserUri = intent.getData();


        if (mCurrentUserUri == null) {

            setTitle(getString(R.string.editor_activity_title_new_user));


            invalidateOptionsMenu();
        } else {

            setTitle(getString(R.string.editor_activity_title_edit_user));


            getLoaderManager().initLoader(EXISTING_USER_LOADER, null, this);
        }


        mNameEditText = (EditText) findViewById(R.id.edit_user_name);
        memailEditText = (EditText) findViewById(R.id.edit_user_email);
        mcreditEditText = (EditText) findViewById(R.id.edit_user_credit);
        mGenderSpinner = (Spinner) findViewById(R.id.spinner_gender);


        mNameEditText.setOnTouchListener(mTouchListener);
        memailEditText.setOnTouchListener(mTouchListener);
        mcreditEditText.setOnTouchListener(mTouchListener);
        mGenderSpinner.setOnTouchListener(mTouchListener);

        setupSpinner();
    }


    private void setupSpinner() {
        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_gender_options, android.R.layout.simple_spinner_item);


        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);


        mGenderSpinner.setAdapter(genderSpinnerAdapter);


        mGenderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.gender_male))) {
                        mGender = UserEntry.GENDER_MALE;
                    } else if (selection.equals(getString(R.string.gender_female))) {
                        mGender = UserEntry.GENDER_FEMALE;
                    } else {
                        mGender = UserEntry.GENDER_UNKNOWN;
                    }
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mGender = UserEntry.GENDER_UNKNOWN;
            }
        });
    }


    private void savePet() {

        String nameString = mNameEditText.getText().toString().trim();
        String emailString = memailEditText.getText().toString().trim();
        String creditString = mcreditEditText.getText().toString().trim();


        if (mCurrentUserUri == null &&
                TextUtils.isEmpty(nameString) && TextUtils.isEmpty(emailString) &&
                TextUtils.isEmpty(creditString) && mGender == UserEntry.GENDER_UNKNOWN) {

            return;
        }


        ContentValues values = new ContentValues();
        values.put(UserEntry.COLUMN_USER_NAME, nameString);
        values.put(UserEntry.COLUMN_USER_EMAIL, emailString);
        values.put(UserEntry.COLUMN_USER_GENDER, mGender);

        int credit = 0;
        if (!TextUtils.isEmpty(creditString)) {
            credit = Integer.parseInt(creditString);
        }
        values.put(UserEntry.COLUMN_USER_CREDIT, credit);


        if (mCurrentUserUri == null) {
            Uri newUri = getContentResolver().insert(UserEntry.CONTENT_URI, values);


            if (newUri == null) {
                Toast.makeText(this, getString(R.string.editor_insert_user_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.editor_insert_user_successful),
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            int rowsAffected = getContentResolver().update(mCurrentUserUri, values, null, null);


            if (rowsAffected == 0) {

                Toast.makeText(this, getString(R.string.editor_update_user_failed),
                        Toast.LENGTH_SHORT).show();
            } else {

                Toast.makeText(this, getString(R.string.editor_update_user_successful),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (mCurrentUserUri == null) {
            MenuItem menuItem = menu.findItem(R.id.action_delete);
            menuItem.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                savePet();

                finish();
                return true;

            case R.id.action_delete:

                showDeleteConfirmationDialog();
                return true;

            case android.R.id.home:

                if (!muserHasChanged) {
                    NavUtils.navigateUpFromSameTask(EditorActivity.this);
                    return true;
                }


                DialogInterface.OnClickListener discardButtonClickListener =
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                NavUtils.navigateUpFromSameTask(EditorActivity.this);
                            }
                        };


                showUnsavedChangesDialog(discardButtonClickListener);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {

        if (!muserHasChanged) {
            super.onBackPressed();
            return;
        }


        DialogInterface.OnClickListener discardButtonClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        finish();
                    }
                };


        showUnsavedChangesDialog(discardButtonClickListener);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

        String[] projection = {
                UserEntry._ID,
                UserEntry.COLUMN_USER_NAME,
                UserEntry.COLUMN_USER_EMAIL,
                UserEntry.COLUMN_USER_GENDER,
                UserEntry.COLUMN_USER_CREDIT };


        return new CursorLoader(this,
                mCurrentUserUri,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        if (cursor == null || cursor.getCount() < 1) {
            return;
        }


        if (cursor.moveToFirst()) {

            int nameColumnIndex = cursor.getColumnIndex(UserEntry.COLUMN_USER_NAME);
            int breedColumnIndex = cursor.getColumnIndex(UserEntry.COLUMN_USER_EMAIL);
            int genderColumnIndex = cursor.getColumnIndex(UserEntry.COLUMN_USER_GENDER);
            int weightColumnIndex = cursor.getColumnIndex(UserEntry.COLUMN_USER_CREDIT);


            String name = cursor.getString(nameColumnIndex);
            String breed = cursor.getString(breedColumnIndex);
            int gender = cursor.getInt(genderColumnIndex);
            int weight = cursor.getInt(weightColumnIndex);


            mNameEditText.setText(name);
            memailEditText.setText(breed);
            mcreditEditText.setText(Integer.toString(weight));


            switch (gender) {
                case UserEntry.GENDER_MALE:
                    mGenderSpinner.setSelection(1);
                    break;
                case UserEntry.GENDER_FEMALE:
                    mGenderSpinner.setSelection(2);
                    break;
                default:
                    mGenderSpinner.setSelection(0);
                    break;
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        mNameEditText.setText("");
        memailEditText.setText("");
        mcreditEditText.setText("");
        mGenderSpinner.setSelection(0);
    }


    private void showUnsavedChangesDialog(
            DialogInterface.OnClickListener discardButtonClickListener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.unsaved_changes_dialog_msg);
        builder.setPositiveButton(R.string.discard, discardButtonClickListener);
        builder.setNegativeButton(R.string.keep_editing, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });


        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    private void showDeleteConfirmationDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_dialog_msg);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                deletePet();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });


        AlertDialog alertDialog = builder.create();
        alertDialog.show();
           }


    private void deletePet() {

        if (mCurrentUserUri != null) {

            int rowsDeleted = getContentResolver().delete(mCurrentUserUri, null, null);


            if (rowsDeleted == 0) {

                Toast.makeText(this, getString(R.string.editor_delete_user_failed),
                        Toast.LENGTH_SHORT).show();
            } else {

                Toast.makeText(this, getString(R.string.editor_delete_user_successful),
                        Toast.LENGTH_SHORT).show();
            }
            Intent intent=new Intent(EditorActivity.this,CatalogActivity.class);
            startActivity(intent);

        }


        finish();
    }
}