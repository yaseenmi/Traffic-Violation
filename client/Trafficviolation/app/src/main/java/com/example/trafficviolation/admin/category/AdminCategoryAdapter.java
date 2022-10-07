package com.example.trafficviolation.admin.category;

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

public class AdminCategoryAdapter extends RecyclerView.Adapter<AdminCategoryAdapter.AdminCategoryHolder>{
    Context context;
    List<AdminCategoryItem> categoryList;

    public AdminCategoryAdapter(Context context, List<AdminCategoryItem> categoryList){
        this.context = context;
        this.categoryList = categoryList;
    }
    @NonNull
    @Override
    public AdminCategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(context).inflate(R.layout.activity_single_category_item, parent,false);
        return new AdminCategoryHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminCategoryHolder holder, int position) {
        AdminCategoryItem item = categoryList.get(position);
        holder.setCategoryId(item.getCategoryId());
        holder.setCategoryName(item.getCategoryName());
        holder.setCategoryPrice(item.getCategoryPrice());
        holder.setEditCategory(item.getCategoryEdit());
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class AdminCategoryHolder extends RecyclerView.ViewHolder {
        TextView mid, mname, mprice;
        View view;
        Button medit;
        public AdminCategoryHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
        }

        public void setCategoryId(int id) {
            mid = view.findViewById(R.id.categoryId);
            mid.setText("ID : "+id);
        }
        public void setEditCategory(int edit) {
            medit = view.findViewById(R.id.edit);
            medit.setTag(edit);
        }

        public void setCategoryName(String name) {
            mname = view.findViewById(R.id.categoryName);
            mname.setText("Name : "+name);
        }

        public void setCategoryPrice(int price) {
            mprice = view.findViewById(R.id.categoryPrice);
            mprice.setText("Price : "+price);
        }

    }
}
