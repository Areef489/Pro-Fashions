package com.areef.asrapro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;


import com.areef.asrapro.AdapterClasses.HomePageAdapter;
import com.areef.asrapro.AdapterClasses.SubCategoryAdapter;
import com.areef.asrapro.ModelClasses.HomePageModel;


import java.util.ArrayList;
import java.util.List;


import static com.areef.asrapro.DBqueries.lists;

import static com.areef.asrapro.DBqueries.loadFragmentData;
import static com.areef.asrapro.DBqueries.loadSubCategories;
import static com.areef.asrapro.DBqueries.loadedCategoriesNames;
import static com.areef.asrapro.DBqueries.subCategoryModelList;
import static com.paytm.pgsdk.easypay.manager.PaytmAssist.getContext;

public class CategoryActivity extends AppCompatActivity {

    private RecyclerView categoryRecyclerView;
    private RecyclerView subCategoryRecyclerView;
    private SubCategoryAdapter subCategoryAdapter;
    private List<HomePageModel> homePageModelFakeList = new ArrayList<>();
    private HomePageAdapter adapter;


    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String title = getIntent().getStringExtra("CategoryName");
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        categoryRecyclerView = findViewById(R.id.category_recyclerview);
        subCategoryRecyclerView = findViewById(R.id.sub_category_recyclerview);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        subCategoryRecyclerView.setLayoutManager(layoutManager);

        LinearLayoutManager testingLayoutManager = new LinearLayoutManager(this);
        testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        categoryRecyclerView.setLayoutManager(testingLayoutManager);

        subCategoryAdapter = new SubCategoryAdapter(subCategoryModelList);
        adapter = new HomePageAdapter(homePageModelFakeList);


        int listPosition = 0;
        for (int x = 0; x < loadedCategoriesNames.size(); x++) {
            if (loadedCategoriesNames.get(x).equals(title.toUpperCase())) {
                listPosition = x;
            }
        }
        if (listPosition == 0) {
            loadedCategoriesNames.add(title.toUpperCase());
            lists.add(new ArrayList<HomePageModel>());
            loadSubCategories(subCategoryRecyclerView, getContext(), title);
            loadFragmentData(categoryRecyclerView, this, loadedCategoriesNames.size() - 1, title);
        } else {
            adapter = new HomePageAdapter(lists.get(listPosition));

        }
        categoryRecyclerView.setAdapter(adapter);
        subCategoryRecyclerView.setAdapter(subCategoryAdapter);
        subCategoryAdapter.notifyDataSetChanged();
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_icon, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.main_search_icon) {
            Intent searchIntent = new Intent(this, SearchActivity.class);
            startActivity(searchIntent);
            return true;
        } else if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
