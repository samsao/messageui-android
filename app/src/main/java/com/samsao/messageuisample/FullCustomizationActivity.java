package com.samsao.messageuisample;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.samsao.messageui.interfaces.OnAskToLoadMoreCallback;
import com.samsao.messageui.models.Message;
import com.samsao.messageui.views.MessagesWindow;

import java.util.ArrayList;
import java.util.List;

public class FullCustomizationActivity extends AppCompatActivity {

    private MessagesWindow mMessagesWindow;
    List<Message> mMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_customization);

        mMessages = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Message message = new Message("first load ", Message.THIS_SIDE);
            mMessages.add(message);
        }

        Message message = new Message("last", Message.THIS_SIDE);
        mMessages.add(message);


        mMessagesWindow = (MessagesWindow)findViewById(R.id.simple_messages_window);
        mMessagesWindow.receiveMessage("Just testing! =)");
        mMessagesWindow.sendMessage("Cool!");
        mMessagesWindow.setOnAskToLoadMoreCallback(new OnAskToLoadMoreCallback() {
            @Override
            public void onAskToLoadMore() {
                mMessagesWindow.setOnAskToLoadMoreCallback(null);
                mMessages = new ArrayList<>();
                Message m = new Message("first ", Message.THIS_SIDE);
                mMessages.add(m);
                for (int i = 0; i < 18; i++) {
                    Message message = new Message("yo ", Message.THIS_SIDE);
                    mMessages.add(message);
                }
                m = new Message("last ", Message.THIS_SIDE);
                mMessages.add(m);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mMessagesWindow.loadOldMessages(mMessages);
                    }
                }, 1000);
            }
        });
    }

}
