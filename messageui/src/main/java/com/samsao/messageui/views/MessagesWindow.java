package com.samsao.messageui.views;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.samsao.messageui.R;
import com.samsao.messageui.adapters.MessageListAdapter;
import com.samsao.messageui.interfaces.OnAskToLoadMoreCallback;
import com.samsao.messageui.models.Message;
import com.samsao.messageui.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lcampos on 2015-09-21.
 */
public class MessagesWindow extends RelativeLayout {

    /**
     * Timestamp mode
     */
    public static final int NONE = 0;
    public static final int ALWAYS_AFTER_BALLOON = 1;
    public static final int INSIDE_BALLOON_TOP = 2;
    public static final int INSIDE_BALLOON_BOTTOM = 3;

    /**
     * Gravity types for the timestamp
     */
    public static final int TIMESTAMP_INSIDE_BALLOON_GRAVITY_LEFT = 0;
    public static final int TIMESTAMP_INSIDE_BALLOON_GRAVITY_RIGHT = 1;
    public static final int TIMESTAMP_INSIDE_BALLOON_GRAVITY_CENTER = 2;
    public static final int TIMESTAMP_INSIDE_BALLOON_GRAVITY_CORNER = 3;

    /**
     * Timestamp position on the that view
     */
    public static final int TIMESTAMP_OUTSIDE_GRAVITY_CORNER = 0;
    public static final int TIMESTAMP_OUTSIDE_GRAVITY_MIDDLE = 1;

    private MessageListAdapter mMessageAdapter;
    private OnAskToLoadMoreCallback mOnAskToLoadMoreCallback;
    private LinearLayoutManager mLinearLayoutManager;
    private RecyclerView mRecyclerView;
    private View mWriteMessageBox;
    private int mTimeStampMode;

    //default views
    private EditText mTypeTextField;
    private Button mSendTextButton;

    private boolean mIsLoading;

    public MessagesWindow(Context context) {
        super(context);
        init(context, null);
    }

    public MessagesWindow(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MessagesWindow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MessagesWindow(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        inflate(context, R.layout.messages_window_view, this);
        setupMessageBox();
        setupRecyclerView(context);
        setupDefaultCustomization(context);
        setupAttrs(context, attrs);
    }

    private void setupMessageBox() {
        if (mWriteMessageBox == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mWriteMessageBox = inflater.inflate(R.layout.write_message_box_view, null);

            mTypeTextField = (EditText) mWriteMessageBox.findViewById(R.id.message_box_text_field);
            mSendTextButton = (Button) mWriteMessageBox.findViewById(R.id.message_box_button);
            mSendTextButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTypeTextField.getText() != null && !mTypeTextField.getText().toString().trim().isEmpty()) {
                        sendMessage(mTypeTextField.getText().toString().trim());
                        mTypeTextField.setText("");
                    }
                    mMessageAdapter.notifyDataSetChanged();
                    mRecyclerView.invalidate();
                }
            });
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            mWriteMessageBox.setId(Utils.generateViewId());
        } else {
            mWriteMessageBox.setId(View.generateViewId());
        }

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

        if (findViewById(mWriteMessageBox.getId()) == null) {
            addView(mWriteMessageBox);
        }

        mWriteMessageBox.setLayoutParams(params);
        if (mRecyclerView != null) {
            ((RelativeLayout.LayoutParams) mRecyclerView.getLayoutParams()).addRule(ABOVE, mWriteMessageBox.getId());
        }
    }

    private void setupRecyclerView(Context context) {
        mRecyclerView = (RecyclerView) findViewById(R.id.messages_window_recycler_view);
        mLinearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mMessageAdapter = new MessageListAdapter(new ArrayList<Message>(), context);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mMessageAdapter);
        ((RelativeLayout.LayoutParams) mRecyclerView.getLayoutParams()).addRule(ABOVE, mWriteMessageBox.getId());
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int pastVisibleItems = mLinearLayoutManager.findFirstVisibleItemPosition();
                if (pastVisibleItems == 0) {
                    if (isScrollable() && mOnAskToLoadMoreCallback != null && !mIsLoading) {
                        mIsLoading = true;
                        mOnAskToLoadMoreCallback.onAskToLoadMore();
                    }
                } else {
                    mIsLoading = false;
                    mMessageAdapter.setHasMoreToLoad(mOnAskToLoadMoreCallback != null);
                }
            }
        });
    }


    public void setupAttrs(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MessageUI, 0, 0);
            try {
                int thisBalloonBackgroundResource = a.getResourceId(R.styleable.MessageUI_thisBalloonBackground, 0);
                if (thisBalloonBackgroundResource != 0) {
                    setThisBalloonBackgroundResource(thisBalloonBackgroundResource);
                }

                int thisBalloonTextColor = a.getColor(R.styleable.MessageUI_thisBalloonTextColor, 0);
                if (thisBalloonTextColor != 0) {
                    setThisBalloonTextColor(thisBalloonTextColor);
                }

                int thatBalloonBackgroundResource = a.getResourceId(R.styleable.MessageUI_thatBalloonBackground, 0);
                if (thatBalloonBackgroundResource != 0) {
                    setThatBalloonBackgroundResource(thatBalloonBackgroundResource);
                }

                int thatBalloonTextColor = a.getColor(R.styleable.MessageUI_thatBalloonTextColor, 0);
                if (thatBalloonTextColor != 0) {
                    setThatBalloonTextColor(thatBalloonTextColor);
                }

                float textSize = a.getDimension(R.styleable.MessageUI_balloonTextSize, 0);
                if (textSize != 0) {
                    setBalloonTextSize(textSize);
                }

                int balloonPadding = (int) a.getDimension(R.styleable.MessageUI_balloonPadding, 0);
                if (balloonPadding != 0) {
                    setBalloonPadding(balloonPadding, balloonPadding, balloonPadding, balloonPadding);
                }

                int balloonMargin = (int) a.getDimension(R.styleable.MessageUI_balloonMargins, 0);
                if (balloonMargin != 0) {
                    setBalloonMargins(balloonMargin, balloonMargin, balloonMargin, balloonMargin);
                }

                int progressBarColor = a.getColor(R.styleable.MessageUI_progressBarColor, 0);
                if (progressBarColor != 0) {
                    setProgressBarColor(progressBarColor);
                }

                int[] attrsArray = new int[]{android.R.attr.background};
                TypedArray ta = context.obtainStyledAttributes(attrs, attrsArray);
                int color = ta.getResourceId(0, 0);
                if (color != 0) {
                    setBackgroundColor(color);
                }

            } finally {
                a.recycle();
            }
        }
    }

    private void setupDefaultCustomization(Context context) {
        setThatBalloonBackgroundResource(R.drawable.balloon_background);
        setThisBalloonBackgroundResource(R.drawable.balloon_background);
        setThatBalloonTextColor(ContextCompat.getColor(getContext(), android.R.color.black));
        setThisBalloonTextColor(ContextCompat.getColor(getContext(), android.R.color.black));
        setBalloonTextSize(context.getResources().getDimension(R.dimen.font_size_medium_large));
        setBalloonMargins((int) getResources().getDimension(R.dimen.double_spacing),
                (int) getResources().getDimension(R.dimen.half_spacing),
                (int) getResources().getDimension(R.dimen.double_spacing),
                (int) getResources().getDimension(R.dimen.half_spacing));
        setBalloonPadding((int) getResources().getDimension(R.dimen.spacing),
                (int) getResources().getDimension(R.dimen.half_spacing),
                (int) getResources().getDimension(R.dimen.spacing),
                (int) getResources().getDimension(R.dimen.half_spacing));
        setBalloonLateralMargin((int) getResources().getDimension(R.dimen.double_spacing));

        setProgressBarColor(ContextCompat.getColor(getContext(), android.R.color.white));
        setProgressBarPadding(0, (int) context.getResources().getDimension(R.dimen.half_spacing), 0, 0);

        setTimeStampBackground(null);
        setTimeStampTextColor(ContextCompat.getColor(getContext(), android.R.color.black));
        setTimeStampMargins((int) getResources().getDimension(R.dimen.double_spacing),
                (int) getResources().getDimension(R.dimen.half_spacing),
                (int) getResources().getDimension(R.dimen.double_spacing),
                (int) getResources().getDimension(R.dimen.half_spacing));

        setTimeStampTextSize((int) context.getResources().getDimension(R.dimen.font_size_medium));

        setBackgroundColor(ContextCompat.getColor(getContext(), android.R.color.holo_blue_dark));
    }

    private boolean isScrollable() {
        return mLinearLayoutManager.getChildCount() < mMessageAdapter.getItemCount();
    }

    private void scrollToBottom() {
        mRecyclerView.smoothScrollToPosition(mMessageAdapter.getItemCount() - 1);
    }


    /******
     * METHODS FOR MANAGING THE CONVERSATION
     ******/

    /**
     * displays a message on the chat
     *
     * @param message
     */
    public void showMessage(Message message) {
        mMessageAdapter.addMessage(message);
        if ((mTimeStampMode == INSIDE_BALLOON_TOP || mTimeStampMode == INSIDE_BALLOON_BOTTOM) && message.getTime() == null) {
            message.setTime(Utils.getCurrentTime(getContext()));
        }

        if (mLinearLayoutManager.findFirstVisibleItemPosition() + mLinearLayoutManager.getChildCount() == mMessageAdapter.getItemCount() - 1 - (isScrollable() ? 1 : 0)
                || message.getSide() == Message.THIS_SIDE) {
            scrollToBottom();
        }

        if (mTimeStampMode == ALWAYS_AFTER_BALLOON) {
            showTimeStamp(message.getSide());
        }
    }

    /**
     * displays a message on your side
     *
     * @param message
     */
    public void sendMessage(String message) {
        showMessage(new Message(message, Message.THIS_SIDE));
    }

    /**
     * displays a message on the other person's side
     *
     * @param message
     */
    public void receiveMessage(String message) {
        showMessage(new Message(message, Message.THAT_SIDE));
    }

    /**
     * callback for when the user reaches the top of the chat and expects to load more messages
     *
     * @param loadMoreCallback
     */
    public void setOnAskToLoadMoreCallback(OnAskToLoadMoreCallback loadMoreCallback) {
        mOnAskToLoadMoreCallback = loadMoreCallback;
    }

    /**
     * loads the messages to be exhibit on the chat (should be called on the first time the user opens the chat)
     *
     * @param messages
     */
    public void loadMessages(List<Message> messages) {
        mMessageAdapter.setData(messages);
        mMessageAdapter.notifyDataSetChanged();
    }

    /**
     * loads more messages to be added on top of previously loaded messages (should be called when OnAskToLoadMoreCallback is triggered)
     *
     * @param messages
     */
    public void loadOldMessages(List<Message> messages) {
        mMessageAdapter.addMoreData(messages);
        mMessageAdapter.notifyDataSetChanged();
        View view = mLinearLayoutManager.findViewByPosition(1); //because position 0 is the progress bar
        int topMargin = 0;
        if (view != null) {
            topMargin = view.getPaddingTop();
        }
        mRecyclerView.scrollToPosition(messages.size() + mLinearLayoutManager.getChildCount() - 1);
        if (mMessageAdapter.getProgressBar() != null) {
            mRecyclerView.scrollBy(mRecyclerView.getScrollX(), -mMessageAdapter.getProgressBar().getMeasuredHeight() - topMargin);
        }
    }

    /**
     * displays a timestamp with the current time
     *
     * @param side of the chat view
     */
    public void showTimeStamp(int side) {
        Message timeStamp = new Message(Message.TIME_STAMP_TYPE, side);
        timeStamp.setTime(Utils.getCurrentTime(getContext()));
        mMessageAdapter.addMessage(timeStamp);
        if (mLinearLayoutManager.findFirstVisibleItemPosition() + mLinearLayoutManager.getChildCount() == mMessageAdapter.getItemCount() - 1 - (isScrollable() ? 1 : 0)) {
            scrollToBottom();
        }
    }


    /**
     * displays a timestamp on the other person's side
     */
    public void receiveTimeStamp() {
        showTimeStamp(Message.THAT_SIDE);
    }

    /**
     * displays a timestamp on your side
     */
    public void sendTimeStamp() {
        showTimeStamp(Message.THIS_SIDE);
    }

    /**
     * sets how the timestamp should be displayed
     * NONE
     * ALWAYS_AFTER_BALLOON
     * INSIDE_BALLOON_TOP
     * INSIDE_BALLOON_BOTTOM
     *
     * @param mode
     */
    public void setTimeStampMode(int mode) {
        mTimeStampMode = mode;
        mMessageAdapter.setTimeStampMode(mode);
    }


    /******
     * METHODS FOR UI CUSTOMIZATION
     ******/

    /**
     * the background of your balloon
     *
     * @param balloonBackground
     */
    public void setThisBalloonBackgroundResource(@DrawableRes int balloonBackground) {
        mMessageAdapter.setThisBalloonBackground(balloonBackground);
    }

    /**
     * the textColor of your balloon
     *
     * @param balloonTextColor
     */
    public void setThisBalloonTextColor(@ColorInt int balloonTextColor) {
        mMessageAdapter.setThisBalloonTextColor(balloonTextColor);
    }

    /**
     * the background of the other person's balloon
     *
     * @param balloonBackground
     */
    public void setThatBalloonBackgroundResource(@DrawableRes int balloonBackground) {
        mMessageAdapter.setThatBalloonBackground(balloonBackground);
    }

    /**
     * the textColor of the other person's balloon
     *
     * @param balloonTextColor
     */
    public void setThatBalloonTextColor(@ColorInt int balloonTextColor) {
        mMessageAdapter.setThatBalloonTextColor(balloonTextColor);
    }

    public void setBalloonTextSize(float balloonTextSize) {
        mMessageAdapter.setBalloonTextSize(balloonTextSize);
    }

    /**
     * sets the padding inside the balloon around the text
     *
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    public void setBalloonPadding(int left, int top, int right, int bottom) {
        mMessageAdapter.setBalloonPadding(left, top, right, bottom);
    }

    /**
     * sets the margins around each balloon
     *
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    public void setBalloonMargins(int left, int top, int right, int bottom) {
        mMessageAdapter.setBalloonMargin(left, top, right, bottom);
    }

    /**
     * the color of the progress bar showed on top when the user requests older messages
     *
     * @param progressBarColor
     */
    public void setProgressBarColor(int progressBarColor) {
        mMessageAdapter.setProgressBarColor(progressBarColor);
    }

    /**
     * the padding around the progress bar showed on top when the user requests older messages
     *
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    public void setProgressBarPadding(int left, int top, int right, int bottom) {
        mMessageAdapter.setProgressBarPadding(left, top, right, bottom);
    }

    public void setTimeStampMargins(int left, int top, int right, int bottom) {
        mMessageAdapter.setTimeStampMargin(left, top, right, bottom);
    }

    public void setTimeStampTextSize(float timeStampTextSize) {
        mMessageAdapter.setTimeStampTextSize(timeStampTextSize);
    }

    public void setTimeStampTextColor(int timeStampTextColor) {
        mMessageAdapter.setTimeStampTextColor(timeStampTextColor);
    }

    public void setTimeStampBackground(Drawable timeStampBackground) {
        mMessageAdapter.setTimeStampBackground(timeStampBackground);
    }

    /**
     * sets the position of the timestamp if the timestamp mode is INSIDE_BALLOON_TOP or INSIDE_BALLOON_BOTTOM
     * TIMESTAMP_INSIDE_BALLOON_GRAVITY_LEFT
     * TIMESTAMP_INSIDE_BALLOON_GRAVITY_RIGHT
     * TIMESTAMP_INSIDE_BALLOON_GRAVITY_CENTER
     * TIMESTAMP_INSIDE_BALLOON_GRAVITY_CORNER
     *
     * @param timeStampGravity
     */
    public void setTimeStampInsideBalloonGravity(int timeStampGravity) {
        mMessageAdapter.setTimeStampGravity(timeStampGravity);
    }

    /**
     * sets the position of the timestamp on the chat view if the timestamp mode is not INSIDE_BALLOON_TOP or INSIDE_BALLOON_BOTTOM
     * TIMESTAMP_OUTSIDE_GRAVITY_CORNER
     * TIMESTAMP_OUTSIDE_GRAVITY_MIDDLE
     *
     * @param position
     */
    public void setTimeStampPosition(int position) {
        mMessageAdapter.setTimeStampPosition(position);
    }

    /**
     * sets a margin on the opposite side of balloon, to distinguish the balloon's side
     *
     * @param margin
     */
    public void setBalloonLateralMargin(int margin) {
        mMessageAdapter.setBalloonLateralMargin(margin);
    }

    /**
     * its the view with the text field and send button. By setting your custom view, you'll have to handle the sending messages.
     *
     * @param view
     */
    public void setWritingMessageView(View view) {
        removeView(mWriteMessageBox);
        mWriteMessageBox = view;
        setupMessageBox();
    }

    public View getWritingMessageView() {
        return mWriteMessageBox;
    }


}
