package com.samsoft.xpendify.fragment;


import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;

import com.samsoft.xpendify.R;
import com.samsoft.xpendify.activity.settings.SettingsActivity;
import com.samsoft.xpendify.activity.details.SummaryDetails;
import com.samsoft.xpendify.adapter.SummaryAdapter;
import com.samsoft.xpendify.animation.adapters.AlphaInAnimationAdapter;
import com.samsoft.xpendify.animation.adapters.ScaleInAnimationAdapter;
import com.samsoft.xpendify.animation.animators.ScaleInAnimator;
import com.samsoft.xpendify.callback.OnItemTouchListener;
import com.samsoft.xpendify.database.DatabaseHelper;
import com.samsoft.xpendify.loader.SummaryLoader;
import com.samsoft.xpendify.model.SummaryData;
import com.samsoft.xpendify.widget.swipe.SwipeLayout;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by SAMSOFT on 23-Apr-16.
 */
public class SummaryFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<SummaryData>>, SwipeRefreshLayout.OnRefreshListener {

    DatabaseHelper databaseHelper = DatabaseHelper.getDatabaseHelperInstance(getActivity());
    @Bind(R.id.recyclerView) RecyclerView recyclerView;
    @Bind(R.id.swipeRefreshLayout) SwipeRefreshLayout swipeRefreshLayout;
    SummaryAdapter summaryAdapter;

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
    public Loader<List<SummaryData>> onCreateLoader(int id, Bundle args) {
        return new SummaryLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<List<SummaryData>> loader, final List<SummaryData> summaryData) {

        summaryAdapter = new SummaryAdapter(summaryData);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(getResources().getBoolean(R.bool.IS_TABLET) && summaryData.size() > 0 ? new GridLayoutManager(getActivity(), 3) : new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new ScaleInAnimator(new OvershootInterpolator(1f)));
        recyclerView.setAdapter(new AlphaInAnimationAdapter(new ScaleInAnimationAdapter(summaryAdapter)));

        summaryAdapter.setOnItemTouchListener(new OnItemTouchListener() {
            @Override
            public void onItemTouchListener(int position, View view) {
                if (view.getId() == R.id.actionButton) {
                    summaryAdapter.swipeItemManger.removeShownLayouts((SwipeLayout) view.findViewById(R.id.swipeLayout));
                    switch (PreferenceManager.getDefaultSharedPreferences(getActivity()).getString(SettingsActivity.KEY_PREF_SUMMARY_DISPLAY, "1")) {
                        case "0":
                            databaseHelper.delIncome(summaryData.get(position).getId());
                            break;
                        case "1":
                            databaseHelper.delExpense(summaryData.get(position).getId());
                            break;
                        default:
                    }
                    summaryData.remove(position);
                    summaryAdapter.notifyItemRemoved(position);
                    summaryAdapter.swipeItemManger.closeAllItems();

                } else {

                    Intent intent = new Intent(getActivity(), SummaryDetails.class);
                    intent.putExtra("id", summaryData.get(position).getId());
                    intent.putExtra("amount", summaryData.get(position).getAmount());
                    intent.putExtra("category", summaryData.get(position).getCategory());
                    intent.putExtra("date", summaryData.get(position).getDate());
                    intent.putExtra("note", summaryData.get(position).getNote());
                    startActivity(intent);
                }
            }
        });

    }

    @Override
    public void onLoaderReset(Loader<List<SummaryData>> loader) {
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