package com.samsao.messageui.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by lcampos on 2015-09-21.
 */
public class WriteMessageBox extends LinearLayout {

    public WriteMessageBox(Context context) {
        super(context);
    }

    public WriteMessageBox(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WriteMessageBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public WriteMessageBox(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
