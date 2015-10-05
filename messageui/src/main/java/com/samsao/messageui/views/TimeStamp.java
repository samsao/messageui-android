package com.samsao.messageui.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.samsao.messageui.R;
import com.samsao.messageui.models.Message;

/**
 * Created by lcampos on 2015-09-29.
 */
public class TimeStamp extends FrameLayout {

    private int mPosition;
    private TextView mTextView;
    private Message mMessage;

    public TimeStamp(Context context, Message message) {
        super(context);
        mMessage = message;
        init(context);
    }

    public TimeStamp(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TimeStamp(Context context) {
        super(context);
        init(context);
    }

    public TimeStamp(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TimeStamp(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        setPadding((int) getResources().getDimension(R.dimen.double_spacing),
                (int) getResources().getDimension(R.dimen.half_spacing),
                (int) getResources().getDimension(R.dimen.double_spacing),
                (int) getResources().getDimension(R.dimen.half_spacing));

        mTextView = new TextView(context);
        mTextView.setTextColor(ContextCompat.getColor(context, android.R.color.black));
        addView(mTextView);
        setupMessage();
    }

    private void setupMessage() {
        if (mMessage != null) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);

            if (mPosition == MessagesWindow.TIMESTAMP_OUTSIDE_GRAVITY_CORNER) {
                if (mMessage.getSide() == Message.THIS_SIDE) {
                    params.gravity = Gravity.RIGHT;
                } else {
                    params.gravity = Gravity.LEFT;
                }
            } else {
                params.gravity = Gravity.CENTER_HORIZONTAL;
            }

            mTextView.setText(mMessage.getTime());
            mTextView.setLayoutParams(params);
        }
    }

    public Message getMessage() {
        return mMessage;
    }

    public void setMessage(Message message) {
        mMessage = message;
        setupMessage();
    }

    public void setMargins(int[] timeStampMargin) {
        setPadding(timeStampMargin[0], timeStampMargin[1], timeStampMargin[2], timeStampMargin[3]);
    }

    public void setTextSize(float timeStampTextSize) {
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, timeStampTextSize);
    }

    public void setTextColor(int timeStampTextColor) {
        mTextView.setTextColor(timeStampTextColor);
    }

    @Override
    public void setBackground(Drawable timeStampBackground) {
        mTextView.setBackground(timeStampBackground);
    }

    public void setPosition(int timeStampPosition) {
        mPosition = timeStampPosition;
    }
}
