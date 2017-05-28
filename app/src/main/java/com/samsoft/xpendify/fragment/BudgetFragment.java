package com.samsoft.xpendify.fragment;


import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;

import com.samsoft.xpendify.R;
import com.samsoft.xpendify.activity.details.BudgetDetails;
import com.samsoft.xpendify.adapter.BudgetAdapter;
import com.samsoft.xpendify.animation.adapters.AlphaInAnimationAdapter;
import com.samsoft.xpendify.animation.adapters.ScaleInAnimationAdapter;
import com.samsoft.xpendify.animation.animators.ScaleInAnimator;
import com.samsoft.xpendify.callback.OnItemTouchListener;
import com.samsoft.xpendify.database.DatabaseHelper;
import com.samsoft.xpendify.loader.BudgetLoader;
import com.samsoft.xpendify.model.BudgetData;
import com.samsoft.xpendify.widget.swipe.SwipeLayout;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by Fred on 14-Nov-15.
 */
public class BudgetFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<BudgetData>>, SwipeRefreshLayout.OnRefreshListener {

    DatabaseHelper databaseHelper = DatabaseHelper.getDatabaseHelperInstance(getActivity());
    @Bind(R.id.recyclerView) RecyclerView recyclerView;
    @Bind(R.id.swipeRefreshLayout) SwipeRefreshLayout swipeRefreshLayout;
    BudgetAdapter budgetAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.swipe_refresh_recycler, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.primaryDarkColor, R.color.accentColor, R.color.primaryColor, R.color.accentColor);
        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.primaryColor);

    }

    @Override
    public Loader<List<BudgetData>> onCreateLoader(int id, Bundle args) {
        return new BudgetLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<List<BudgetData>> loader, final List<BudgetData> budgetData) {
        budgetAdapter = new BudgetAdapter(budgetData);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(getResources().getBoolean(R.bool.IS_TABLET) && budgetData.size() > 0 ? new GridLayoutManager(getActivity(), 3) : new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new ScaleInAnimator(new OvershootInterpolator(1f)));
        recyclerView.setAdapter(new AlphaInAnimationAdapter(new ScaleInAnimationAdapter(budgetAdapter)));

        budgetAdapter.setOnItemTouchListener(new OnItemTouchListener() {
            @Override
            public void onItemTouchListener(int position, View view) {
                if (view.getId() == R.id.actionButton) {
                    budgetAdapter.swipeItemManger.removeShownLayouts((SwipeLayout) view.findViewById(R.id.swipeLayout));

                    budgetData.remove(position);
                    budgetAdapter.notifyItemRemoved(position);
                    budgetAdapter.swipeItemManger.closeAllItems();

                } else {

                    Intent intent = new Intent(getActivity(), BudgetDetails.class);

                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onLoaderReset(Loader<List<BudgetData>> loader) {
        recyclerView.setAdapter(null);
    }

    @Override
    public void onRefresh() {
        getLoaderManager().restartLoader(0, null, this).forceLoad();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        getLoaderManager().initLoader(0, null, this).forceLoad();
    }
}
