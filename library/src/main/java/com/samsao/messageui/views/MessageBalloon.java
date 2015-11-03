package com.samsao.messageui.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.samsao.messageui.models.Message;

/**
 * Created by lcampos on 2015-09-21.
 */
public class MessageBalloon extends FrameLayout {



    private Message mMessage;
    private LinearLayout mBalloonFrame;
    private TextView mMessageTextView;
    private Timestamp mTimestamp;
    private int mTimestampMode;
    private Integer mTimestampGravity;
    private int mLateralMargin;


    public MessageBalloon(Context context) {
        super(context);
        init(context);
    }

    public MessageBalloon(Context context, Message message) {
        super(context);
        mMessage = message;
        init(context);
    }

    public MessageBalloon(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MessageBalloon(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MessageBalloon(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));


        mBalloonFrame = new LinearLayout(context);
        mBalloonFrame.setOrientation(LinearLayout.VERTICAL);
        addView(mBalloonFrame);

        mMessageTextView = new TextView(context);
        mMessageTextView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        mBalloonFrame.addView(mMessageTextView);

        mBalloonFrame.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        setupMessage();
    }

    private void setupMessage() {
        if (mMessage != null) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);

            if (mMessage.getSide() == Message.THIS_SIDE) {
                params.gravity = Gravity.RIGHT;
                params.setMargins(mLateralMargin, 0, 0, 0);
            } else {
                params.gravity = Gravity.LEFT;
                params.setMargins(0, 0, mLateralMargin, 0);
            }

            setupTimestampGravity(mMessage.getSide());

            mMessageTextView.setText(mMessage.getText());
            mBalloonFrame.setLayoutParams(params);
        }
    }

    public Message getMessage() {
        return mMessage;
    }

    public void setMessage(Message message) {
        mMessage = message;
        setupMessage();
    }

    public void setBalloonTextSize(float balloonTextSize) {
        mMessageTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, balloonTextSize);
    }

    public void setBalloonTextColor(@ColorInt int balloonTextColor) {
        mMessageTextView.setTextColor(balloonTextColor);
    }

    public void setBalloonPadding(int[] balloonPadding) {
        if (balloonPadding != null) {
            mBalloonFrame.setPadding(balloonPadding[0], balloonPadding[1], balloonPadding[2], balloonPadding[3]);
        }
    }

    public void setBalloonMargin(int[] balloonMargin) {
        if (balloonMargin != null) {
            setPadding(balloonMargin[0], balloonMargin[1], balloonMargin[2], balloonMargin[3]);
        }
    }

    public void setBalloonBackground(@DrawableRes int balloonBackground) {
        mBalloonFrame.setBackgroundResource(balloonBackground);
    }

    public void setTimestampMode(int timestampMode) {
        mTimestampMode = timestampMode;
    }

    public void setTimestamp(Timestamp timestamp) {
        mTimestamp = timestamp;
        if (mTimestamp != null) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            mTimestamp.setLayoutParams(params);
            if (mTimestampMode == MessagesWindow.INSIDE_BALLOON_BOTTOM) {
                mBalloonFrame.addView(mTimestamp);
            } else if (mTimestampMode == MessagesWindow.INSIDE_BALLOON_TOP) {
                mBalloonFrame.addView(mTimestamp, 0);
            }
            if (mTimestampGravity != null) {
                setTimestampGravity(mTimestampGravity);
            }
        }
    }

    public void setTimestampGravity(int gravity) {
        mTimestampGravity = gravity;
    }

    private void setupTimestampGravity(int side) {
        if (mTimestamp != null && mTimestampGravity != null) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mTimestamp.getLayoutParams();
            switch (mTimestampGravity) {
                case MessagesWindow.TIMESTAMP_INSIDE_BALLOON_GRAVITY_LEFT:
                    params.gravity = Gravity.LEFT;
                    break;
                case MessagesWindow.TIMESTAMP_INSIDE_BALLOON_GRAVITY_RIGHT:
                    params.gravity = Gravity.RIGHT;
                    break;
                case MessagesWindow.TIMESTAMP_INSIDE_BALLOON_GRAVITY_CORNER:
                    if (side == Message.THIS_SIDE) {
                        params.gravity = Gravity.RIGHT;
                    } else {
                        params.gravity = Gravity.LEFT;
                    }
                    break;
                case MessagesWindow.TIMESTAMP_INSIDE_BALLOON_GRAVITY_CENTER:
                    if (side == Message.THIS_SIDE) {
                        params.gravity = Gravity.LEFT;
                    } else {
                        params.gravity = Gravity.RIGHT;
                    }
                    break;
            }
        }
    }

    public void setLateralMargin(int lateralMargin) {
        mLateralMargin = lateralMargin;
    }
}
