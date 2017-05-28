package com.samsoft.xpendify.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.samsoft.xpendify.database.DatabaseHelper;
import com.samsoft.xpendify.model.AccountData;

import java.util.List;

/**
 * Created by Fred on 15-Nov-15.
 */
public class AccountLoader extends AsyncTaskLoader<List<AccountData>> {

    List<AccountData> accountDataList;
    DatabaseHelper databaseHelper = DatabaseHelper.getDatabaseHelperInstance(getContext());

    public AccountLoader(Context context) {
        super(context);
    }

    @Override
    public List<AccountData> loadInBackground() {
        accountDataList = databaseHelper.getAccountInfo();
        return accountDataList;
    }

    @Override
    public void deliverResult(List<AccountData> data) {
        if (isReset()) {
            return;
        }
        accountDataList = data;
        if (isStarted()) {
            super.deliverResult(data);
        }
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if (accountDataList != null) {
            deliverResult(accountDataList);
        }
        if (takeContentChanged() || accountDataList == null) {
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
        accountDataList = null;
    }
}
