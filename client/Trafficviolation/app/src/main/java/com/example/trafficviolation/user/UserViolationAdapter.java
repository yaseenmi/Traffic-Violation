package com.example.trafficviolation.user;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trafficviolation.R;

import java.util.List;

public class UserViolationAdapter extends RecyclerView.Adapter<UserViolationAdapter.UserViolationHolder>  {
    Context context;
    List<UserViolationItem> violationList;

    public UserViolationAdapter(Context context , List<UserViolationItem> violationList){
        this.context = context;
        this.violationList = violationList;
    }
    @NonNull
    @Override
    public UserViolationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(context).inflate(R.layout.activity_single_violation_item , parent , false);
        return new UserViolationHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViolationHolder holder, int position) {
        UserViolationItem item = violationList.get(position);
        holder.setmCategory(item.getCategory());
        holder.setmLocation(item.getLocation());
        holder.setmDate(item.getDate());
        holder.setmPrice(item.getPrice());
        holder.setButton(item.getButton());
    }

    @Override
    public int getItemCount() {
        return violationList.size();
    }

    public class UserViolationHolder extends RecyclerView.ViewHolder {
        TextView mCategory, mLocation, mDate, mPrice;
        View view;
        Button mbutton;

        public UserViolationHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;

        }
        public void setButton(int button){
            mbutton = view.findViewById(R.id.button);
            mbutton.setTag(button);
        }
        public void setmCategory(String category) {
            mCategory = view.findViewById(R.id.category);
            mCategory.setText("Ticket type : "+category);
            }

        public void setmLocation(String location) {
            mLocation = view.findViewById(R.id.location);
            mLocation.setText("Location : " + location);
        }

        public void setmDate(String date) {
            mDate = view.findViewById(R.id.date);
            mDate.setText("Date : " +date);
        }


        public void setmPrice(int price) {
            mPrice = view.findViewById(R.id.price);
            mPrice.setText("Price : " +price);
        }
    }
}
