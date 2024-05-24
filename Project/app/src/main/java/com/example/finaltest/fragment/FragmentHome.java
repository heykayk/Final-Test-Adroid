package com.example.finaltest.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finaltest.DetailFoodActivity;
import com.example.finaltest.R;
import com.example.finaltest.UpdateMeal;
import com.example.finaltest.adapter.RecycleViewAdapter;
import com.example.finaltest.adapter.RecycleViewAdapterHome;
import com.example.finaltest.dal.Database;
import com.example.finaltest.model.CircularProgressBar;
import com.example.finaltest.model.CustomProgressBar;
import com.example.finaltest.model.Food;
import com.example.finaltest.model.FoodDaily;
import com.example.finaltest.model.Target;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FragmentHome extends Fragment implements RecycleViewAdapterHome.ItemHomeListener {
    private ImageView imPrevious, imNext;
    private TextView tvCurrentDate, tvDate, tvProtein, tvCarbs, tvFat, tvUsed, tvNeed, tvTotalMeal;
    private CircularProgressBar progressBar;
    private CustomProgressBar customProgressBar, customProgressBarCarb,customProgressBarFat;
    private List<FoodDaily> list = new ArrayList<FoodDaily>();
    private Target target;
    private Spinner spinnerHome;
    private RecyclerView recycleViewHome;
    private RecycleViewAdapterHome adapterHome;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
        getData();
        draw(this.list);
        listener();
    }



    private void initView(View view){
        imPrevious = view.findViewById(R.id.ivPrevious);
        imNext = view.findViewById(R.id.ivNext);
        tvCurrentDate = view.findViewById(R.id.tvCurrentDate);
        tvDate = view.findViewById(R.id.tvDate);
        tvProtein = view.findViewById(R.id.tvProtein);
        tvCarbs = view.findViewById(R.id.tvCarbs);
        tvFat = view.findViewById(R.id.tvFat);
        tvUsed = view.findViewById(R.id.tvUsed);
        tvNeed = view.findViewById(R.id.tvNeed);
        tvTotalMeal = view.findViewById(R.id.tvTotalMeal);
        spinnerHome = view.findViewById(R.id.spinnerHome);
        recycleViewHome = view.findViewById(R.id.recycleViewHome);
        progressBar = view.findViewById(R.id.circularProgressBar);
        customProgressBar = view.findViewById(R.id.progressBarProtein);
        customProgressBarCarb = view.findViewById(R.id.progressBarCarb);
        customProgressBarFat = view.findViewById(R.id.progressBarFat);

        spinnerHome.setAdapter(new ArrayAdapter<String>(getContext(),R.layout.item_spinner,
                getResources().getStringArray(R.array.meal)));

        adapterHome = new RecycleViewAdapterHome();

        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recycleViewHome.setLayoutManager(manager);
        recycleViewHome.setAdapter(adapterHome);

        adapterHome.setItemListener(this);
        setTvCurrentDate();
    }

    private void listener(){
        spinnerHome.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Xử lý sự kiện khi một mục được chọn từ Spinner
                Database db = new Database(getContext());
                String s = parentView.getSelectedItem().toString();
                setRecycleView(s);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Xử lý sự kiện khi không có mục nào được chọn từ Spinner
            }
        });


        imNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               updateDate(1);
            }
        });

        imPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDate(-1);
            }
        });
    }

    private void updateDate(int a){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try{
            SharedPreferences sharedPreferences = requireContext().getSharedPreferences("date", Context.MODE_PRIVATE);
            String pickDate = sharedPreferences.getString("pickDate", "");
            Date date = dateFormat.parse(pickDate);

            // Tạo đối tượng Calendar và đặt ngày cho nó
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            // Thêm 1 ngày
            calendar.add(Calendar.DAY_OF_MONTH, a);
            Date increasedDate = calendar.getTime();
            String newDate = dateFormat.format(increasedDate);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("pickDate", newDate);
            editor.apply();


            getData();
            draw(list);
            setTvCurrentDate();
        }catch (Exception e){

        }
    }

    private void setTvCurrentDate() {
        try {
            SharedPreferences sharedPreferences = requireContext().getSharedPreferences("date", Context.MODE_PRIVATE);
            String date = sharedPreferences.getString("pickDate", "");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

            Date pickDate = simpleDateFormat.parse(date.trim());
            Date currDate = new Date();

            Calendar c1 = Calendar.getInstance();
            Calendar c2 = Calendar.getInstance();

            c1.setTime(pickDate);
            c2.setTime(currDate);

            long noDay = (c1.getTime().getTime() - c2.getTime().getTime()) / (24 * 3600 * 1000);

            String result = "";
            if(noDay < 0){
                if(Math.abs(noDay) == 1){
                    result = "Hôm qua";
                } else {
                    result = Math.abs(noDay) + " ngày trước";
                }
            } else if (noDay == 0){
                if(c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH)){
                    result = "Hôm nay";
                } else {
                    result = "Ngày mai";
                }
            }else if(noDay > 0){
                result = "Thời gian tới";
            }
            tvCurrentDate.setText(result);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private void getData(){
        Database db = new Database(getContext());
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences(
                "user", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("userId", 0);
        target = db.existsTarget(userId);

        sharedPreferences = requireContext().getSharedPreferences("date", Context.MODE_PRIVATE);
        String pickDate = sharedPreferences.getString("pickDate", "");
        list = getAllFoodDaiLy(pickDate, userId);

        tvUsed.setText(caculateTotalKcal(list) + " KCal\n Đã nạp");
        tvNeed.setText(caculateBMI(target) + " Kcal\n Cần nạp");
        tvDate.setText(pickDate);

        setRecycleView("all");
    }

    private void setRecycleView(String s){
        if(s.equalsIgnoreCase("all")){
            adapterHome.setList(list);
            tvTotalMeal.setText("Tổng: " +  caculateTotalKcal(list) + "KCal");
        } else{
            List<FoodDaily> foodDailies = getByMeal(s);
            adapterHome.setList(foodDailies);
            tvTotalMeal.setText("Tổng: " +  caculateTotalKcal(foodDailies) + "KCal");
        }
    }

    private List<FoodDaily> getByMeal(String s){
        List<FoodDaily> foodDailies = new ArrayList<>();
        for(FoodDaily f: list){
            if(f.getMeal().equalsIgnoreCase(s)){
                foodDailies.add(f);
            }
        }
        return foodDailies;
    }


    private List<FoodDaily> getAllFoodDaiLy(String date, int userID){
        Database db = new Database(getContext());
        List<FoodDaily> foodDailies =  db.getAllFoodDailyByDate(date, userID);
        Collections.sort(foodDailies, (p1, p2) -> p1.getMeal().compareTo(p2.getMeal()));
        return foodDailies;
    }

    private int caculateTotalKcal(List<FoodDaily> foodDailies){
        double totalKcal = 0;
        for (FoodDaily i:foodDailies){
            totalKcal += i.getTotalKCal();
        }
        return (int)totalKcal;
    }

    private void draw(List<FoodDaily> list){
        List<Double> listEnergies = caculateEnergy(list);
        int totalEnergy = caculateBMI(this.target);

        int totalFat = totalEnergy * 3 / (10 * 9);
        int totalProtein = (totalEnergy - totalFat * 9)/ ( 2 * 4);
        int totalCarbs = (totalEnergy - totalFat * 9)/ ( 2 * 4);
        tvProtein.setText(listEnergies.get(0)+ " / " + totalProtein);
        tvCarbs.setText(listEnergies.get(1)+ " / " + totalCarbs);
        tvFat.setText(listEnergies.get(2)+ " / " + totalFat);


        progressBar.setPercentage((float) (caculateTotalKcal(list) * 100 / caculateBMI(target)));
        customProgressBar.setEnergyLevel((int) (listEnergies.get(0) / totalProtein * 100));
        customProgressBarCarb.setEnergyLevel((int) (listEnergies.get(1) / totalCarbs * 100));
        customProgressBarFat.setEnergyLevel((int)(listEnergies.get(2) / totalFat * 100));
    }

    private int caculateBMI(Target target){
        double bmi = 0d;

        if(target.getSex() == 1){
            bmi = 66 + 13.7 * target.getWeight() + 5 * target.getHeight() - 6.8 * target.getAge();
        } else {
            bmi = 655 + 9.6 * target.getWeight() + 1.8 * target.getHeight() - 4.7 * target.getAge();
        }
        bmi = bmi * target.getR();
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

    @Override
    public void onItemClick(View view, int postion) {
        FoodDaily foodDaily = adapterHome.getItem(postion);
        Intent intent = new Intent(getActivity(), UpdateMeal.class);
        intent.putExtra("foodDaily", foodDaily);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("date", Context.MODE_PRIVATE);
        String pickDate = sharedPreferences.getString("pickDate", "");

        sharedPreferences = requireContext().getSharedPreferences(
                "user", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("userId", 0);
        list = getAllFoodDaiLy(pickDate, userId);
        String s = spinnerHome.getSelectedItem().toString();
        setRecycleView(s);
        getData();
        draw(list);
        super.onResume();
    }
}
