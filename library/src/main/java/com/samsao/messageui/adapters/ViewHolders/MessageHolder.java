package com.samsao.messageui.adapters.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.samsao.messageui.models.Message;
import com.samsao.messageui.views.MessageBalloon;
import com.samsao.messageui.views.Timestamp;

/**
 * Created by lcampos on 2015-09-29.
 */
public class MessageHolder extends RecyclerView.ViewHolder {
    protected MessageBalloon mBalloon;
    private int mThisBalloonBackground;
    private int mThisBalloonTextColor;
    private int mThatBalloonBackground;
    private int mThatBalloonTextColor;
    private int mBalloonLateralMargin;
    private Timestamp mTimestamp;


    public MessageHolder(MessageBalloon balloon, int thisBalloonBackground, int thisBalloonTextColor,
                         int thatBalloonBackground, int thatBalloonTextColor, float balloonTextSize,
                         int[] balloonPadding, int[] balloonMargin, int timestampMode, int timestampGravity, int balloonLateralMargin) {
        super(balloon);
        mBalloon = balloon;
        mThisBalloonBackground = thisBalloonBackground;
        mThisBalloonTextColor = thisBalloonTextColor;
        mThatBalloonBackground = thatBalloonBackground;
        mThatBalloonTextColor = thatBalloonTextColor;
        mBalloon.setBalloonTextSize(balloonTextSize);
        mBalloon.setBalloonPadding(balloonPadding);
        mBalloon.setBalloonMargin(balloonMargin);
        mBalloon.setTimestampMode(timestampMode);
        mBalloon.setTimestampGravity(timestampGravity);
        mBalloon.setLateralMargin(balloonLateralMargin);
    }

    public void setup(final Message message) {
        mBalloon.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        mBalloon.setMessage(message);
        if (mTimestamp != null) {
            mTimestamp.setMessage(message);
        }

        if (message.getSide() == Message.THAT_SIDE) {
            mBalloon.setBalloonBackground(mThatBalloonBackground);
            mBalloon.setBalloonTextColor(mThatBalloonTextColor);
        } else {
            mBalloon.setBalloonBackground(mThisBalloonBackground);
            mBalloon.setBalloonTextColor(mThisBalloonTextColor);
        }

    }

    public void setTimestamp(Timestamp timestamp) {
        mTimestamp = timestamp;
        mBalloon.setTimestamp(timestamp);
    }
}
