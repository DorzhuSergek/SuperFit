package Authorization;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.superfit.R;
import com.example.superfit.RegistrationActivity;

import Other.DBHelper;

public class AuthorizationFirst extends AppCompatActivity {

    private TextView sign_in,sign_up;
    private EditText name_et;
    private DBHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization_first);

        helper = new DBHelper(AuthorizationFirst.this);

        name_et = findViewById(R.id.user_name_et);

        sign_in=findViewById(R.id.sign_in_btn);
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = helper.getReadableDatabase();
                if (!name_et.getText().toString().equals("")) {
                    Cursor c = db.query("users", null, null, null, null, null, null);
                    boolean isFound = false;
                    if (c.moveToFirst()) {
                        do {
                            String name = c.getString(c.getColumnIndex("name"));
                            if (name.equals(name_et.getText().toString())) {
                                isFound = true;
                                Intent intent = new Intent(AuthorizationFirst.this, AuthorizationSecond.class);
                                intent.putExtra("name", name_et.getText().toString());
                                startActivity(intent);
                                db.close();
                                break;
                            }
                        } while (c.moveToNext());
                    } else {
                        Toast.makeText(getApplicationContext(), "DataBase is empty", Toast.LENGTH_SHORT).show();
                    }
                    if (!isFound) {
                        Toast.makeText(getApplicationContext(), "No user", Toast.LENGTH_SHORT).show();
                    }
                    c.close();
                }
            }
        });
        sign_up=findViewById(R.id.sign_up_btn);
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AuthorizationFirst.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });
    }
}