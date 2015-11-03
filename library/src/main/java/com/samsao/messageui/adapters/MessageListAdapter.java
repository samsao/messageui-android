package com.samsao.messageui.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.samsao.messageui.adapters.ViewHolders.MessageHolder;
import com.samsao.messageui.adapters.ViewHolders.ProgressBarSpinnerHolder;
import com.samsao.messageui.adapters.ViewHolders.TimestampHolder;
import com.samsao.messageui.models.Message;
import com.samsao.messageui.views.MessageBalloon;
import com.samsao.messageui.views.MessagesWindow;
import com.samsao.messageui.views.Timestamp;

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
    private boolean mMoreToLoad;

    //Features for customization
    private int mThisBalloonBackground;
    private int mThisBalloonTextColor;
    private int mThatBalloonBackground;
    private int mThatBalloonTextColor;
    private float mBalloonTextSize;
    private int[] mBalloonPadding;
    private int[] mBalloonMargin;
    private int mTimestampMode;
    private int mTimestampGravity;
    private int mBalloonLateralMargin;

    private int mProgressBarColor;
    private int[] mProgressBarPadding;

    private int[] mTimestampMargin;
    private float mTimestampTextSize;
    private int mTimestampTextColor;
    private Drawable mTimestampBackground;
    private int mTimestampPosition;


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
                    mThatBalloonBackground, mThatBalloonTextColor, mBalloonTextSize, mBalloonPadding, mBalloonMargin, mTimestampMode, mTimestampGravity, mBalloonLateralMargin);
            if (mTimestampMode == MessagesWindow.INSIDE_BALLOON_TOP || mTimestampMode == MessagesWindow.INSIDE_BALLOON_BOTTOM) {
                Timestamp timestamp = new Timestamp(mContext);
                timestamp.setMargins(mTimestampMargin);
                timestamp.setTextSize(mTimestampTextSize);
                timestamp.setTextColor(mTimestampTextColor);
                timestamp.setBackground(mTimestampBackground);
                messagesHolder.setTimestamp(timestamp);
            }
            return messagesHolder;

        } else if (viewType == TYPE_SPINNER) {
            mProgressBar = new ProgressBar(mContext, null, android.R.attr.progressBarStyleSmall);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            mProgressBar.setLayoutParams(params);

            return new ProgressBarSpinnerHolder(mProgressBar, mProgressBarColor, mProgressBarPadding);
        } else if (viewType == TYPE_TIME_STAMP) {
            TimestampHolder timestampHolder = new TimestampHolder(new Timestamp(mContext), mTimestampMargin, mTimestampTextSize,
                    mTimestampTextColor, mTimestampBackground, mTimestampPosition);
            return timestampHolder;
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
            Message message = mMessages.get(position - (mMoreToLoad ? 1 : 0));
            ((MessageHolder) holder).setup(message);
        } else if (holder instanceof TimestampHolder) {
            Message message = mMessages.get(position - (mMoreToLoad ? 1 : 0));
            ((TimestampHolder) holder).setup(message);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mMoreToLoad && position == 0) {
            return TYPE_SPINNER;
        }

        if (mMessages.get(position - (mMoreToLoad ? 1 : 0)).getType() == Message.TIME_STAMP_TYPE) {
            return TYPE_TIME_STAMP;
        }

        return TYPE_ITEM;
    }


    @Override
    public int getItemCount() {
        return mMessages.size() + (mMoreToLoad ? 1 : 0);
    }

    public void addMessage(Message message) {
        mMessages.add(message);
        notifyDataSetChanged();
    }

    public void setMoreToLoad(boolean moreToLoad) {
        mMoreToLoad = moreToLoad;
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

    public void setThisBalloonBackground(@DrawableRes int thisBalloonBackground) {
        mThisBalloonBackground = thisBalloonBackground;
    }

    public void setThisBalloonTextColor(int thisBalloonTextColor) {
        mThisBalloonTextColor = thisBalloonTextColor;
    }

    public void setThatBalloonBackground(@DrawableRes int thatBalloonBackground) {
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

    public void setTimestampMargin(int left, int top, int right, int bottom) {
        mTimestampMargin = new int[]{left, top, right, bottom};
    }

    public void setTimestampTextSize(float timestampTextSize) {
        mTimestampTextSize = timestampTextSize;
    }

    public void setTimestampTextColor(int timestampTextColor) {
        mTimestampTextColor = timestampTextColor;
    }

    public void setTimestampBackground(Drawable timestampBackground) {
        mTimestampBackground = timestampBackground;
    }

    public void setTimestampGravity(int timestampGravity) {
        mTimestampGravity = timestampGravity;
    }

    public void setTimestampMode(int timestampMode) {
        mTimestampMode = timestampMode;
    }

    public void setTimestampPosition(int timestampPosition) {
        mTimestampPosition = timestampPosition;
    }

    public void setBalloonLateralMargin(int balloonLateralMargin) {
        mBalloonLateralMargin = balloonLateralMargin;
    }
}
