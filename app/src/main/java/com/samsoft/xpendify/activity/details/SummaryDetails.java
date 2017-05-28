package com.samsoft.xpendify.activity.details;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.samsoft.xpendify.R;

/**
 * Created by Fred on 01-Nov-15.
 */
public class SummaryDetails extends AppCompatActivity {

    FloatingActionButton floatingActionButton;
    TextView category, amount, date, note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }

        category = (TextView)
                findViewById(R.id.category);
        amount = (TextView)
                findViewById(R.id.amount);
        date = (TextView)
                findViewById(R.id.date);
        note = (TextView)
                findViewById(R.id.note);

        category.setText(
                extras.get("category").toString());
        amount.setText
                (extras.get("amount").toString());
        date.setText
                (extras.get("date").toString());
        note.setText(
                extras.get("note").toString());


        /*floatingActionButton = (FloatingActionButton) findViewById(R.id.actionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SummaryBottomSheetDialogFragment summaryBottomSheetDialogFragment = new SummaryBottomSheetDialogFragment();
                summaryBottomSheetDialogFragment.show(getSupportFragmentManager(), summaryBottomSheetDialogFragment.getTag());
            }
        });*/

    }


    public static class SummaryBottomSheetDialogFragment extends BottomSheetDialogFragment {

        private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    dismiss();
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        };

        @Override
        public void setupDialog(Dialog dialog, int style) {
            super.setupDialog(dialog, style);
            View view = View.inflate(getContext(), 0, null);
            dialog.setContentView(view);

            CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) view.getParent()).getLayoutParams();
            CoordinatorLayout.Behavior behavior = params.getBehavior();

            if (behavior != null && behavior instanceof BottomSheetBehavior) {
                ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
            }

        }
    }
}
