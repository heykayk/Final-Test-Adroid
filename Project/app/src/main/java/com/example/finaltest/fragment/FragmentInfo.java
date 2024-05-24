package com.example.finaltest.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.finaltest.DetailFoodActivity;
import com.example.finaltest.LoginActivity;
import com.example.finaltest.R;
import com.example.finaltest.UpdateTargetActivity;
import com.example.finaltest.dal.Database;
import com.example.finaltest.model.Food;
import com.example.finaltest.model.Target;

import java.text.DecimalFormat;

public class FragmentInfo extends Fragment {
    private TextView tvName, tvBMI, tvDate, tvHeight, tvWeight, tvWeightTarger, tvR;
    private ImageView imLogout;
    private Target currentTarget;
    private Button updateTarget;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvName = view.findViewById(R.id.tvName);
        tvBMI = view.findViewById(R.id.tvBMI);
        tvDate = view.findViewById(R.id.tvDate);
        tvHeight = view.findViewById(R.id.tvHeight);
        tvWeight = view.findViewById(R.id.tvWeight);
        tvWeightTarger = view.findViewById(R.id.tvWeightTarget);
        tvR = view.findViewById(R.id.tvR);
        imLogout = view.findViewById(R.id.imLogout);
        updateTarget = view.findViewById(R.id.updateTarget);

        drawData();
        imLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();

                preferences = getActivity().getSharedPreferences("date", Context.MODE_PRIVATE);
                editor = preferences.edit();
                editor.clear();

                Intent intent = new Intent(getContext(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        updateTarget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), UpdateTargetActivity.class);
                intent.putExtra("target", currentTarget);
                startActivity(intent);
            }
        });
    }

    private void drawData(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("userId", -1);
        String fullname = sharedPreferences.getString("fullName", "");
        tvName.setText("Xin Chào, " + fullname);

        Database db = new Database(getContext());
        Target target = db.existsTarget(userId);
        this.currentTarget = target;

        double bmi = target.getWeight() / (target.getHeight() * target.getHeight()) * 10000;
        DecimalFormat decimalFormat = new DecimalFormat("#.0");
        String formattedBmi = decimalFormat.format(bmi);
        tvBMI.setText(formattedBmi + "");
        tvDate.setText(target.getDate());
        tvHeight.setText(target.getHeight()+"");
        tvWeight.setText(target.getWeight()+"");
        tvWeightTarger.setText(target.getWeightTarget() + "");
        tvR.setText("R = "  + target.getR());
    }

    @Override
    public void onResume() {
        drawData();
        super.onResume();
    }
}
