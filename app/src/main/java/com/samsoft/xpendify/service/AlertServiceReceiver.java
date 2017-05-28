package com.samsoft.xpendify.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


/**
 * Created by Fred on 10-Jan-16.
 */
public class AlertServiceReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent service = new Intent(context, AlertService.class);
        context.startService(service);
    }
}
