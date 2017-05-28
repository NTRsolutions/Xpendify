package com.samsoft.xpendify.loader;


import android.content.AsyncTaskLoader;
import android.content.Context;
import android.preference.PreferenceManager;

import com.samsoft.xpendify.activity.settings.SettingsActivity;
import com.samsoft.xpendify.database.DatabaseHelper;
import com.samsoft.xpendify.model.SummaryData;

import java.util.List;

/**
 * Created by Fred on 15-Nov-15.
 */
public class SummaryLoader extends AsyncTaskLoader<List<SummaryData>> {

    List<SummaryData> summaryDataList;
    DatabaseHelper databaseHelper = DatabaseHelper.getDatabaseHelperInstance(getContext());

    public SummaryLoader(Context context) {
        super(context);
    }

    @Override
    public List<SummaryData> loadInBackground() {
        switch (PreferenceManager.getDefaultSharedPreferences(getContext()).getString(SettingsActivity.KEY_PREF_SUMMARY_DISPLAY, "1")) {
            case "0":
                summaryDataList = databaseHelper.getINCOME();
                break;
            case "1":
                summaryDataList = databaseHelper.getEXPENSE();
                break;
            default:
        }
        return summaryDataList;
    }

    @Override
    public void deliverResult(List<SummaryData> data) {
        if (isReset()) {
            return;
        }
        summaryDataList = data;
        if (isStarted()) {
            super.deliverResult(data);
        }
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if (summaryDataList != null) {
            deliverResult(summaryDataList);
        }
        if (takeContentChanged() || summaryDataList == null) {
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        super.onStopLoading();
        cancelLoad();
    }

    @Override
    protected void onReset() {
        super.onReset();
        summaryDataList = null;
    }
}
