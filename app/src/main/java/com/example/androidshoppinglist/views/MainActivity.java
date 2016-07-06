package com.example.androidshoppinglist.views;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.androidshoppinglist.R;
import com.example.androidshoppinglist.actions.ActionCreator;
import com.example.androidshoppinglist.app.BaseApplication;
import com.example.androidshoppinglist.data.ShoppingListEvent;
import com.example.androidshoppinglist.models.ShoppingListItem;
import com.example.androidshoppinglist.stores.DatabaseStore;
import com.example.androidshoppinglist.stores.ShoppingListStore;
import com.example.androidshoppinglist.views.adapters.ShoppingListAdapter;

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
        ListTitleDialogFragment.ListTitleDialogListener{

    @ViewById(R.id.toolbar) Toolbar toolbar;
    @ViewById(R.id.fab) FloatingActionButton fab;
    @ViewById(R.id.drawer_layout) DrawerLayout drawer;
    @ViewById(R.id.nav_view) NavigationView navigationView;
    @ViewById(R.id.recyclerView) RecyclerView recyclerView;

    @Inject ActionCreator actionCreator;
    @Inject EventBus eventBus;
    @Inject ShoppingListStore shoppingListStore;
    @Inject DatabaseStore databaseStore;

    List<ShoppingListItem> shoppingListItems;
    ShoppingListAdapter mRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BaseApplication) getApplication()).component().inject(this);
    }

    @Override
    public void onStop() {
        shoppingListStore.onPause();
        databaseStore.onPause();
        eventBus.unregister(this);
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        mRecyclerAdapter.notifyDataSetChanged();
    }

    @AfterViews
    public void prepare() {
        eventBus.register(this);

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        shoppingListItems = new ArrayList<>();
        setupAdapter();

        actionCreator.createGetShoppingListsFromDbAction(this);
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

        } else if (id == R.id.nav_archived) {

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
        mRecyclerAdapter.notifyData(shoppingListItems);
    }

    @Subscribe
    public void onShoppingListEvent(ShoppingListEvent shoppingListEvent) {
        shoppingListItems = shoppingListEvent.getList();
        mRecyclerAdapter.notifyData(shoppingListItems);
    }

    private void setupAdapter() {
        mRecyclerAdapter = new ShoppingListAdapter(shoppingListItems, this);
        recyclerView.setAdapter(mRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
