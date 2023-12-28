package com.example.quickquiz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment implements OnCategoryClickListener {

    public CategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewCategories);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        CategoryAdapter adapter = new CategoryAdapter(getCategories(), this); // передаем this в качестве слушателя
        recyclerView.setAdapter(adapter);

        return view;
    }

    private List<Category> getCategories() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyApp", Context.MODE_PRIVATE);
        int literatureValue = sharedPreferences.getInt("Литература", 0);
        int muzValue = sharedPreferences.getInt("Музыка", 0);
        int gameValue = sharedPreferences.getInt("Игры", 0);
        int sportValue = sharedPreferences.getInt("Спорт", 0);
        int fValue = sharedPreferences.getInt("Политика", 0);
        int sValue = sharedPreferences.getInt("Страны", 0);
        int AValue = sharedPreferences.getInt("Аниме", 0);
        int dValue = sharedPreferences.getInt("Древняя русь", 0);
        int kValue = sharedPreferences.getInt("Космос", 0);
        int hValue = sharedPreferences.getInt("Хип-хоп", 0);
        int filmValue = sharedPreferences.getInt("Фильмы", 0);
        int gValue = sharedPreferences.getInt("Животные", 0);
        Category priv1= new Category("Литература", literatureValue);
        Category priv2= new Category("Музыка", muzValue);
        Category priv3= new Category("Игры", gameValue);
        Category priv4= new Category("Спорт", sportValue);
        Category priv5= new Category("Политика", fValue);
        Category priv6= new Category("Страны", sValue);
        Category priv7= new Category("Аниме", AValue);
        Category priv8= new Category("Древняя русь", dValue);
        Category priv9= new Category("Космос", kValue);
        Category priv10= new Category("Хип-хоп", hValue);
        Category priv11= new Category("Фильмы", filmValue);
        Category priv12= new Category("Животные", gValue);

        List<Category> categories = new ArrayList<>();
        categories.add(priv1);
        categories.add(priv2);
        categories.add(priv3);
        categories.add(priv4);
        categories.add(priv5);
        categories.add(priv6);
        categories.add(priv7);
        categories.add(priv8);
        categories.add(priv9);
        categories.add(priv10);
        categories.add(priv11);
        categories.add(priv12);

        return categories;
    }

    @Override
    public void onCategoryClick(Category category) {
        Log.d("CategoryFragment", "Нажата категория: " + category.getTitle());
        Intent intent = new Intent(getActivity(), QuizActivity.class);

        // Передаем информацию о выбранной категории в новую активность
        intent.putExtra("categoryTitle", category.getTitle());
        intent.putExtra("categoryPercentage", category.getCorrectAnswersPercentage());

        // Запускаем новую активность
        startActivity(intent);
    }
}
