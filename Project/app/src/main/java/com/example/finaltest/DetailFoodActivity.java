package com.example.finaltest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.finaltest.model.Food;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;
import java.util.List;

public class DetailFoodActivity extends AppCompatActivity {
    private EditText edWeight;
    private TextView tvAddmeal, tvDate, tvName;
    private Spinner spMeal;
    private PieChart pieChart;
    private Food food;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_food);

        initView();
    }

    private void initView(){
        Intent intent=getIntent();
        food = (Food)intent.getSerializableExtra("food");

        tvName = findViewById(R.id.tvName);
        edWeight = findViewById(R.id.edWeight);
        spMeal = findViewById(R.id.spMeal);
        tvAddmeal = findViewById(R.id.tvAddmeal);
        tvDate = findViewById(R.id.tvDate);
        pieChart = findViewById(R.id.pieChart);

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
        setupPieChart();
        loadPieChartData();
    }

    private void setupPieChart() {
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(12f);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setCenterText((int)food.getCalories() + " KCal");
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

    private void loadPieChartData() {
        ArrayList<PieEntry> entries = new ArrayList<>();
        // Tính lại phần trăm của từng phần
        float total = (float)(food.getCarbs() + food.getProtein() + food.getFat());
        entries.add(new PieEntry(((float) food.getCarbs() / total) * 100f, (Object) null));
        entries.add(new PieEntry(((float) food.getProtein() / total) * 100f, (Object) null));
        entries.add(new PieEntry(((float) food.getFat() / total) * 100f, (Object) null));

        // Tạo một mảng riêng lưu trữ các nhãn để sử dụng cho chú thích
        String[] labels = {"USA", "China", "UK"};

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
        setLegendLabels();
    }

    // Phương thức để thiết lập chú thích
    private void setLegendLabels() {
        String[] labels = {"Carbs" + " (" + food.getCarbs()+ "g)"
                , "Protein"  + " (" + food.getProtein()+ "g)"
                , "Fat"  + " (" + food.getFat()+ "g)"} ;
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
}