package com.samsoft.xpendify.activity.details;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.samsoft.xpendify.R;

/**
 * Created by Fred on 01-Nov-15.
 */
public class BudgetDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


       /* BudgetBottomSheetDialogFragment summaryBottomSheetDialogFragment = new BudgetBottomSheetDialogFragment();
        summaryBottomSheetDialogFragment.show(getSupportFragmentManager(), summaryBottomSheetDialogFragment.getTag());*/
    }



    public static class BudgetBottomSheetDialogFragment extends BottomSheetDialogFragment {

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
            View contentView = View.inflate(getContext(), 0, null);
            dialog.setContentView(contentView);

            CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
            CoordinatorLayout.Behavior behavior = params.getBehavior();

            if (behavior != null && behavior instanceof BottomSheetBehavior) {
                ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
            }

        }
    }

}
