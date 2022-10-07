package com.example.trafficviolation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.trafficviolation.admin.AdminMainActivity;
import com.example.trafficviolation.user.RegisterActivity;
import com.example.trafficviolation.user.UserMainActivity;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity {

    public Button loginBtn, signupBtn;
    public EditText username, password;
    public TextView responseMsg;
    public Intent intent;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String user = prefs.getString("user", "");

        if(user.equals("admin")){
            intent = new Intent(LoginActivity.this, AdminMainActivity.class);
            startActivity(intent);
            finish();
        }
        else if (user.equals("user")) {
            intent = new Intent(LoginActivity.this, UserMainActivity.class);
            startActivity(intent);
            finish();
        }

        loginBtn = findViewById(R.id.loginBtn);
        signupBtn = findViewById(R.id.signupBtn);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        responseMsg = findViewById(R.id.responseMsg);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginRequest("http://192.168.1.103:8000/api/login");
            }
        });

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    public void loginRequest(String url)  {
        AndroidNetworking.post(url)
                .addBodyParameter("username", username.getText().toString())
                .addBodyParameter("password", password.getText().toString())
                .addHeaders("Accept", "application/json")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.getBoolean("success") == true){
                                if(response.getString("role").equals("admin")){
                                    prefs.edit().putString("user", "admin").commit();
                                    prefs.edit().putString("token", response.getString("token")).commit();
                                    intent = new Intent(LoginActivity.this, AdminMainActivity.class);
                                    startActivity(intent);
                                }
                                else if(response.getString("role").equals("user")){
                                    prefs.edit().putString("user", "user").commit();
                                    prefs.edit().putString("token", response.getString("token")).commit();
                                    intent = new Intent(LoginActivity.this, UserMainActivity.class);
                                    startActivity(intent);

                                }
                            }
                            else{
                                responseMsg.setText(response.getString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        responseMsg.setText("Invalid username or password");
                    }
                });
    }
}