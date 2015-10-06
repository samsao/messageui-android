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
    private TimeStamp mTimeStamp;
    private int mTimeStampMode;
    private Integer mTimeStampGravity;
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

            setupTimeStampGravity(mMessage.getSide());

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

    public void setTimeStampMode(int timeStampMode) {
        mTimeStampMode = timeStampMode;
    }

    public void setTimeStamp(TimeStamp timeStamp) {
        mTimeStamp = timeStamp;
        if (mTimeStamp != null) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            mTimeStamp.setLayoutParams(params);
            if (mTimeStampMode == MessagesWindow.INSIDE_BALLOON_BOTTOM) {
                mBalloonFrame.addView(mTimeStamp);
            } else if (mTimeStampMode == MessagesWindow.INSIDE_BALLOON_TOP) {
                mBalloonFrame.addView(mTimeStamp, 0);
            }
            if (mTimeStampGravity != null) {
                setTimeStampGravity(mTimeStampGravity);
            }
        }
    }

    public void setTimeStampGravity(int gravity) {
        mTimeStampGravity = gravity;
    }

    private void setupTimeStampGravity(int side) {
        if (mTimeStamp != null && mTimeStampGravity != null) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mTimeStamp.getLayoutParams();
            switch (mTimeStampGravity) {
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
