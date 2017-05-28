package com.samsoft.xpendify.activity.miscellaneous;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.samsoft.xpendify.R;
import com.samsoft.xpendify.activity.MainActivity;
import com.samsoft.xpendify.database.DatabaseHelper;
import com.samsoft.xpendify.model.PatternData;
import com.samsoft.xpendify.widget.others.AppView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Fred on 25-Oct-15.
 */
public class LockActivity extends AppCompatActivity implements AppView.OnPatternListener {

    DatabaseHelper databaseHelper = DatabaseHelper.getDatabaseHelperInstance(LockActivity.this);
    @Bind(R.id.pattern_info) TextView pattern;
    @Bind(R.id.app_view) AppView appView;
    String tempPasscode = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        pattern.setText(getStatus() ? getResources().getString(R.string.draw_pattern) : getResources().getString(R.string.set_pattern));

        appView.setOnPatternListener(this);
    }

    @Override
    public void onPatternDetected(String password) {
        if (getStatus()) {
            if (databaseHelper.vPasscode(password)) {
                startMainActivity();
            } else {
                pattern.setText(getResources().getString(R.string.wrong_pattern));
            }
        } else {
            if (password.length() > 3 && tempPasscode.length() == 0) {
                pattern.setText(getResources().getString(R.string.confirm_pattern));
                tempPasscode = password;
            } else if (tempPasscode.length() > 0) {
                if (tempPasscode.matches(password)) {
                    databaseHelper.iPasscode(new PatternData(password));
                    startMainActivity();
                } else {
                    pattern.setText(getResources().getString(R.string.pattern_mismatch));
                }
            } else {
                pattern.setText(getResources().getString(R.string.invalid_pattern));
            }
        }
    }

    private void startMainActivity() {
        Intent intent = new Intent(LockActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private boolean getStatus() {
        return databaseHelper.getPassState();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("passcode", tempPasscode);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        tempPasscode = savedInstanceState.getString("passcode");
    }
}
