package com.samsoft.xpendify.widget.picker.colormode.mode;


import com.samsoft.xpendify.widget.picker.colormode.Channel;

import java.util.List;

/**
 * Created by Pavel Sikun on 28.03.16.
 */
public interface AbstractColorMode {
    int evaluateColor(List<Channel> channels);
    List<Channel> getChannels();
}
