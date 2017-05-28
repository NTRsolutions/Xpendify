package com.samsoft.xpendify.activity.miscellaneous;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import com.samsoft.xpendify.activity.MainActivity;
import com.samsoft.xpendify.database.DatabaseHelper;
import com.samsoft.xpendify.model.CategoryData;

public class LaunchActivity extends AppCompatActivity {

    public static final CategoryData[] category = new CategoryData[]{new CategoryData("TAX", "expense", "#f44336"), new CategoryData("FOOD", "expense", "#795548"), new CategoryData("TRAVEL", "expense", "#ffc107"), new CategoryData("HEALTH", "expense", "#ffc107"), new CategoryData("INSURANCE", "expense", "#ff9800"), new CategoryData("INVESTMENT", "expense", "#5d4037"), new CategoryData("ENTERTAINMENT", "expense", "#ff9800"), new CategoryData("LOAN", "income", "#009688"), new CategoryData("SALES", "income", "#ffe65100"), new CategoryData("SALARY", "income", "#e91e63")};
    DatabaseHelper databaseHelper = DatabaseHelper.getDatabaseHelperInstance(LaunchActivity.this);
    private boolean passcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        passcode = sharedPreferences.getBoolean("pref_passcode", true);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                if (!databaseHelper.getCatStatus()) {
                    synchronized (this) {
                        for (CategoryData e : category) {
                            databaseHelper.category(e);
                        }
                    }
                }

                Intent intent = (passcode ? new Intent(LaunchActivity.this, LockActivity.class) : new Intent(LaunchActivity.this, MainActivity.class));
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        };

        Thread td = new Thread(runnable);
        td.start();

    }
}
