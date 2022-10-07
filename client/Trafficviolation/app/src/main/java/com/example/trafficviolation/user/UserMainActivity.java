package com.example.trafficviolation.user;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.trafficviolation.LoginActivity;
import com.example.trafficviolation.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserMainActivity extends AppCompatActivity
{
    private RecyclerView recyclerView;
    private List<UserViolationItem> mList;
    private SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);
        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        mList = new ArrayList<>();
        getViolation();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
               prefs.edit().putString("user","none").apply();
               Intent intent = new Intent(UserMainActivity.this, LoginActivity.class);
               startActivity(intent);
               finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        }

    private void getViolation(){
         String token = prefs.getString("token", "");
         AndroidNetworking.get("http://192.168.1.103:8000/api/violation/user")
                 .addHeaders("Authorization","Bearer " + token)
                 .addHeaders("Accept","application/json")
                 .setPriority(Priority.MEDIUM)
                 .build()
                 .getAsJSONArray(new JSONArrayRequestListener() {
                     @Override
                     public void onResponse(JSONArray response) {
                         if(response.length() ==0){
                             new AlertDialog.Builder(UserMainActivity.this)
                                     .setTitle("Congrats !")
                                     .setMessage("You don't have violation tickets ")
                                     .setNegativeButton(android.R.string.ok, null)
                                     .setIcon(android.R.drawable.checkbox_on_background)
                                     .show();
                         }
                         for(int i=0;i<response.length();i++) {
                             try {
                                 String location = response.getJSONObject(i).getString("location");
                                 String date = response.getJSONObject(i).getString("created_at");
                                 JSONObject categoryObject = response.getJSONObject(i).getJSONObject("category");
                                 String category = categoryObject.getString("name");
                                 int price = categoryObject.getInt("price");
                                 int id =response.getJSONObject(i).getInt("id");

                                 UserViolationItem item = new UserViolationItem(category, location, date, price,id);
                                 mList.add(item);


                             } catch (JSONException e) {
                                 e.printStackTrace();
                             }

                         }

                         UserViolationAdapter adapter = new UserViolationAdapter(UserMainActivity.this,mList);
                         recyclerView.setAdapter(adapter);
                         adapter.notifyDataSetChanged();
                     }

                     @Override
                     public void onError(ANError error) {
                         Toast.makeText(UserMainActivity.this, error.getErrorBody(), Toast.LENGTH_SHORT).show();
                     }
                 });
    }


    public void clickButton(View view){
        new AlertDialog.Builder(UserMainActivity.this)
                .setTitle("Pay Ticket")
                .setMessage("Are you sure you want to pay this ticket?")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                        String token = prefs.getString("token", "");
                        AndroidNetworking.delete("http://192.168.1.103:8000/api/violation/{id}")
                                .addPathParameter("id",view.getTag().toString())
                                .addHeaders("Authorization","Bearer " + token)
                                .setPriority(Priority.MEDIUM)
                                .build()
                                .getAsJSONObject(new JSONObjectRequestListener() {
                                    @Override
                                    public void onResponse(JSONObject response) {

                                        Toast.makeText(UserMainActivity.this,"ticket has been payed",Toast.LENGTH_SHORT).show();
                                        Intent refresh = new Intent(UserMainActivity.this,UserMainActivity.class);
                                        startActivity(refresh);
                                        finish();
                                    }

                                    @Override
                                    public void onError(ANError anError) {
                                        Toast.makeText(UserMainActivity.this,anError.getErrorBody(),Toast.LENGTH_SHORT).show();

                                    }
                                });
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no,null )
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();



    }
}