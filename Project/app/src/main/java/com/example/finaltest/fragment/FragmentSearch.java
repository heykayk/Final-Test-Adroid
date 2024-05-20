package com.example.finaltest.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finaltest.DetailFoodActivity;
import com.example.finaltest.R;
import com.example.finaltest.adapter.RecycleViewAdapter;
import com.example.finaltest.dal.Database;
import com.example.finaltest.model.Food;

import java.util.List;

public class FragmentSearch extends Fragment implements RecycleViewAdapter.ItemListener,
        View.OnClickListener,
        SearchView.OnQueryTextListener{
    private SearchView searchView;
    private RecyclerView recyclerView;

    private RecycleViewAdapter adapter;
    private Database db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchView = view.findViewById(R.id.search);
        recyclerView = view.findViewById(R.id.recycleViewSearch);

        adapter = new RecycleViewAdapter();
        db = new Database(getContext());
        List<Food> list = db.getAllFoods();

        adapter.setList(list);

        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        adapter.setItemListener(this);
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        adapter.setList(db.searchByName(newText));
        return false;
    }

    @Override
    public void onItemClick(View view, int postion) {
        String date = "";

        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentHome homeFragment = (FragmentHome) fragmentManager.findFragmentByTag("fragment_home");
        if (homeFragment != null) {
            date = homeFragment.getTvDate().getText().toString();
        }

        Food food = adapter.getItem(postion);
        Intent intent = new Intent(getActivity(), DetailFoodActivity.class);
        intent.putExtra("food", food);
        startActivity(intent);

    }

    @Override
    public void onClick(View view) {

    }
}
