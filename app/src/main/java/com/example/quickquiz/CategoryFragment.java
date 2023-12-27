package com.example.quickquiz;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class CategoryFragment extends Fragment {

    public CategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewCategories);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        CategoryAdapter adapter = new CategoryAdapter(getCategories());
        recyclerView.setAdapter(adapter);

        return view;
    }

    private List<Category> getCategories() {
        Category priv1= new Category("Литература", 24);
        Category priv2= new Category("Музыка", 94);
        Category priv3= new Category("Игры", 0);

        List<Category> categories = new ArrayList<>();
        categories.add(priv1);
        categories.add(priv2);
        categories.add(priv3);

        return categories;
    }
}