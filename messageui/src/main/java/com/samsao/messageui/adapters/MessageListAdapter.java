package com.samsao.messageui.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.samsao.messageui.adapters.ViewHolders.MessageHolder;
import com.samsao.messageui.adapters.ViewHolders.ProgressBarSpinnerHolder;
import com.samsao.messageui.adapters.ViewHolders.TimeStampHolder;
import com.samsao.messageui.models.Message;
import com.samsao.messageui.views.MessageBalloon;
import com.samsao.messageui.views.MessagesWindow;
import com.samsao.messageui.views.TimeStamp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lcampos on 2015-09-21.
 */
public class MessageListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected static final int TYPE_SPINNER = 0;
    protected static final int TYPE_ITEM = 1;
    protected static final int TYPE_TIME_STAMP = 2;

    protected List<Message> mMessages;
    private Context mContext;
    private ProgressBar mProgressBar;
    private boolean mHasMoreToLoad;

    //Features for customization
    private Drawable mThisBalloonBackground;
    private int mThisBalloonTextColor;
    private Drawable mThatBalloonBackground;
    private int mThatBalloonTextColor;
    private float mBalloonTextSize;
    private int[] mBalloonPadding;
    private int[] mBalloonMargin;
    private int mTimeStampMode;
    private int mTimeStampGravity;
    private int mBalloonLateralMargin;

    private int mProgressBarColor;
    private int[] mProgressBarPadding;

    private int[] mTimeStampMargin;
    private float mTimeStampTextSize;
    private int mTimeStampTextColor;
    private Drawable mTimeStampBackground;
    private int mTimeStampPosition;


    public MessageListAdapter(List<Message> messages, Context context) {
        mMessages = messages;
        mContext = context;
        mMessages = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_ITEM) {
            MessageBalloon balloon = new MessageBalloon(mContext);
            MessageHolder messagesHolder = new MessageHolder(balloon, mThisBalloonBackground, mThisBalloonTextColor,
                    mThatBalloonBackground, mThatBalloonTextColor, mBalloonTextSize, mBalloonPadding, mBalloonMargin, mTimeStampMode, mTimeStampGravity, mBalloonLateralMargin);
            if (mTimeStampMode == MessagesWindow.INSIDE_BALLOON_TOP || mTimeStampMode == MessagesWindow.INSIDE_BALLOON_BOTTOM) {
                TimeStamp timeStamp = new TimeStamp(mContext);
                timeStamp.setMargins(mTimeStampMargin);
                timeStamp.setTextSize(mTimeStampTextSize);
                timeStamp.setTextColor(mTimeStampTextColor);
                timeStamp.setBackground(mTimeStampBackground);
                messagesHolder.setTimeStamp(timeStamp);
            }
            return messagesHolder;

        } else if (viewType == TYPE_SPINNER) {
            mProgressBar = new ProgressBar(mContext, null, android.R.attr.progressBarStyleSmall);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            mProgressBar.setLayoutParams(params);

            return new ProgressBarSpinnerHolder(mProgressBar, mProgressBarColor, mProgressBarPadding);
        } else if (viewType == TYPE_TIME_STAMP) {
            TimeStampHolder timeStampHolder = new TimeStampHolder(new TimeStamp(mContext), mTimeStampMargin, mTimeStampTextSize,
                    mTimeStampTextColor, mTimeStampBackground, mTimeStampPosition);
            return timeStampHolder;
        }
        return null;
    }

    public void setData(List<Message> messages) {
        mMessages = messages;
    }

    public void addMoreData(List<Message> messages) {
        mMessages.addAll(0, messages);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MessageHolder) {
            Message message = mMessages.get(position - (mHasMoreToLoad ? 1 : 0));
            ((MessageHolder) holder).setup(message);
        } else if (holder instanceof TimeStampHolder) {
            Message message = mMessages.get(position - (mHasMoreToLoad ? 1 : 0));
            ((TimeStampHolder) holder).setup(message);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mHasMoreToLoad && position == 0)
            return TYPE_SPINNER;

        if (mMessages.get(position).getType() == Message.TIME_STAMP_TYPE) {
            return TYPE_TIME_STAMP;
        }

        return TYPE_ITEM;
    }


    @Override
    public int getItemCount() {
        return mMessages.size() + (mHasMoreToLoad ? 1 : 0);
    }

    public void addMessage(Message message) {
        mMessages.add(message);
        notifyDataSetChanged();
    }

    public void setHasMoreToLoad(boolean hasMoreToLoad) {
        mHasMoreToLoad = hasMoreToLoad;
    }

    public ProgressBar getProgressBar() {
        return mProgressBar;
    }


    /**
     * CUSTOMIZATION
     */

    public void setBalloonPadding(int left, int top, int right, int bottom) {
        mBalloonPadding = new int[]{left, top, right, bottom};
    }

    public void setBalloonMargin(int left, int top, int right, int bottom) {
        mBalloonMargin = new int[]{left, top, right, bottom};
    }

    public void setThisBalloonBackground(Drawable thisBalloonBackground) {
        mThisBalloonBackground = thisBalloonBackground;
    }

    public void setThisBalloonTextColor(int thisBalloonTextColor) {
        mThisBalloonTextColor = thisBalloonTextColor;
    }

    public void setThatBalloonBackground(Drawable thatBalloonBackground) {
        mThatBalloonBackground = thatBalloonBackground;
    }

    public void setThatBalloonTextColor(int thatBalloonTextColor) {
        mThatBalloonTextColor = thatBalloonTextColor;
    }

    public void setBalloonTextSize(float balloonTextSize) {
        mBalloonTextSize = balloonTextSize;
    }

    public void setProgressBarColor(Integer progressBarColor) {
        mProgressBarColor = progressBarColor;
    }

    public void setProgressBarPadding(int left, int top, int right, int bottom) {
        mProgressBarPadding = new int[]{left, top, right, bottom};
    }

    public void setProgressBarColor(int progressBarColor) {
        mProgressBarColor = progressBarColor;
    }

    public void setTimeStampMargin(int left, int top, int right, int bottom) {
        mTimeStampMargin = new int[]{left, top, right, bottom};
    }

    public void setTimeStampTextSize(float timeStampTextSize) {
        mTimeStampTextSize = timeStampTextSize;
    }

    public void setTimeStampTextColor(int timeStampTextColor) {
        mTimeStampTextColor = timeStampTextColor;
    }

    public void setTimeStampBackground(Drawable timeStampBackground) {
        mTimeStampBackground = timeStampBackground;
    }

    public void setTimeStampGravity(int timeStampGravity) {
        mTimeStampGravity = timeStampGravity;
    }

    public void setTimeStampMode(int timeStampMode) {
        mTimeStampMode = timeStampMode;
    }

    public void setTimeStampPosition(int timeStampPosition) {
        mTimeStampPosition = timeStampPosition;
    }

    public void setBalloonLateralMargin(int balloonLateralMargin) {
        mBalloonLateralMargin = balloonLateralMargin;
    }
}
