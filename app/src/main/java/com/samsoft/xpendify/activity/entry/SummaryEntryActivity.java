package com.samsoft.xpendify.activity.entry;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.samsoft.xpendify.R;
import com.samsoft.xpendify.database.DatabaseHelper;
import com.samsoft.xpendify.model.SummaryData;
import com.samsoft.xpendify.widget.others.GetDateFormat;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Fred on 7/10/2016.
 */
public class SummaryEntryActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    GetDateFormat getDateFormat = new GetDateFormat();
    Calendar calender = GregorianCalendar.getInstance();

    DatabaseHelper databaseHelper = DatabaseHelper.getDatabaseHelperInstance(this);
    @Bind(R.id.actionButton) FloatingActionButton actionButton;
    @Bind(R.id.account_debited) Spinner account_debited;  @Bind(R.id.amount_spent) EditText amount_spent;
    @Bind(R.id.expense_category) Spinner expense_category; @Bind(R.id.expense_date) EditText expense_date;
    @Bind(R.id.expense_note) EditText expense_note; @Bind(R.id.coordinating_layout) CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        //navigate backwards
        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }

        //insert record
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper.expense(new SummaryData("150","FOOD","2016-09-20","DESC"));
                    Snackbar.make(coordinatorLayout, "OPERATION SUCCESSFUL, RECONCILE ACCOUNT?", Snackbar.LENGTH_LONG).setAction("YES", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    }).show();

            }
        });

        getDateFormat.getDate(SummaryEntryActivity.this);
        expense_date.setKeyListener(null);


        expense_date.setText(new SimpleDateFormat(getDateFormat.getDateFormat(), Locale.getDefault()).format(calender.getTime()));
        expense_category.setAdapter(new ArrayAdapter<>(SummaryEntryActivity.this, R.layout.simple_list_item_1, databaseHelper.getCategory("expense")));
        account_debited.setAdapter(new ArrayAdapter<>(SummaryEntryActivity.this, R.layout.simple_list_item_1, databaseHelper.getDebitCreditInfo()));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.date_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_date:
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        SummaryEntryActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "DatePickerDialog");
                return true;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        DatePickerDialog dpd = (DatePickerDialog) getFragmentManager().findFragmentByTag("DatePickerDialog");
        if (dpd != null) dpd.setOnDateSetListener(this);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        Calendar newDate = Calendar.getInstance();

        //format time
        SimpleDateFormat dateFormatter = new SimpleDateFormat(getDateFormat.getDateFormat(), Locale.getDefault());

        //set time
        newDate.set(year, monthOfYear, dayOfMonth); expense_date.setText(dateFormatter.format(newDate.getTime()));
    }
}
