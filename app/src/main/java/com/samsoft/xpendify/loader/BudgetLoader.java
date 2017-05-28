package com.samsoft.xpendify.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.samsoft.xpendify.database.DatabaseHelper;
import com.samsoft.xpendify.model.BudgetData;

import java.util.Collections;
import java.util.List;

/**
 * Created by Fred on 15-Nov-15.
 */
public class BudgetLoader extends AsyncTaskLoader<List<BudgetData>> {

    List<BudgetData> MyBudgetList = Collections.EMPTY_LIST;
    DatabaseHelper databaseHelper = DatabaseHelper.getDatabaseHelperInstance(getContext());

    public BudgetLoader(Context context) {
        super(context);
    }

    @Override
    public List<BudgetData> loadInBackground() {
        MyBudgetList = databaseHelper.getBudget();
        return MyBudgetList;
    }

    @Override
    public void deliverResult(List<BudgetData> data) {
        if (isReset()) {
            return;
        }
        MyBudgetList = data;
        if (isStarted()) {
            super.deliverResult(data);
        }
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if (MyBudgetList != null) {
            deliverResult(MyBudgetList);
        }
        if (takeContentChanged() || MyBudgetList == null) {
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
        MyBudgetList = null;
    }
}
