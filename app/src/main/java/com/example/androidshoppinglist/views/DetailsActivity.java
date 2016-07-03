package com.example.androidshoppinglist.views;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.androidshoppinglist.R;
import com.example.androidshoppinglist.models.Product;
import com.example.androidshoppinglist.views.adapters.ProductListAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.activity_details)
public class DetailsActivity extends AppCompatActivity {

    @ViewById(R.id.recyclerView) RecyclerView recyclerView;
    @ViewById(R.id.toolbar) Toolbar toolbar;
    @ViewById(R.id.fab) FloatingActionButton fab;

    ProductListAdapter mRecyclerAdapter;
    List<Product> productsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @AfterViews
    public void prepare() {
        setSupportActionBar(toolbar);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        productsList = new ArrayList<>();
        setupAdapter();
    }

    private void setupAdapter() {
        mRecyclerAdapter = new ProductListAdapter(productsList, this);
        recyclerView.setAdapter(mRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}
