package com.example.trafficviolation.admin.category;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.trafficviolation.R;
import com.example.trafficviolation.user.UserMainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AdminEditAddCategory extends AppCompatActivity {
    TextView titleEditAdd, messageCategory;
    Button editAdd;
    EditText name,price;
    private SharedPreferences prefs;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category_add_edit);

        titleEditAdd = findViewById(R.id.titleEditAdd);
        editAdd = findViewById(R.id.editAdd);
        name = findViewById(R.id.editAdd_name);
        price = findViewById(R.id.editAdd_price);
        messageCategory = findViewById(R.id.messageCategory);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        Bundle extras = getIntent().getExtras();
        String button = extras.getString("button");


        if (button.equals("add")) {
            titleEditAdd.setText("Add Category");
            editAdd.setText("Add");
            String token = prefs.getString("token", "");

            editAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AndroidNetworking.post("http://192.168.1.103:8000/api/category")
                            .addHeaders("Authorization","Bearer " + token)
                            .addHeaders("Accept","application/json")
                            .addBodyParameter("name",name.getText().toString())
                            .addBodyParameter("price",price.getText().toString())
                            .setPriority(Priority.MEDIUM)
                            .build()
                            .getAsJSONObject(new JSONObjectRequestListener() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        messageCategory.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
                                        messageCategory.setText( response.getString("message"));
                                        Intent refresh = new Intent(AdminEditAddCategory.this,AdminCategoryActivity.class);
                                        startActivity(refresh);

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                @Override
                                public void onError(ANError anError) {
                                    messageCategory.setTextColor(getResources().getColor(R.color.design_default_color_error));
                                    //messageCategory.setText(anError.getErrorBody());

                                    if((name.getText().toString().matches("") || price.getText().toString().matches("")))
                                    {
                                        messageCategory.setText("Fill the field");
                                    }
                                    if((name.getText().toString().matches("") && price.getText().toString().matches(""))){
                                        messageCategory.setText("Fill the fields");
                                    }
                                }
                            });
                }
            });


        } else {
            titleEditAdd.setText("Update Category");
            editAdd.setText("Update");

            String token = prefs.getString("token", "");
            editAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AndroidNetworking.put("http://192.168.1.103:8000/api/category/{id}")
                            .addPathParameter("id",getIntent().getStringExtra("categoryId"))
                            .addHeaders("Authorization","Bearer " + token)
                            .addHeaders("Accept","application/json")
                            .addBodyParameter("name",name.getText().toString())
                            .addBodyParameter("price",price.getText().toString())
                            .setPriority(Priority.MEDIUM)
                            .build()
                            .getAsJSONObject(new JSONObjectRequestListener() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        messageCategory.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
                                        messageCategory.setText( response.getString("message"));
                                        Intent refresh = new Intent(AdminEditAddCategory.this,AdminCategoryActivity.class);
                                        startActivity(refresh);

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                @Override
                                public void onError(ANError anError) {
                                    messageCategory.setTextColor(getResources().getColor(R.color.design_default_color_error));

                                    if((name.getText().toString().matches("") || price.getText().toString().matches("")))
                                    {
                                        messageCategory.setText("Fill the field");
                                    }
                                    if((name.getText().toString().matches("") && price.getText().toString().matches(""))){
                                        messageCategory.setText("Fill the fields");
                                    }
                                    //messageCategory.setText(anError.getErrorBody());

                                }
                            });
                }
            });
        }
    }
    }

