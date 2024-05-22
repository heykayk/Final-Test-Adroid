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
import com.example.finaltest.dal.Database;
import com.example.finaltest.model.CircularProgressBar;
import com.example.finaltest.model.CustomProgressBar;
import com.example.finaltest.model.FoodDaily;
import com.example.finaltest.model.Target;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FragmentHome extends Fragment {
    private ImageView imPrevious, imNext;
    private TextView tvCurrentDate, tvDate, tvProtein, tvCarbs, tvFat, tvUsed, tvNeed;
    private CircularProgressBar progressBar;
    private CustomProgressBar customProgressBar, customProgressBarCarb,customProgressBarFat;
    private List<FoodDaily> list = new ArrayList<FoodDaily>();
    private Target target;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
        draw(this.list);
    }

    private void setCurrentDate(String date){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("date", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("pickDate", date);
        editor.apply();
    }

    private void initView(View view){
        setCurrentDate("22/05/2024");

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

        Database db = new Database(getContext());
        this.list = db.getAllFoodDailyByDate("22/05/2024");
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences(
                "user", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("userId", 0);
        target = db.existsTarget(userId);
    }

    private void draw(List<FoodDaily> list){
        List<Double> listEnergies = caculateEnergy(list);
        int totalEnergy = caculateBMI(this.target);

        int totalFat = totalEnergy * 10 / (3 * 9);
        int totalProtein = (totalEnergy - totalFat * 9)/ ( 2 * 4);
        int totalCarbs = (totalEnergy - totalFat * 9)/ ( 2 * 4);

        progressBar.setPercentage(20f);
        customProgressBar.setEnergyLevel(50);
        customProgressBarCarb.setEnergyLevel(75);
        customProgressBarFat.setEnergyLevel(60);
    }

    private int caculateBMI(Target target){
        double bmi = 0d;

        if(target.getSex() == 1){
            bmi = 66 + 13.7 * target.getWeight() + 5 * target.getHeight() - 6.8 * target.getAge();
        } else {
            bmi = 655 + 9.6 * target.getWeight() + 1.8 * target.getHeight() - 4.7 * target.getAge();
        }
        return (int)bmi;
    }

    private List<Double> caculateEnergy(List<FoodDaily> foodDailies){
        List<Double> energies = new ArrayList<>();
        Double protein = 0d, carbs = 0d, fat = 0d;
        for(FoodDaily foodDaily:foodDailies){
            protein = Double.valueOf(String.valueOf(Double.parseDouble(String.format(String.format(Locale.US, "%.1f",
                    foodDaily.getFood().getProtein() * (double) foodDaily.getWeight() / 100
            )))));

            carbs = Double.valueOf(String.valueOf(Double.parseDouble(String.format(String.format(Locale.US, "%.1f",
                            foodDaily.getFood().getCarbs() * (double) foodDaily.getWeight() / 100
            )))));

            fat = Double.valueOf(String.valueOf(Double.parseDouble(String.format(String.format(Locale.US, "%.1f",
                    foodDaily.getFood().getFat() * (double) foodDaily.getWeight() / 100
            )))));
        }
        energies.add(protein);
        energies.add(carbs);
        energies.add(fat);

        return energies;
    }
}
