package com.samsao.messageui.adapters.ViewHolders;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;

import com.samsao.messageui.models.Message;
import com.samsao.messageui.views.Timestamp;

/**
 * Created by lcampos on 2015-09-29.
 */
public class TimestampHolder extends RecyclerView.ViewHolder {
    protected Timestamp mTimestamp;

    public TimestampHolder(Timestamp timestamp, int[] timestampMargin, float timestampTextSize,
                           int timestampTextColor, Drawable timestampBackground, int timestampPosition) {
        super(timestamp);
        mTimestamp = timestamp;

        mTimestamp.setMargins(timestampMargin);
        mTimestamp.setTextSize(timestampTextSize);
        mTimestamp.setBackground(timestampBackground);
        mTimestamp.setPosition(timestampPosition);
        mTimestamp.setTextColor(timestampTextColor);
    }

    public void setup(final Message message) {
        mTimestamp.setMessage(message);
    }
}
