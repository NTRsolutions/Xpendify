package com.samsoft.xpendify.activity.settings;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.samsoft.xpendify.R;

import java.util.List;

/**
 * Created by Fred on 22-Oct-15.
 */
public class SettingsActivity extends AppCompatPreferenceActivity {

    public static final String KEY_PREF_DATE_FORMAT = "pref_date_format";
    public static final String KEY_PREF_SUMMARY_DISPLAY = "pref_summary_display";
    public static final String KEY_PREF_FIRST_DAY_OF_WEEK = "pref_first_day_of_week";
    //public static final String[] FRAGMENTS = {"com.samsoft.xpendify.activity.settings.SettingsActivity$SettingsFragment"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
        View content = root.getChildAt(0);
        LinearLayout linearLayout = (LinearLayout) View.inflate(this, R.layout.activity_settings, null);

        root.removeAllViews();
        linearLayout.addView(content);
        root.addView(linearLayout);

        Toolbar toolbar = (Toolbar) linearLayout.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBuildHeaders(List<Header> target) {
        super.onBuildHeaders(target);
        loadHeadersFromResource(R.xml.preference_headers, target);
        //preferences = PreferenceManager.getDefaultSharedPreferences(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //preferences.registerOnSharedPreferenceChangeListener(preferenceChangeListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // preferences.unregisterOnSharedPreferenceChangeListener(preferenceChangeListener);
    }

    @Override
    protected boolean isValidFragment(String fragmentName) {
       /* for (String FRAGMENT : FRAGMENTS) {
            if (FRAGMENT.equals(fragmentName)) {
                return true;
            }
        }
        return super.isValidFragment(fragmentName);*/
        return true;
    }

/*    public static class SettingsFragment extends PreferenceFragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            getActivity().setTheme(R.style.AppTheme);
            if (getArguments() != null) {
                String page = getArguments().getString("PAGE");
                if (page != null)
                    switch (page) {
                        case "P1":
                            addPreferencesFromResource(R.xml.preference_general);
                            break;
                        case "P2":
                            addPreferencesFromResource(R.xml.preference_summary);
                            break;
                        case "P3":
                            addPreferencesFromResource(R.xml.preference_budget);
                            break;
                        case "P4":
                            addPreferencesFromResource(R.xml.preference_reports);
                            break;
                        case "P5":
                            addPreferencesFromResource(R.xml.preference_security);
                            break;
                        default:
                    }
            }
        }
    }*/
}



