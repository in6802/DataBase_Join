package com.cji.edu.database_join;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener{

    EditText editTextCountry, editTextCity;
    Button buttonSearch, buttonAdd, buttonInsert, buttonRead, buttonUpdate, buttonDelete;
    TextView textViewResult;

    MyDBOpenHelper dbHelper;
    SQLiteDatabase mdb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        System.out.println("*************   Main onCreate()");
        dbHelper = new MyDBOpenHelper(this, "awe.db", null, 2);
        System.out.println("*************   Main onCreate()");
        mdb = dbHelper.getWritableDatabase();

        editTextCountry = findViewById(R.id.editTextCountry);
        editTextCity = findViewById(R.id.editTextCity);

        buttonSearch = findViewById(R.id.buttonSearch);
        buttonSearch.setOnClickListener(this);

        buttonAdd = findViewById(R.id.buttonAddVisited);
        buttonAdd.setOnClickListener(this);

        buttonInsert = findViewById(R.id.buttonInsertRecord);
        buttonInsert.setOnClickListener(this);

        buttonRead = findViewById(R.id.buttonReadData);
        buttonRead.setOnClickListener(this);

        buttonUpdate = findViewById(R.id.buttonUpdateRecord);
        buttonUpdate.setOnClickListener(this);

        buttonDelete = findViewById(R.id.buttonDeleteRecord);
        buttonDelete.setOnClickListener(this);

        textViewResult = findViewById(R.id.textViewResult);

        editTextCountry.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if((event.getAction()==KeyEvent.ACTION_DOWN) && (keyCode==KeyEvent.KEYCODE_ENTER)) {

                    InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                    return true;
                }
                return false;
            }
        });
        editTextCountry.setOnKeyListener(this);
        editTextCity.setOnKeyListener(this);
    }

    @Override
    public void onClick(View v) {

        InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

        switch(v.getId()) {
            case R.id.buttonSearch:
                SearchDB();
                break;
            case R.id.buttonAddVisited:
                AddVisited();
                break;
            case R.id.buttonInsertRecord:
                InsertDB();
                break;
            case R.id.buttonReadData:
                readDB();
                break;
            case R.id.buttonUpdateRecord:
                UpdateDB();
                break;
            case R.id.buttonDeleteRecord:
                DeleteDB();
                break;
        }
    }

    public void SearchDB(){
//        country  = editTextCountry.getText().toString();
//        query = "SELECT pkid, country, capital, count(fkid) visitedTotal " +
//                "FROM awe_country INNER JOIN awe_country_visitedcount" +
//                "ON pkid"

    }

    public void AddVisited(){

    }

    public void InsertDB(){
        String str = "INSERT INTO awe_country VALUES( null, '" + editTextCountry.getText().toString() + "', '" + editTextCity.getText().toString() + " ');";
        mdb.execSQL(str);
    }

    public void readDB() {
        String query = "SELECT * FROM awe_country ORDER BY _id DESC";
        Cursor cursor = mdb.rawQuery(query, null);
        String str = "";

        while(cursor.moveToNext()) {
            int id;

            id = cursor.getInt(0);
            String country = cursor.getString(cursor.getColumnIndex("country"));
            String city = cursor.getString(2);
            str += (id + ":" + country + "-" + city + "\n");
        }
        if(str.length()>0) {
            textViewResult.setText(str);
        }
        else{
            Toast.makeText(getApplicationContext(), "Warning: Empty DB", Toast.LENGTH_SHORT).show();
            textViewResult.setText("");
        }
    }

    public void UpdateDB(){
        String query = "UPDATE awe_country SET capital='"+editTextCity.getText().toString()+"' WHERE country='" + editTextCountry.getText().toString() +"'";
        mdb.execSQL(query);
    }

    public void DeleteDB(){
        String query = "DELETE FROM awe_country WHERE country='"+editTextCountry.getText().toString()+"'";
        mdb.execSQL(query);
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if((event.getAction()==KeyEvent.ACTION_DOWN) && (keyCode==KeyEvent.KEYCODE_ENTER)) {

            InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

            return true;
        }
        return false;
    }
}