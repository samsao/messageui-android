package com.samsao.messageui.adapters.ViewHolders;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;

import com.samsao.messageui.models.Message;
import com.samsao.messageui.views.TimeStamp;

/**
 * Created by lcampos on 2015-09-29.
 */
public class TimeStampHolder extends RecyclerView.ViewHolder {
    protected TimeStamp mTimeStamp;

    public TimeStampHolder(TimeStamp timeStamp, int[] timeStampMargin, float timeStampTextSize,
                           int timeStampTextColor, Drawable timeStampBackground, int timeStampPosition) {
        super(timeStamp);
        mTimeStamp = timeStamp;

        mTimeStamp.setMargins(timeStampMargin);
        mTimeStamp.setTextSize(timeStampTextSize);
        mTimeStamp.setBackground(timeStampBackground);
        mTimeStamp.setPosition(timeStampPosition);
        mTimeStamp.setTextColor(timeStampTextColor);
    }

    public void setup(final Message message) {
        mTimeStamp.setMessage(message);
    }
}
