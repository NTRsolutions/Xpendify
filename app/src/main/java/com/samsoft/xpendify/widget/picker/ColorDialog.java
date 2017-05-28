package com.samsoft.xpendify.widget.picker;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.samsoft.xpendify.R;
import com.samsoft.xpendify.widget.picker.colormode.ColorMode;
import com.samsoft.xpendify.widget.picker.view.ColorView;

/**
 * Created by Daniel Morales on 04/04/2016.
 */
public class ColorDialog extends DialogFragment {

    private final static String ARG_INITIAL_COLOR = "arg_initial_color";
    private final static String ARG_COLOR_MODE_ID = "arg_color_mode_id";
    private final static String ARG_INDICATOR_MODE = "arg_indicator_mode";
    private final static String ARG_SHOW_COLOR_INDICATOR = "arg_show_color_indicator";

    private OnColorSelectedListener listener;
    private ColorView colorView;

    private static ColorDialog newInstance(@ColorInt int initialColor, ColorMode colorMode, ModeAlert modeAlert, boolean showColorIndicator) {
        ColorDialog fragment = new ColorDialog();
        fragment.setArguments(makeArgs(initialColor, colorMode, modeAlert, showColorIndicator));
        return fragment;
    }

    private static Bundle makeArgs(@ColorInt int initialColor, ColorMode colorMode, ModeAlert modeAlert, boolean showColorIndicator) {
        Bundle args = new Bundle();
        args.putInt(ARG_INITIAL_COLOR, initialColor);
        args.putInt(ARG_COLOR_MODE_ID, colorMode.ordinal());
        args.putInt(ARG_INDICATOR_MODE, modeAlert.ordinal());
        args.putBoolean(ARG_SHOW_COLOR_INDICATOR, showColorIndicator);
        return args;
    }

    public void setListener(OnColorSelectedListener listener) {
        this.listener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        if (savedInstanceState == null) {
            colorView = new ColorView(
                    getArguments().getInt(ARG_INITIAL_COLOR),

                    getArguments().getBoolean(ARG_SHOW_COLOR_INDICATOR),

                    ColorMode.values()[
                            getArguments().getInt(ARG_COLOR_MODE_ID)],

                    ModeAlert.values()[
                            getArguments().getInt(ARG_INDICATOR_MODE)],


                    getActivity());
        } else {
            colorView = new ColorView(

                    savedInstanceState.getInt(ARG_INITIAL_COLOR, ColorView.DEFAULT_COLOR),

                    savedInstanceState.getBoolean(ARG_SHOW_COLOR_INDICATOR),

                    ColorMode.values()[
                            savedInstanceState.getInt(ARG_COLOR_MODE_ID)],

                    ModeAlert.values()[
                            savedInstanceState.getInt(ARG_INDICATOR_MODE)],

                    getActivity());
        }

        colorView.enableButtonBar(new ColorView.ButtonBarListener() {
            @Override
            public void onPositiveButtonClick(int color) {
                if (listener != null) listener.onColorSelected(color);
                dismiss();
            }

            @Override
            public void onNegativeButtonClick() {
                dismiss();
            }
        });

        final AlertDialog ad = new AlertDialog.Builder(getActivity(), getTheme()).setView(colorView).create();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
            ad.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialog) {
                    measureLayout(ad);
                }
            });
        } else {
            measureLayout(ad);
        }

        return ad;
    }

    void measureLayout(AlertDialog ad) {
        int multiplier = getResources().getConfiguration()
                .orientation == Configuration.ORIENTATION_LANDSCAPE
                ? 2
                : 1;

        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int height = getResources().getConfiguration()
                .orientation == Configuration.ORIENTATION_LANDSCAPE
                ? (int) (metrics.heightPixels * 0.8)
                : WindowManager.LayoutParams.WRAP_CONTENT;

        int width = getResources().getDimensionPixelSize(R.dimen.chroma_dialog_width) * multiplier;

        ad.getWindow().setLayout(width, height);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putAll(makeArgs(colorView.getCurrentColor(), colorView.getColorMode(), colorView.getModeAlert(), colorView.isShowTextIndicator()));
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        listener = null;
    }

    public static class Builder {
        private
        @ColorInt
        int initialColor = ColorView.DEFAULT_COLOR;
        private ColorMode colorMode = ColorView.DEFAULT_MODE;
        private ModeAlert modeAlert = ModeAlert.DECIMAL;
        private boolean showColorIndicator = ColorView.DEFAULT_TEXT_INDICATOR_STATE;
        private OnColorSelectedListener listener = null;

        public Builder initialColor(@ColorInt int initialColor) {
            this.initialColor = initialColor;
            return this;
        }

        public Builder colorMode(ColorMode colorMode) {
            this.colorMode = colorMode;
            return this;
        }

        public Builder showColorIndicator(boolean showColorIndicator) {
            this.showColorIndicator = showColorIndicator;
            return this;
        }

        public Builder indicatorMode(ModeAlert modeAlert) {
            this.modeAlert = modeAlert;
            return this;
        }

        public Builder onColorSelected(OnColorSelectedListener listener) {
            this.listener = listener;
            return this;
        }

        public ColorDialog create() {
            ColorDialog fragment = newInstance(initialColor, colorMode, modeAlert, showColorIndicator);
            fragment.setListener(listener);
            return fragment;
        }
    }
}
