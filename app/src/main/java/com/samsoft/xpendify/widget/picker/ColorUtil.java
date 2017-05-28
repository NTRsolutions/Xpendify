package com.samsoft.xpendify.widget.picker;

/**
 * Created by Daniel Morales on 04/04/2016.
 */
public class ColorUtil {

    public static String getFormattedColorString(int color, boolean showAlpha) {
        if (showAlpha)
            return String.format("#%08X", color);
        else
            return String.format("#%06X", 0xFFFFFF & color);
    }

}
