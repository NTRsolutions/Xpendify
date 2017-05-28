package com.samsoft.xpendify.activity;

import android.app.Fragment;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.samsoft.xpendify.R;
import com.samsoft.xpendify.activity.entry.AccountEntryActivity;
import com.samsoft.xpendify.activity.entry.BillEntryActivity;
import com.samsoft.xpendify.activity.entry.BudgetEntryActivity;
import com.samsoft.xpendify.activity.entry.SummaryEntryActivity;
import com.samsoft.xpendify.activity.miscellaneous.AboutActivity;
import com.samsoft.xpendify.activity.miscellaneous.HelpActivity;
import com.samsoft.xpendify.activity.settings.SettingsActivity;
import com.samsoft.xpendify.fragment.AccountFragment;
import com.samsoft.xpendify.fragment.BillsFragment;
import com.samsoft.xpendify.fragment.BudgetFragment;
import com.samsoft.xpendify.fragment.SummaryFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Fred on 23-Aug-15.
 */
public class MainActivity extends AppCompatActivity {

    @Bind(R.id.actionButton) FloatingActionButton actionButton;
    @Bind(R.id.navSpinner) AppCompatSpinner navSpinner;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("0A080DB591CD472D8F5D295477F473DE").build();
        mAdView.loadAd(adRequest);

        ArrayAdapter<CharSequence> navSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.nav_array, R.layout.simple_list_item_1);
        navSpinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        navSpinner.setAdapter(navSpinnerAdapter);

        navSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        onReplaceFragment(new SummaryFragment(), "A");
                        break;
                    case 1:
                        onReplaceFragment(new BillsFragment(), "B");
                        break;
                    case 2:
                        onReplaceFragment(new BudgetFragment(), "C");
                        break;
                    default:
                        onReplaceFragment(new AccountFragment(), "D");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (getFragmentManager().findFragmentById(R.id.nav_content).getTag()) {
                    case "A":
                        startActivity(SummaryEntryActivity.class);
                        break;
                    case "B":
                        startActivity(BillEntryActivity.class);
                        break;
                    case "C":
                        startActivity(BudgetEntryActivity.class);
                        break;
                    default:
                        startActivity(AccountEntryActivity.class);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(true);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_analysis:
                startActivity(AnalysisActivity.class);
                return true;
            case R.id.action_settings:
                startActivity(SettingsActivity.class);
                return true;
            case R.id.action_about:
                startActivity(AboutActivity.class);
                return true;
            case R.id.action_help:
                startActivity(HelpActivity.class);
                return true;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    private void startActivity(Class cls) {
        Intent intent = new Intent(MainActivity.this, cls);
        startActivity(intent);
    }

    public void onReplaceFragment(Fragment fragment, String tag) {
        getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.animator.scalexy_enter, R.animator.scalexy_exit)
                .replace(R.id.nav_content, fragment, tag)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        if (mAdView != null) {
            mAdView.resume();
        }
        super.onResume();
    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }
}