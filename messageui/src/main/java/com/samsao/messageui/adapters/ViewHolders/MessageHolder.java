package com.samsao.messageui.adapters.ViewHolders;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.samsao.messageui.models.Message;
import com.samsao.messageui.views.MessageBalloon;
import com.samsao.messageui.views.TimeStamp;

/**
 * Created by lcampos on 2015-09-29.
 */
public class MessageHolder extends RecyclerView.ViewHolder {
    protected MessageBalloon mBalloon;
    private Drawable mThisBalloonBackground;
    private int mThisBalloonTextColor;
    private Drawable mThatBalloonBackground;
    private int mThatBalloonTextColor;
    private int mBalloonLateralMargin;
    private TimeStamp mTimeStamp;


    public MessageHolder(MessageBalloon balloon, Drawable thisBalloonBackground, int thisBalloonTextColor,
                         Drawable thatBalloonBackground, int thatBalloonTextColor, float balloonTextSize,
                         int[] balloonPadding, int[] balloonMargin, int timeStampMode, int timeStampGravity, int balloonLateralMargin) {
        super(balloon);
        mBalloon = balloon;
        mThisBalloonBackground = thisBalloonBackground;
        mThisBalloonTextColor = thisBalloonTextColor;
        mThatBalloonBackground = thatBalloonBackground;
        mThatBalloonTextColor = thatBalloonTextColor;
        mBalloon.setBalloonTextSize(balloonTextSize);
        mBalloon.setBalloonPadding(balloonPadding);
        mBalloon.setBalloonMargin(balloonMargin);
        mBalloon.setTimeStampMode(timeStampMode);
        mBalloon.setTimeStampGravity(timeStampGravity);
        mBalloon.setLateralMargin(balloonLateralMargin);
    }

    public void setup(final Message message) {
        if (message.getSide() == Message.THAT_SIDE) {
            mBalloon.setBalloonBackground(mThatBalloonBackground);
            mBalloon.setBalloonTextColor(mThatBalloonTextColor);
        } else {
            mBalloon.setBalloonBackground(mThisBalloonBackground);
            mBalloon.setBalloonTextColor(mThisBalloonTextColor);
        }

        mBalloon.setMessage(message);
        if (mTimeStamp != null) {
            mTimeStamp.setMessage(message);
        }
        mBalloon.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
    }

    public void setTimeStamp(TimeStamp timeStamp) {
        mTimeStamp = timeStamp;
        mBalloon.setTimeStamp(timeStamp);
    }
}
