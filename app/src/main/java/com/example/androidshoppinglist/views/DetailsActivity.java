package com.example.androidshoppinglist.views;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.androidshoppinglist.R;
import com.example.androidshoppinglist.actions.ActionCreator;
import com.example.androidshoppinglist.app.BaseApplication;
import com.example.androidshoppinglist.data.GetListActionType;
import com.example.androidshoppinglist.data.ProductBoughtEvent;
import com.example.androidshoppinglist.data.ProductsListEvent;
import com.example.androidshoppinglist.models.Product;
import com.example.androidshoppinglist.stores.DatabaseStore;
import com.example.androidshoppinglist.stores.ShoppingListStore;
import com.example.androidshoppinglist.views.adapters.ProductListAdapter;
import com.nikhilpanju.recyclerviewenhanced.RecyclerTouchListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

@EActivity(R.layout.activity_details)
public class DetailsActivity extends AppCompatActivity implements ProductDialogFragment.ProductDialogListener {

    @ViewById(R.id.recyclerView) RecyclerView recyclerView;
    @ViewById(R.id.toolbar) Toolbar toolbar;
    @ViewById(R.id.fab) FloatingActionButton fab;

    @Inject ActionCreator actionCreator;
    @Inject EventBus eventBus;
    @Inject ShoppingListStore shoppingListStore;
    @Inject DatabaseStore databaseStore;

    @Extra("listCreatedAtTime")
    long listCreatedAtTime;

    @Extra("listType")
    GetListActionType listType;

    ProductListAdapter mRecyclerAdapter;
    List<Product> productsList;
    RecyclerTouchListener onTouchListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BaseApplication) getApplication()).component().inject(this);
        productsList = new ArrayList<>();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!eventBus.isRegistered(this)) {
            eventBus.register(this);
        }
        actionCreator.createGetProductsListFromDbAction(listCreatedAtTime);
        recyclerView.addOnItemTouchListener(onTouchListener);
    }

    @Override
    public void onPause() {
        if (eventBus.isRegistered(this)) {
            eventBus.unregister(this);
        }
        recyclerView.removeOnItemTouchListener(onTouchListener);
        super.onPause();
    }

    @AfterViews
    public void prepare() {
        setSupportActionBar(toolbar);

        if (listType.equals(GetListActionType.CURRENT)) {
            fab.setVisibility(View.VISIBLE);
        } else {
            fab.setVisibility(View.GONE);
        }

        fab.setOnClickListener(view -> {
            DialogFragment dialog = new ProductDialogFragment();
            dialog.show(getSupportFragmentManager(), "ProductDialogFragment");
        });

        setupAdapter();
    }

    private void setupAdapter() {
        mRecyclerAdapter = new ProductListAdapter(productsList, this, listType);
        recyclerView.setAdapter(mRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        onTouchListener = new RecyclerTouchListener(this, recyclerView);
        onTouchListener.setViewsToFade(R.id.productDetailsView, R.id.imageViewCheckbox)
                .setSwipeOptionViews(R.id.delete)
                .setSwipeable(R.id.card, R.id.swipe, (viewID, position) -> {
                    if (viewID == R.id.delete) {
                    }
                });
    }

    @Override
    public void onFinishEditDialog(String inputText, double quantity, String unit) {
        actionCreator.createAddProductToListsAction(new Product(inputText, new Date(), quantity, listCreatedAtTime, unit));
    }

    @Subscribe
    public void onProductsListUpdate(ProductsListEvent productsListEvent) {
        productsList = productsListEvent.getProducts();
        mRecyclerAdapter.notifyData(productsList);
    }

    @Subscribe
    public void onProductBoughtUpdate(ProductBoughtEvent productBoughtEvent) {
        mRecyclerAdapter.notifyDataSetChanged();
    }
}
