package com.example.trafficviolation.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.trafficviolation.LoginActivity;
import com.example.trafficviolation.R;
import com.example.trafficviolation.admin.car.AdminCarsActivity;
import com.example.trafficviolation.admin.category.AdminCategoryActivity;

public class AdminMainActivity extends AppCompatActivity {

    public LinearLayout violationLayout, carLayout, categoryLayout;
    public Button logoutBtn;
    private SharedPreferences prefs;
    public Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        logoutBtn = findViewById(R.id.logoutBtn);
        violationLayout = findViewById(R.id.violationLayout);
        carLayout = findViewById(R.id.carLayout);
        categoryLayout = findViewById(R.id.categoryLayout);

        violationLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(AdminMainActivity.this, AdminViolationActivity.class);
                startActivity(intent);
            }
        });

        carLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(AdminMainActivity.this, AdminCarsActivity.class);
                startActivity(intent);
            }
        });

        categoryLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(AdminMainActivity.this, AdminCategoryActivity.class);
                startActivity(intent);
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prefs.edit().putString("user", "none").commit();
                intent = new Intent(AdminMainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}