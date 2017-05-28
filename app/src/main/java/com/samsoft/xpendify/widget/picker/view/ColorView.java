package com.samsoft.xpendify.widget.picker.view;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.samsoft.xpendify.R;
import com.samsoft.xpendify.widget.picker.ColorUtil;
import com.samsoft.xpendify.widget.picker.ModeAlert;
import com.samsoft.xpendify.widget.picker.colormode.Channel;
import com.samsoft.xpendify.widget.picker.colormode.ColorMode;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Daniel Morales on 04/04/2016.
 */
public class ColorView extends RelativeLayout {

    public final static int DEFAULT_COLOR = Color.GRAY;
    public final static ColorMode DEFAULT_MODE = ColorMode.RGB;
    public final static ModeAlert DEFAULT_INDICATOR = ModeAlert.DECIMAL;
    public final static boolean DEFAULT_TEXT_INDICATOR_STATE = false;

    private final ColorMode colorMode;
    private
    @ColorInt
    int currentColor;
    private ModeAlert modeAlert;
    private boolean showTextIndicator;

    public ColorView(Context context) {
        this(DEFAULT_COLOR, DEFAULT_TEXT_INDICATOR_STATE, DEFAULT_MODE, DEFAULT_INDICATOR, context);
    }

    public ColorView(@ColorInt int initialColor, ColorMode colorMode, Context context) {
        this(initialColor, false, colorMode, DEFAULT_INDICATOR, context);
    }

    public ColorView(@ColorInt int initialColor, boolean showTextIndicator, ColorMode colorMode, ModeAlert modeAlert, Context context) {
        super(context);
        this.modeAlert = modeAlert;
        this.colorMode = colorMode;
        this.currentColor = initialColor;
        this.showTextIndicator = showTextIndicator;
        init();
    }

    void init() {
        inflate(getContext(), R.layout.color_view, this);
        setClipToPadding(false);

        final View colorView = findViewById(R.id.color_view);
        colorView.setBackgroundColor(currentColor);
        final TextView colorTextIndicator = (TextView) findViewById(R.id.tv_color_indicator);
        if (this.showTextIndicator) {
            colorTextIndicator.setVisibility(View.VISIBLE);
            if (colorTextIndicator.getTag().equals("large-land"))
                findViewById(R.id.large_land_divider).setVisibility(View.VISIBLE);
        }

        List<Channel> channels = colorMode.getColorMode().getChannels();
        final List<ChannelView> channelViews = new ArrayList<>();
        for (Channel c : channels) {
            channelViews.add(new ChannelView(c, currentColor, getContext()));
        }

        updateText(colorView, colorTextIndicator, channelViews, channels);

        ChannelView.OnProgressChangedListener seekBarChangeListener = new ChannelView.OnProgressChangedListener() {
            @Override
            public void onProgressChanged() {
                List<Channel> channels = new ArrayList<>();
                for (ChannelView chan : channelViews)
                    channels.add(chan.getChannel());
                updateText(colorView, colorTextIndicator, channelViews, channels);
            }
        };

        ViewGroup channelContainer = (ViewGroup) findViewById(R.id.channel_container);
        for (ChannelView c : channelViews) {
            channelContainer.addView(c);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) c.getLayoutParams();
            params.topMargin =
                    getResources().getDimensionPixelSize(R.dimen.channel_view_margin_top);
            params.bottomMargin =
                    getResources().getDimensionPixelSize(R.dimen.channel_view_margin_bottom);

            c.registerListener(seekBarChangeListener);
        }
    }

    private void updateText(View colorView, TextView colorTextIndicator,
                            List<ChannelView> channelViews, List<Channel> channels) {
        currentColor = colorMode.getColorMode().evaluateColor(channels);
        colorView.setBackgroundColor(currentColor);

        if (modeAlert == ModeAlert.HEX)
            colorTextIndicator.setText(ColorUtil.getFormattedColorString(currentColor, colorMode == ColorMode.ARGB));
        else {
            String decText = "";
            for (ChannelView chan : channelViews)
                decText += chan.getChannel().getProgress() + " ";
            colorTextIndicator.setText(String.valueOf(decText.trim()));
        }

        if (!colorTextIndicator.getTag().equals("large-land"))
            colorTextIndicator.setTextColor(getInverseColor(currentColor));
    }

    private int getInverseColor(int color) {
        return Color.argb(Color.alpha(color), 255 - Color.red(color), 255 -
                Color.green(color), 255 - Color.blue(color));
    }

    public ColorMode getColorMode() {
        return colorMode;
    }

    public int getCurrentColor() {
        return currentColor;
    }

    public ModeAlert getModeAlert() {
        return modeAlert;
    }

    public boolean isShowTextIndicator() {
        return showTextIndicator;
    }

    public void enableButtonBar(final ButtonBarListener listener) {
        LinearLayout buttonBar = (LinearLayout) findViewById(R.id.button_bar);
        TextView positiveButton = (TextView) buttonBar.findViewById(R.id.positive_button);
        TextView negativeButton = (TextView) buttonBar.findViewById(R.id.negative_button);

        if (listener != null) {
            buttonBar.setVisibility(VISIBLE);
            positiveButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onPositiveButtonClick(currentColor);
                }
            });

            negativeButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onNegativeButtonClick();
                }
            });
        } else {
            buttonBar.setVisibility(GONE);
            positiveButton.setOnClickListener(null);
            negativeButton.setOnClickListener(null);
        }
    }

    public interface ButtonBarListener {
        void onPositiveButtonClick(int color);

        void onNegativeButtonClick();
    }
}
