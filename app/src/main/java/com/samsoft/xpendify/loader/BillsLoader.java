package com.samsoft.xpendify.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.samsoft.xpendify.database.DatabaseHelper;
import com.samsoft.xpendify.model.BillsData;

import java.util.List;

/**
 * Created by Fred on 15-Nov-15.
 */
public class BillsLoader extends AsyncTaskLoader<List<BillsData>> {

    List<BillsData> billsDataList;
    DatabaseHelper databaseHelper = DatabaseHelper.getDatabaseHelperInstance(getContext());

    public BillsLoader(Context context) {
        super(context);
    }

    @Override
    public List<BillsData> loadInBackground() {
        billsDataList = databaseHelper.getBillsInfo();
        return billsDataList;
    }

    @Override
    public void deliverResult(List<BillsData> data) {
        if (isReset()) {
            return;
        }
        billsDataList = data;
        if (isStarted()) {
            super.deliverResult(data);
        }
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if (billsDataList != null) {
            deliverResult(billsDataList);
        }
        if (takeContentChanged() || billsDataList == null) {
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
        billsDataList = null;
    }
}
