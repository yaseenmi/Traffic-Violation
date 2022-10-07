package com.example.trafficviolation.admin.car;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trafficviolation.R;

import java.util.List;

public class AdminCarAdapter extends RecyclerView.Adapter<AdminCarAdapter.AdminCarHolder>{
    Context context;
    List<AdminCarItem> carList;

    public AdminCarAdapter(Context context, List<AdminCarItem> carList){
        this.context = context;
        this.carList = carList;
    }
    @NonNull
    @Override
    public AdminCarHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(context).inflate(R.layout.activity_single_car_item, parent,false);
        return new AdminCarHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminCarHolder holder, int position) {
    AdminCarItem item = carList.get(position);
    holder.setBrand(item.getBrand());
    holder.setCode(item.getCode());
    holder.setModel(item.getModel());
    holder.setUsername(item.getUsername());
    }

    @Override
    public int getItemCount() {
        return carList.size();
    }

    public class AdminCarHolder extends RecyclerView.ViewHolder {
        TextView mbrand, mmodel, mcode,musername;
        View view;

        public AdminCarHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
        }

        public void setBrand(String brand) {
            mbrand = view.findViewById(R.id.brand);
            mbrand.setText("Brand : "+brand);
        }

        public void setModel(String model) {
            mmodel = view.findViewById(R.id.model);
            mmodel.setText("Model : "+model);
        }

        public void setCode(String code) {
            mcode = view.findViewById(R.id.plate);
            mcode.setText("Plate : "+code);
        }

        public void setUsername(String username) {
            musername = view.findViewById(R.id.driver);
            musername.setText("Driver : "+username);
        }
    }
}
