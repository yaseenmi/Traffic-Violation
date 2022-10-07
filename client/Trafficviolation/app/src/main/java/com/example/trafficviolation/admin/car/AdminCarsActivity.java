package com.example.trafficviolation.admin.car;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import com.example.trafficviolation.R;
import com.example.trafficviolation.admin.category.AdminCategoryActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AdminCarsActivity extends AppCompatActivity {
    private RecyclerView carRecycleView;
    private List<AdminCarItem> mList;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_cars);
        carRecycleView = findViewById(R.id.carRecycleView);
        carRecycleView.setHasFixedSize(true);
        carRecycleView.setLayoutManager(new LinearLayoutManager(this));
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        mList = new ArrayList<>();

        getCars();
    }

    public void getCars(){
        String token = prefs.getString("token", "");
        AndroidNetworking.get("http://192.168.1.103:8000/api/car")
                .addHeaders("Authorization","Bearer " + token)
                .addHeaders("Accept","application/json")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if(response.length() ==0){
                            new AlertDialog.Builder(AdminCarsActivity.this)
                                    .setTitle("Oops !")
                                    .setMessage("You don't have any car ")
                                    .setNegativeButton(android.R.string.ok, null)
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();
                        }
                        for(int i=0;i<response.length();i++){
                            try {
                            String brand = response.getJSONObject(i).getString("brand");
                            String model = response.getJSONObject(i).getString("model");
                            String code = response.getJSONObject(i).getString("code");
                            JSONObject driverObject = response.getJSONObject(i).getJSONObject("driver");
                            String username = driverObject.getString("username");
                                AdminCarItem item =new AdminCarItem(brand,code,model,username);
                                mList.add(item);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        AdminCarAdapter adapter = new AdminCarAdapter(AdminCarsActivity.this,mList);
                        carRecycleView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(AdminCarsActivity.this, anError.getErrorBody(), Toast.LENGTH_SHORT).show();

                    }
                });
    }
}
