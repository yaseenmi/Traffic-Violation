package com.example.trafficviolation.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.trafficviolation.LoginActivity;
import com.example.trafficviolation.R;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {
    Button register;
    EditText name, password,confirm_password,phone,car_model,car_brand,car_plate;
    TextView message,returnToLoginText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register = findViewById(R.id.register);
        name = findViewById(R.id.username);
        password = findViewById(R.id.password);
        confirm_password = findViewById(R.id.confirm_password);
        phone = findViewById(R.id.phone);
        car_model = findViewById(R.id.model);
        car_brand = findViewById(R.id.brand);
        car_plate = findViewById(R.id.car_plate);
        message = findViewById(R.id.message);
        returnToLoginText = findViewById(R.id.returnToLoginText);
        AndroidNetworking.initialize(getApplicationContext());

        returnToLoginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
            startActivity(intent);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postData();

            }
        });
    }

    public void postData(){

        AndroidNetworking.post("http://192.168.1.103:8000/api/register")
                .addBodyParameter("username",name.getText().toString())
                .addBodyParameter("password", password.getText().toString())
                .addBodyParameter("password_confirmation",confirm_password.getText().toString())
                .addBodyParameter("phone_number",phone.getText().toString())
                .addBodyParameter("model",car_model.getText().toString())
                .addBodyParameter("brand",car_brand.getText().toString())
                .addBodyParameter("code",car_plate.getText().toString())
                .setPriority(Priority.MEDIUM)
                .addHeaders("Accept","application/json")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            message.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
                            message.setText(response.getString("message"));
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        message.setTextColor(getResources().getColor(R.color.design_default_color_error));
                        message.setText("error occurred");
                    }
                });
    }
}
