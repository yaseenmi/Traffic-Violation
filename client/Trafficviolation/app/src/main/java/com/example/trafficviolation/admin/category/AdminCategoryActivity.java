package com.example.trafficviolation.admin.category;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.trafficviolation.R;
import com.example.trafficviolation.admin.car.AdminCarAdapter;
import com.example.trafficviolation.admin.car.AdminCarItem;
import com.example.trafficviolation.admin.car.AdminCarsActivity;
import com.example.trafficviolation.user.UserMainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AdminCategoryActivity extends AppCompatActivity {
    private RecyclerView categoryRecycleView;
    private List<AdminCategoryItem> mList;
    private SharedPreferences prefs;
    private Button addCategory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);
        addCategory = findViewById(R.id.add_category_button);
        categoryRecycleView = findViewById(R.id.categoryRecycleView);

        categoryRecycleView.setHasFixedSize(true);
        categoryRecycleView.setLayoutManager(new LinearLayoutManager(this));
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        mList = new ArrayList<>();
        
        getCategory();
        addCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminEditAddCategory.class);
                intent.putExtra("button","add");
                startActivity(intent);
            }
        });
    }
    public void getCategory(){
        String token = prefs.getString("token", "");
        AndroidNetworking.get("http://192.168.1.103:8000/api/category")
                .addHeaders("Authorization","Bearer " + token)
                .addHeaders("Accept","application/json")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if(response.length() ==0){
                            new AlertDialog.Builder(AdminCategoryActivity.this)
                                    .setTitle("Oops !")
                                    .setMessage("You don't have any category ")
                                    .setNegativeButton(android.R.string.ok, null)
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();
                        }
                        for(int i=0;i<response.length();i++){
                            try {
                                String name = response.getJSONObject(i).getString("name");
                                 int id = response.getJSONObject(i).getInt("id");
                                 int price = response.getJSONObject(i).getInt("price");

                                AdminCategoryItem item =new AdminCategoryItem(name,price,id,id);
                                mList.add(item);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        AdminCategoryAdapter adapter = new AdminCategoryAdapter(AdminCategoryActivity.this,mList);
                        categoryRecycleView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(AdminCategoryActivity.this, anError.getErrorBody(), Toast.LENGTH_SHORT).show();

                    }
                });
    }
    public void clickEditButton(View view){
        //Toast.makeText(AdminCategoryActivity.this, view.getTag().toString(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(AdminCategoryActivity.this,AdminEditAddCategory.class);
        intent.putExtra("categoryId",view.getTag().toString());
        intent.putExtra("button","update");
        startActivity(intent);
    }



}
