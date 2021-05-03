package Authorization;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.superfit.MainActivity;
import com.example.superfit.R;

import Other.DBHelper;

public class AuthorizationSecond extends AppCompatActivity implements View.OnClickListener {
    ImageView back;
    TextView name_tv;
    String passwordString = "";

    DBHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization_second);

        helper = new DBHelper(AuthorizationSecond.this);

        name_tv = findViewById(R.id.user_name_tv);
        Bundle args = getIntent().getExtras();
        if (args != null) {
            name_tv.setText(args.getString("name"));
        }

        back=findViewById(R.id.arrow_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AuthorizationSecond.this, AuthorizationFirst.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.num_1:
                passwordString += "1";
                checkPassword();
                break;
            case R.id.num_2:
                passwordString += "2";
                checkPassword();
                break;
            case R.id.num_3:
                passwordString += "3";
                checkPassword();
                break;
            case R.id.num_4:
                passwordString += "4";
                checkPassword();
                break;
            case R.id.num_5:
                passwordString += "5";
                checkPassword();
                break;
            case R.id.num_6:
                passwordString += "6";
                checkPassword();
            case R.id.num_7:
                passwordString += "7";
                checkPassword();
                break;
            case R.id.num_8:
                passwordString += "8";
                checkPassword();
                break;
            case R.id.num_9:
                passwordString += "9";
                checkPassword();
                break;
        }
    }
    private void checkPassword() {
        if (passwordString.length() == 4) {
            SQLiteDatabase db = helper.getReadableDatabase();
            Log.d("PASSWORD", passwordString);

            Cursor c = db.query("users", null, null, null, null, null, null);
            boolean isFound = false;
            if (c.moveToFirst()) {
                do {
                    String name = c.getString(c.getColumnIndex("name"));
                    if (name.equals(name_tv.getText().toString())) {
                        isFound = true;
                        int password = c.getInt(c.getColumnIndex("password"));
                        if (passwordString.equals(String.valueOf(password))) {
                            Intent intent = new Intent(AuthorizationSecond.this, MainActivity.class);
                            startActivity(intent);
                            db.close();
                        } else {
                            passwordString = "";
                            Toast.makeText(getApplicationContext(), "Password is not correct. Password cleared", Toast.LENGTH_SHORT).show();
                        }
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
}