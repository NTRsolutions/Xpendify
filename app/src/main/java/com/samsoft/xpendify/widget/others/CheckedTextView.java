package com.samsoft.xpendify.widget.others;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;

import com.samsoft.xpendify.R;

/**
 * Created by Fred on 26-Jul-16.
 */
public class CheckedTextView extends android.widget.CheckedTextView {

    public CheckedTextView(Context context) {
        super(context);
        init(null);
    }

    public CheckedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CheckedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CheckedTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CheckedTextView);
            String fontName = a.getString(R.styleable.CheckedTextView_fonts);
            try {
                if (fontName != null) {
                    Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/" + fontName);
                    setTypeface(myTypeface);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            a.recycle();
        }
    }
}
