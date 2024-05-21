package com.example.finaltest.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.finaltest.R;
import com.example.finaltest.model.CircularProgressBar;
import com.example.finaltest.model.CustomProgressBar;
import com.example.finaltest.model.FoodDaily;

import java.util.ArrayList;
import java.util.List;

public class FragmentHome extends Fragment {
    private ImageView imPrevious, imNext;
    private TextView tvCurrentDate, tvDate, tvProtein, tvCarbs, tvFat, tvUsed, tvNeed;
    private CircularProgressBar progressBar;
    private CustomProgressBar customProgressBar, customProgressBarCarb,customProgressBarFat;
    private List<FoodDaily> list = new ArrayList<FoodDaily>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
        draw(view);
    }

    private void setCurrentDate(String date){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("date", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("pickDate", date);
        editor.apply();
    }

    private void initView(View view){
        setCurrentDate("11/05/2024");

        imPrevious = view.findViewById(R.id.ivPrevious);
        imNext = view.findViewById(R.id.ivNext);
        tvCurrentDate = view.findViewById(R.id.tvCurrentDate);
        tvDate = view.findViewById(R.id.tvDate);
        tvProtein = view.findViewById(R.id.tvProtein);
        tvCarbs = view.findViewById(R.id.tvCarbs);
        tvFat = view.findViewById(R.id.tvFat);
        tvUsed = view.findViewById(R.id.tvUsed);
        tvNeed = view.findViewById(R.id.tvNeed);
        progressBar = view.findViewById(R.id.circularProgressBar);
        customProgressBar = view.findViewById(R.id.progressBarProtein);
        customProgressBarCarb = view.findViewById(R.id.progressBarCarb);
        customProgressBarFat = view.findViewById(R.id.progressBarFat);
    }

    private void draw(View view){
        progressBar.setPercentage(20f);  // Set 70% completion
        customProgressBar.setEnergyLevel(50);
        customProgressBarCarb.setEnergyLevel(75);
        customProgressBarFat.setEnergyLevel(60);
    }

    private List<Float> caculateEnergy(){
        List<Float> energys = new ArrayList<>();
        float protein = 0, carbs = 0, fat = 0;
        for(FoodDaily foodDaily:this.list){
//            protein += (float) foodDaily.get
        }
        energys.add(protein);
        energys.add(carbs);
        energys.add(fat);

        return energys;
    }
}
