package com.samsoft.xpendify.widget.others;

import android.content.Context;
import android.preference.PreferenceManager;

import com.samsoft.xpendify.activity.settings.SettingsActivity;

/**
 * Created by Fred on 29-Oct-15.
 */
public class GetFirstDayOfWeek {

    int dayOfWeek;

    public GetFirstDayOfWeek() {
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public int GetFirstDayOfWeek(Context context) {
        switch (PreferenceManager.getDefaultSharedPreferences(context).getString(SettingsActivity.KEY_PREF_FIRST_DAY_OF_WEEK, "")) {
            case "1":
                setDayOfWeek(1);
                break;
            case "2":
                setDayOfWeek(2);
                break;
            case "3":
                setDayOfWeek(3);
                break;
            case "4":
                setDayOfWeek(4);
                break;
            case "5":
                setDayOfWeek(5);
                break;
            case "6":
                setDayOfWeek(6);
                break;
            case "7":
                setDayOfWeek(7);
                break;
            default:
                setDayOfWeek(1);
        }
        return 0;
    }
}
