package com.samsoft.xpendify.widget.others;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;

import com.samsoft.xpendify.R;

/**
 * Created by SAMSOFT on 26-Apr-16.
 */
public class TextView extends android.widget.TextView {

    public TextView(Context context) {
        super(context);
        init(null);
    }

    public TextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public TextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.TextView);
            String fontName = a.getString(R.styleable.TextView_font);
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

