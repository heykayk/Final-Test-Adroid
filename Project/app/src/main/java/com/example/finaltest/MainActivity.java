package com.example.finaltest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.finaltest.adapter.ViewPagerAdapter;
import com.example.finaltest.dal.Database;
import com.example.finaltest.model.Food;
import com.example.finaltest.model.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView navigationView;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        initView();
        insertFood();
    }

    private void initView(){
        navigationView = findViewById(R.id.bottomNav);
        viewPager = findViewById(R.id.viewPager);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        navigationView.getMenu().findItem(R.id.mHome).setChecked(true);
                        break;
                    case 1:
                        navigationView.getMenu().findItem(R.id.mSearch).setChecked(true);
                        break;
                    case 2:
                        navigationView.getMenu().findItem(R.id.mInfo).setChecked(true);
                        break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemSelected = item.getItemId();
                if(itemSelected == R.id.mHome){
                    viewPager.setCurrentItem(0);
                }
                else if(itemSelected == R.id.mSearch){
                    viewPager.setCurrentItem(1);
                }
                else if(itemSelected == R.id.mInfo){
                    viewPager.setCurrentItem(2);
                }
                return true;
            }
        });
    }

    private void insertFood(){
        Database db = new Database(this);
//        db.createFood(createFood("Trứng gà/Hột gà", 166,
//                15, 0.5, 11.6));
//        db.createFood(createFood("Quả trứng gà (Lekima)", 106,
//                4.3, 21.3, 0.4));
//        db.createFood(createFood("Thịt ức bò(nạc và mỡ)", 251,
//                18.5, 0, 7.5));
//        db.createFood(createFood("Thịt ức bò(phần nạc)", 155,
//                21, 0, 2.5));
//        db.createFood(createFood("Ức gà chiên xù", 250,
//                20, 5, 15));
//        db.createFood(createFood("Ức gà áp chảo", 220,
//                20, 2, 15));
//        db.createFood(createFood("Súp lơ xanh", 26,
//                3, 3, 0.3));
//        db.createFood(createFood("Sữa đậu nành", 131,
//                8, 14.6, 4.4));
//        db.createFood(createFood("Yến mạch", 389,
//                17, 66, 7));
//
//        db.createFood(createFood("Gạo lứt/ Gạo nâu", 106, 4.3, 21.3, 0.4));
//        db.createFood(createFood("Cá hồi", 206, 22.1, 0.0, 13.4));
//        db.createFood(createFood("Bông cải trắng", 55, 3.7, 11.2, 0.6));
//        db.createFood(createFood("Táo", 52, 0.3, 14.0, 0.2));
//        db.createFood(createFood("Chuối", 89, 1.1, 23.0, 0.3));
//        db.createFood(createFood("Sữa chua Hy Lạp", 59, 10.0, 3.6, 0.4));
//        db.createFood(createFood("Hạnh nhân", 579, 21.2, 21.6, 49.9));
//        db.createFood(createFood("Bơ", 160, 2.0, 9.0, 15.0));
//        db.createFood(createFood("Khoai lang", 86, 1.6, 20.1, 0.1));
//        db.createFood(createFood("Rau chân vịt", 23, 2.9, 3.6, 0.4));
//        db.createFood(createFood("Cam", 47, 0.9, 12.0, 0.1));
//        db.createFood(createFood("Đậu phụ", 76, 8.1, 1.9, 4.8));
//        db.createFood(createFood("Quả óc chó", 654, 15.2, 13.7, 65.2));
//        db.createFood(createFood("Dâu tây", 32, 0.7, 7.7, 0.3));
//        db.createFood(createFood("Dưa chuột", 16, 0.7, 3.6, 0.1));
//        db.createFood(createFood("Cà rốt", 41, 0.9, 9.6, 0.2));
//        db.createFood(createFood("Đậu xanh", 347, 23.9, 62.0, 1.2));
//        db.createFood(createFood("Bắp ngô", 86, 3.2, 19.0, 1.2));
//        db.createFood(createFood("Quả việt quất", 57, 0.7, 14.5, 0.3));
//        db.createFood(createFood("Đậu đen", 341, 21.6, 62.4, 1.4));
//        db.createFood(createFood("Đậu nành", 446, 36.5, 30.2, 19.9));
//        db.createFood(createFood("Quả nho", 69, 0.7, 18.1, 0.2));
//        db.createFood(createFood("Quả lê", 57, 0.4, 15.0, 0.1));
//        db.createFood(createFood("Quả đào", 39, 0.9, 9.5, 0.3));
//        db.createFood(createFood("Thịt lợn nạc", 242, 27.0, 0.0, 14.0));
//        db.createFood(createFood("Quả mâm xôi", 52, 1.2, 11.9, 0.7));
//        db.createFood(createFood("Măng tây", 20, 2.2, 3.9, 0.1));
//        db.createFood(createFood("Hạt chia", 486, 16.5, 42.1, 30.7));
//        db.createFood(createFood("Nấm", 22, 3.1, 3.3, 0.3));
//        db.createFood(createFood("Cá thu", 305, 19.0, 0.0, 25.6));
//        db.createFood(createFood("Đậu lăng", 116, 9.0, 20.1, 0.4));
//        db.createFood(createFood("Dừa", 354, 3.3, 15.2, 33.5));
//        db.createFood(createFood("Quả kiwi", 61, 1.1, 14.7, 0.5));
//        db.createFood(createFood("Rau diếp cá", 17, 1.2, 3.3, 0.2));
//        db.createFood(createFood("Phô mai", 402, 25.0, 1.3, 33.3));
//        db.createFood(createFood("Cà chua", 18, 0.9, 3.9, 0.2));

    }

    private Food createFood(String name, double calories,
                            double protein, double carbs, double fat){
        return new Food(name, calories, protein, carbs, fat);
    }
}