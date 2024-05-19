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
    }

    private Food createFood(String name, double calories,
                            double protein, double carbs, double fat){
        return new Food(name, calories, protein, carbs, fat);
    }
}