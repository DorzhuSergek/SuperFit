package com.example.superfit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import Authorization.AuthorizationFirst;
import Other.DBHelper;

public class RegistrationActivity extends AppCompatActivity {
    TextView sign_in,sign_up;
    EditText user_name_et, email_et, re_code_et, code_et;

    private DBHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        helper = new DBHelper(RegistrationActivity.this);

        user_name_et = findViewById(R.id.user_name_et);
        email_et = findViewById(R.id.email_et);
        code_et = findViewById(R.id.code_et);
        re_code_et = findViewById(R.id.re_code_et);

        sign_in=findViewById(R.id.sign_in);
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationActivity.this, AuthorizationFirst.class);
                startActivity(intent);
            }
        });
        sign_up = findViewById(R.id.sign_up);
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = helper.getWritableDatabase();
                Log.d("Test", "Test");
                if (!user_name_et.getText().toString().equals("") &&
                !email_et.getText().toString().equals("") &&
                !code_et.getText().toString().equals("") &&
                !re_code_et.getText().toString().equals("")) {
                    ContentValues cv = new ContentValues();
                    cv.put("name", user_name_et.getText().toString());
                    cv.put("email", email_et.getText().toString());
                    if (code_et.getText().toString().equals(re_code_et.getText().toString())) {
                        cv.put("password", code_et.getText().toString());
                        db.insert("users", null, cv);
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                        db.close();
                    } else {
                        Toast.makeText(getApplicationContext(), "Codes has differences", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}