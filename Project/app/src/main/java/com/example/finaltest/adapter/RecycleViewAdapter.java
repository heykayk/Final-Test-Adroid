package com.example.finaltest.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finaltest.R;
import com.example.finaltest.model.Food;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.HomeViewHolder> {
    private List<Food> list;
    private ItemListener itemListener;

    public RecycleViewAdapter(){
        list = new ArrayList<>();
    }

    public void setItemListener(ItemListener itemListener){
        this.itemListener = itemListener;
    }

    public void setList(List<Food> list){
        this.list = list;
        notifyDataSetChanged();
    }

    public Food getItem(int position){
        return list.get(position);
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycleview, parent, false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        Food food = list.get(position);
        holder.txtName.setText(food.getName());
        holder.txtKcal.setText(food.getCalories() + " Kcal");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView txtName, txtKcal;

        public HomeViewHolder(@NonNull View view) {
            super(view);
            txtName = view.findViewById(R.id.txtName);
            txtKcal = view.findViewById(R.id.txtKcal);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(itemListener != null){
                itemListener.onItemClick(view, getAdapterPosition());
            }
        }
    }

    public interface ItemListener{
        void onItemClick(View view, int postion);
    }
}
