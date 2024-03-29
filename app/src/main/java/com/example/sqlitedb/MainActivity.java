package com.example.sqlitedb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLException;

public class MainActivity extends AppCompatActivity {

    EditText etName, etPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
    }

    public void btnSubmit(View v) {

        String name = etName.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();

        //try {

            ContactsDB db = new ContactsDB(this);
            db.open();
            db.insertRow(name, phone);
            db.close();
            Toast.makeText(this,  " Data inserted successfully!", Toast.LENGTH_SHORT).show();
            etName.setText("");
            etPhone.setText("");
//        }
//        catch (SQLException e) {
//            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
//        }
    }

    public void btnShowData(View v) {

        startActivity(new Intent(this, ShowData.class));
    }

    public void btnEditData(View v) {

        //try {

            ContactsDB db = new ContactsDB(this);
            db.open();
            db.updateRow("1", "John Wick", "7854584574");
            db.close();
            Toast.makeText(this, "Row 1 updated successfully!", Toast.LENGTH_SHORT).show();
//        }
//        catch (SQLException e) {
//            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
//        }
    }

    public void btnDeleteData(View v) {

        //try {

            ContactsDB db = new ContactsDB(this);
            db.open();
            db.deleteRow("2");
            db.close();
            Toast.makeText(this, "Row 2 deleted successfully!", Toast.LENGTH_SHORT).show();
//        }
//        catch (SQLException e) {
//            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
//        }
    }
}
