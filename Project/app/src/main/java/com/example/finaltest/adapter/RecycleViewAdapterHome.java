package com.example.finaltest.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finaltest.R;
import com.example.finaltest.model.FoodDaily;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewAdapterHome extends RecyclerView.Adapter<RecycleViewAdapterHome.HomeViewHolder> {
    private List<FoodDaily> list;
    private ItemHomeListener itemHomeListener;

    public RecycleViewAdapterHome(){
        this.list = new ArrayList<>();
    }

    public void setItemListener(ItemHomeListener itemListener){
        this.itemHomeListener = itemListener;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycleview_home, parent, false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        FoodDaily foodDaily = list.get(position);
        holder.txtName.setText(foodDaily.getFood().getName());
        holder.txtKcal.setText(foodDaily.getTotalKCal()+"");
        holder.txtWeight.setText(foodDaily.getWeight() + "g   - ");
        holder.txtMeal.setText(foodDaily.getMeal());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<FoodDaily> list){
        this.list = list;
        notifyDataSetChanged();
    }

    public FoodDaily getItem(int position){
        return list.get(position);
    }


    public class HomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView txtName, txtKcal, txtWeight, txtMeal;

        public HomeViewHolder(@NonNull View view) {
            super(view);
            txtName = view.findViewById(R.id.txtName);
            txtKcal = view.findViewById(R.id.txtKcal);
            txtWeight = view.findViewById(R.id.txtWeight);
            txtMeal = view.findViewById(R.id.txtMeal);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(itemHomeListener != null){
                itemHomeListener.onItemClick(view, getAdapterPosition());
            }
        }
    }

    public interface ItemHomeListener{
        void onItemClick(View view, int postion);
    }
}
