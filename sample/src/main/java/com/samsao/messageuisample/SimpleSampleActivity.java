package com.samsao.messageuisample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.samsao.messageui.views.MessagesWindow;

public class SimpleSampleActivity extends AppCompatActivity {

    private MessagesWindow mMessagesWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);
        mMessagesWindow = (MessagesWindow) findViewById(R.id.simple_messages_window);
        mMessagesWindow.sendMessage("Hey!");
        mMessagesWindow.receiveMessage("Hi!");
    }

}
