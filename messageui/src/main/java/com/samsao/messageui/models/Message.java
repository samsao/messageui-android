package com.samsao.messageui.models;

/**
 * Created by lcampos on 2015-09-21.
 */
public class Message {

    public static final int THIS_SIDE = 0;
    public static final int THAT_SIDE = 1;

    public static final int MESSAGE_TYPE = 0;
    public static final int TIME_STAMP_TYPE = 1;

    private int mType = MESSAGE_TYPE;
    private String mText;
    private String mTime;
    private int mSide;

    public Message(int type, int side) {
        mType = type;
        mSide = side;
    }

    public Message(String text, int side) {
        mText = text;
        mSide = side;
    }

    public Message(int type, String text, int side) {
        mType = type;
        mText = text;
        mSide = side;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    public int getSide() {
        return mSide;
    }

    public void setSide(int side) {
        mSide = side;
    }

    public int getType() {
        return mType;
    }

    public void setType(int type) {
        mType = type;
    }

    public String getTime() {
        return mTime;
    }

    public void setTime(String time) {
        mTime = time;
    }
}
