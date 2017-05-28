package com.samsoft.xpendify.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.samsoft.xpendify.database.DatabaseHelper;
import com.samsoft.xpendify.model.CategoryData;

import java.util.Collections;
import java.util.List;

/**
 * Created by Fred on 15-Nov-15.
 */
public class CategoryLoader extends AsyncTaskLoader<List<CategoryData>> {

    List<CategoryData> categoryDataList = Collections.EMPTY_LIST;
    DatabaseHelper databaseHelper = DatabaseHelper.getDatabaseHelperInstance(getContext());

    public CategoryLoader(Context context) {
        super(context);
    }

    @Override
    public List<CategoryData> loadInBackground() {
        //categoryDataList = databaseHelper.getBudget();
        return categoryDataList;
    }

    @Override
    public void deliverResult(List<CategoryData> data) {
        if (isReset()) {
            return;
        }
        categoryDataList = data;
        if (isStarted()) {
            super.deliverResult(data);
        }
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if (categoryDataList != null) {
            deliverResult(categoryDataList);
        }
        if (takeContentChanged() || categoryDataList == null) {
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
        categoryDataList = null;
    }
}
