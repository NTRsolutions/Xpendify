package com.samsoft.xpendify.widget.picker.colormode;


import com.samsoft.xpendify.widget.picker.colormode.mode.ARGB;
import com.samsoft.xpendify.widget.picker.colormode.mode.AbstractColorMode;
import com.samsoft.xpendify.widget.picker.colormode.mode.HSV;
import com.samsoft.xpendify.widget.picker.colormode.mode.RGB;

/**
 * Created by Daniel Morales on 04/04/2016.
 */
public enum ColorMode {
    RGB, HSV, ARGB;

    public AbstractColorMode getColorMode() {
        switch (this) {
            case RGB:
                return new RGB();
            case HSV:
                return new HSV();
            case ARGB:
                return new ARGB();
            default:
                return new RGB();
        }
    }
}
