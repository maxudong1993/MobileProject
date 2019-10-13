package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project.utils.CallBackUtil;
import com.example.project.utils.OkhttpUtil;

import okhttp3.Call;

public class LoginActivity extends AppCompatActivity {
    private EditText etEmail;
    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Get Reference to variables
        etEmail = findViewById(R.id.email);
        etPassword = findViewById(R.id.password);
    }

    // Triggers when LOGIN Button clicked
    public void checkLogin(View arg0) {
        // Get text from email and passord field
        final String email = etEmail.getText().toString();
        final String password = etPassword.getText().toString();

        String url = "http://10.0.2.2:8080/query?email="+email+"&password="+password;
        OkhttpUtil.okHttpGet(url, new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(Call call, Exception e) {
                Toast.makeText(LoginActivity.this, "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(String response) {
                if (response.equalsIgnoreCase("true")) {
                /* Here launching another activity when login successful. If you persist login state
                use sharedPreferences of Android. and logout button to clear sharedPreferences.
                 */
                    Intent intent = new Intent(LoginActivity.this, MapsActivity.class);
                    startActivity(intent);
                    LoginActivity.this.finish();
                } else if (response.equalsIgnoreCase("false")) {
                    // If username and password does not match display a error message
                    Toast.makeText(LoginActivity.this, "Invalid email or password", Toast.LENGTH_LONG).show();
                } else if (response.equalsIgnoreCase("exception") || response.equalsIgnoreCase("unsuccessful")) {
                    Toast.makeText(LoginActivity.this, "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
