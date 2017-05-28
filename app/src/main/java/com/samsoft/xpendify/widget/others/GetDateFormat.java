package com.samsoft.xpendify.widget.others;

import android.content.Context;
import android.preference.PreferenceManager;

import com.samsoft.xpendify.activity.settings.SettingsActivity;

/**
 * Created by Fred on 07-Oct-15.
 */
public class GetDateFormat {

    String dateFormat;

    public GetDateFormat() {
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String getDate(Context context) {
        switch (PreferenceManager.getDefaultSharedPreferences(context).getString(SettingsActivity.KEY_PREF_DATE_FORMAT, "dd - MM - yyyy")) {
            case "0":
                setDateFormat("dd MMM yyyy");
                break;
            case "1":
                setDateFormat("dd - MM - yyyy");
                break;
            case "2":
                setDateFormat("MM - dd - yyyy");
                break;
            case "3":
                setDateFormat("yyyy - MM - dd");
                break;
            case "4":
                setDateFormat("yyyy - dd - MM");
                break;
            case "5":
                setDateFormat("EEEE, d MMMM, yyyy");
                break;
            default:
                setDateFormat("dd - MM - yyyy");
        }
        return null;
    }

}
