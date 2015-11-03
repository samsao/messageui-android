package com.samsao.messageui.utils;

import android.content.Context;
import android.text.format.DateFormat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by lcampos on 2015-09-22.
 */
public class Utils {

    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

    /**
     * generates an unique number that can be user as a view's id
     * @return
     */
    public static int generateViewId() {
        for (;;) {
            final int result = sNextGeneratedId.get();
            // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
            int newValue = result + 1;
            if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
            if (sNextGeneratedId.compareAndSet(result, newValue)) {
                return result;
            }
        }
    }

    /**
     * Get time accordingly to the format of the device
     * @param context
     * @return time
     */
    public static String getCurrentTime(Context context) {
        Calendar c = Calendar.getInstance();
        int mode = c.get(Calendar.AM_PM);
        Date date = c.getTime();
        String time;

        if (DateFormat.is24HourFormat(context))
        {
            SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
            time = sdf.format(date).toString();
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("h:mm");
            time = sdf.format(date).toString() + " " + (mode == 0 ? "AM" : "PM");
        }
        return time;
    }
}
