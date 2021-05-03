package com.example.superfit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import Authorization.AuthorizationFirst;

public class MainActivity extends AppCompatActivity {
    TextView sign_out;
    TextView see_all,resipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sign_out = findViewById(R.id.sign_out);
        sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AuthorizationFirst.class);
                startActivity(intent);
            }
        });
        see_all= findViewById(R.id.see_all_btn);

        resipes=findViewById(R.id.recipes);
        resipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),RecipesList.class);
                startActivity(intent);
            }
        });

    }
}