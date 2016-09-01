package com.example.androidshoppinglist.views;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.androidshoppinglist.R;
import com.example.androidshoppinglist.actions.ActionCreator;
import com.example.androidshoppinglist.app.BaseApplication;
import com.example.androidshoppinglist.data.ActionEvent;
import com.example.androidshoppinglist.data.GetListActionType;
import com.example.androidshoppinglist.data.ShoppingListEvent;
import com.example.androidshoppinglist.models.ShoppingListItem;
import com.example.androidshoppinglist.stores.DatabaseStore;
import com.example.androidshoppinglist.stores.ProductsListStore;
import com.example.androidshoppinglist.stores.ShoppingListStore;
import com.example.androidshoppinglist.views.adapters.ShoppingListAdapter;
import com.nikhilpanju.recyclerviewenhanced.RecyclerTouchListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@EActivity(R.layout.activity_drawer)
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        ListTitleDialogFragment.ListTitleDialogListener {

    @ViewById(R.id.toolbar) Toolbar toolbar;
    @ViewById(R.id.fab) FloatingActionButton fab;
    @ViewById(R.id.drawer_layout) DrawerLayout drawer;
    @ViewById(R.id.nav_view) NavigationView navigationView;
    @ViewById(R.id.recyclerView) RecyclerView recyclerView;
    @ViewById(R.id.image_stretch_detail) ImageView listImage;
    @ViewById(R.id.toolbar_layout) CollapsingToolbarLayout collapsingToolbarLayout;
    @ViewById(R.id.app_bar) AppBarLayout appBarLayout;

    @Inject DatabaseStore databaseStore;
    @Inject ShoppingListStore shoppingListStore;
    @Inject ProductsListStore productsListStore;
    @Inject ActionCreator actionCreator;
    @Inject EventBus eventBus;

    List<ShoppingListItem> shoppingListItems;
    ShoppingListAdapter mRecyclerAdapter;
    RecyclerTouchListener onTouchListener;
    GetListActionType listType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BaseApplication) getApplication()).component().inject(this);
        shoppingListItems = new ArrayList<>();
        listType = GetListActionType.CURRENT;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!eventBus.isRegistered(this)) {
            eventBus.register(this);
        }
        actionCreator.createGetShoppingListsFromDbAction(listType);
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
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        setupAdapter();
    }

    @Click(R.id.fab)
    public void onAddListClick() {
        DialogFragment dialog = new ListTitleDialogFragment();
        dialog.show(getSupportFragmentManager(), "ListTitleDialogFragment");
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_current) {
            actionCreator.createGetShoppingListsFromDbAction(GetListActionType.CURRENT);

        } else if (id == R.id.nav_archived) {
            actionCreator.createGetShoppingListsFromDbAction(GetListActionType.ARCHIVED);

        } else if (id == R.id.nav_settings) {

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFinishEditDialog(String inputText) {
        actionCreator.addNewShoppingListAction(inputText);
    }

    @Subscribe
    public void onNewListAdded(ShoppingListItem shoppingListItem) {
        shoppingListItems.add(shoppingListItem);
        notifyData(shoppingListItems);
    }

    @Subscribe
    public void onShoppingListEvent(ShoppingListEvent shoppingListEvent) {
        shoppingListItems = shoppingListEvent.getList();

        if (shoppingListEvent.getListType().equals(GetListActionType.CURRENT)) {
            listImage.setBackgroundResource(R.drawable.shopping);
            collapsingToolbarLayout.setTitle("Current lists");
            fab.setVisibility(View.VISIBLE);
            listType = GetListActionType.CURRENT;

        } else if (shoppingListEvent.getListType().equals(GetListActionType.ARCHIVED)) {
            listImage.setBackgroundResource(R.drawable.clock_old);
            collapsingToolbarLayout.setTitle("Archived lists");
            fab.setVisibility(View.GONE);
            listType = GetListActionType.ARCHIVED;
        }

        notifyData(shoppingListItems);
    }

    @Subscribe
    public void onActionEvent(ActionEvent actionEvent) {
        actionCreator.createGetShoppingListsFromDbAction(GetListActionType.CURRENT);
        Toast.makeText(this, actionEvent.getMessage(), Toast.LENGTH_LONG).show();
    }

    private void notifyData(List<ShoppingListItem> shoppingListItems) {
        mRecyclerAdapter.notifyData(shoppingListItems, listType);
        setupOnTouchListener();
    }

    private void setupAdapter() {
        mRecyclerAdapter = new ShoppingListAdapter(shoppingListItems, this, listType);
        recyclerView.setAdapter(mRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        onTouchListener = new RecyclerTouchListener(this, recyclerView);
    }

    public void setupOnTouchListener() {
        recyclerView.setAdapter(mRecyclerAdapter);

        if (listType.equals(GetListActionType.CURRENT)) {
            onTouchListener.setSwipeable(true);
            onTouchListener.setIndependentViews(R.id.imageViewDetails)
                    .setSwipeOptionViews(R.id.delete, R.id.archive)
                    .setViewsToFade(R.id.imageViewDetails)
                    .setSwipeable(R.id.card, R.id.swipe, (viewID, position) -> {
                        if (viewID == R.id.archive) {
                            actionCreator.createArchiveShoppingListAction(shoppingListItems.get(position).getCreatedAtTime());
                        } else if (viewID == R.id.delete) {
                            actionCreator.createDeleteShoppingListAction(shoppingListItems.get(position).getCreatedAtTime());
                        }
                    });

        } else {
            onTouchListener.setSwipeable(false);
        }
    }
}