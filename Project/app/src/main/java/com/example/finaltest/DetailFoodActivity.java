package com.example.finaltest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finaltest.dal.Database;
import com.example.finaltest.model.Food;
import com.example.finaltest.model.FoodDaily;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class DetailFoodActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText edWeight;
    private TextView tvAddmeal, tvDate, tvName;
    private Spinner spMeal;
    private PieChart pieChart;
    private Food food;
    private Button btnAddMeal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_food);

        initView();
        btnAddMeal.setOnClickListener(this);
        edWeight.setOnClickListener(this);
    }

    private void initView(){
        Intent intent=getIntent();
        this.food = (Food)intent.getSerializableExtra("food");

        tvName = findViewById(R.id.tvName);
        edWeight = findViewById(R.id.edWeight);
        spMeal = findViewById(R.id.spMeal);
        tvAddmeal = findViewById(R.id.tvAddmeal);
        tvDate = findViewById(R.id.tvDate);
        pieChart = findViewById(R.id.pieChart);
        btnAddMeal = findViewById(R.id.btnAddMeal);

        spMeal.setAdapter(new ArrayAdapter<String>(this,R.layout.item_spinner,
                getResources().getStringArray(R.array.meal)));
        tvName.setText(food.getName());

        spMeal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Xử lý sự kiện khi một mục được chọn từ Spinner
                tvAddmeal.setText("Món ăn sẽ được thêm vào bữa: " + spMeal.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Xử lý sự kiện khi không có mục nào được chọn từ Spinner
            }
        });
        setUpdate();
        setupPieChart();
        loadPieChartData(this.food);
        setTextChangeEditText();
    }

    private void setTextChangeEditText(){
        edWeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String weightString = charSequence.toString();
                if (weightString.isEmpty() || weightString == null){
                    loadPieChartData(food);
                    return;
                }
                int weight = 0;
                try {
                    weight = Integer.parseInt(weightString);
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), "Khối lượng vui lòng nhập số!", Toast.LENGTH_SHORT).show();
                    loadPieChartData(food);
                    return;
                }


                System.out.println(weight);

                Food newFood = new Food(food.getName(),
                        Double.parseDouble(String.valueOf(Double.parseDouble(String.format(Locale.US, "%.1f", food.getCalories() * (double) weight / 100)))),
                        Double.parseDouble(String.valueOf(Double.parseDouble(String.format(Locale.US, "%.1f", food.getProtein() * (double) weight / 100)))),
                        Double.parseDouble(String.valueOf(Double.parseDouble(String.format(Locale.US, "%.1f", food.getCarbs() * (double) weight / 100)))),
                        Double.parseDouble(String.valueOf(Double.parseDouble(String.format(Locale.US, "%.1f", food.getFat() * (double) weight / 100))))
                );
                System.out.println(newFood);
                loadPieChartData(newFood);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void setUpdate(){
        try {
            SharedPreferences sharedPreferences = getSharedPreferences("date", MODE_PRIVATE);
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
                    result = "Hôm qua - ";
                } else {
                    result = Math.abs(noDay) + " ngày trước - ";
                }
            } else if (noDay == 0){
                result = "Hôm nay - ";
            }else {
                if (noDay == 1){
                    result = "Ngày mai - ";
                } else {
                    result = "Thời gian tới - ";
                }
            }
            result = result + "Ngày " + date;
            tvDate.setText(result);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private void setupPieChart() {
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(12f);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setCenterTextSize(26f);
        pieChart.getDescription().setEnabled(false);

        // Configure legend
        Legend legend = pieChart.getLegend();
        legend.setEnabled(true);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setDrawInside(false);
        legend.setTextSize(16f); // Adjust this value as needed
        legend.setTextColor(Color.BLACK);
    }

    private void loadPieChartData(Food newFood) {
        ArrayList<PieEntry> entries = new ArrayList<>();

        pieChart.setCenterText((int)newFood.getCalories() + " KCal");
        // Tính lại phần trăm của từng phần
        float total = (float)(newFood.getCarbs() + newFood.getProtein() + newFood.getFat());
        entries.add(new PieEntry(((float) food.getCarbs() / total) * 100f, (Object) null));
        entries.add(new PieEntry(((float) food.getProtein() / total) * 100f, (Object) null));
        entries.add(new PieEntry(((float) food.getFat() / total) * 100f, (Object) null));

        // Mảng màu mới
        int[] colors = {
                ContextCompat.getColor(this, R.color.blue),
                ContextCompat.getColor(this, R.color.green),
                ContextCompat.getColor(this, R.color.pink)
        };

        PieDataSet dataSet = new PieDataSet(entries, "Sales by Region");
        dataSet.setColors(colors); // Sử dụng mảng màu mới

        PieData data = new PieData(dataSet);
        data.setDrawValues(true); // Hiển thị giá trị (phần trăm)
        data.setValueFormatter(new PercentFormatter(pieChart)); // Định dạng giá trị là phần trăm

        data.setValueTextSize(16f);
        pieChart.setData(data);
        pieChart.invalidate(); // Làm mới biểu đồ

        // Thiết lập chú thích
        setLegendLabels(newFood);
    }

    // Phương thức để thiết lập chú thích
    private void setLegendLabels(Food newFood) {
        String[] labels = {"Carbs" + " (" + newFood.getCarbs()+ "g)"
                , "Protein"  + " (" + newFood.getProtein()+ "g)"
                , "Fat"  + " (" + newFood.getFat()+ "g)"} ;
        Legend legend = pieChart.getLegend();
        if (legend != null) {
            legend.setCustom(getLegendLabels(labels));
        }
    }

    // Phương thức để tạo chuỗi HTML để sử dụng cho chú thích
    private ArrayList<LegendEntry> getLegendLabels(String[] labels) {
        ArrayList<LegendEntry> entries = new ArrayList<>();
        int[] colors = {
                ContextCompat.getColor(this, R.color.blue),
                ContextCompat.getColor(this, R.color.green),
                ContextCompat.getColor(this, R.color.pink),
        };
        for (int i = 0; i < labels.length; i++) {
            LegendEntry entry = new LegendEntry();
            entry.label = labels[i];
            entry.formColor = colors[i]; // Màu sắc của mỗi phần tương ứng
            entries.add(entry);
        }
        return entries;
    }

    @Override
    public void onClick(View view) {

        if(view  == btnAddMeal){
            String weightString = edWeight.getText().toString();
            String meal = spMeal.getSelectedItem().toString().toString();
            if (weightString.isEmpty() || weightString == null){
                Toast.makeText(this, "Vui lòng điền khối lượng", Toast.LENGTH_SHORT).show();
                return;
            }
            int weight = 0;
            try {
                weight = Integer.parseInt(weightString);
            }catch (Exception e){
                Toast.makeText(this, "Khối lượng vui lòng nhập số!", Toast.LENGTH_SHORT).show();
                return;
            }

            SharedPreferences sharedPreferences = getSharedPreferences("date", MODE_PRIVATE);
            String date = sharedPreferences.getString("pickDate", "");

            sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
            int userId = sharedPreferences.getInt("userId", 0);
            FoodDaily foodDaily = new FoodDaily(
                    userId, food,
                    weight, date,
                    meal
            );
            Database db = new Database(this);
            long tmp = db.createFoodDaily(foodDaily);
            finish();
        }
    }
}