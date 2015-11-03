package com.samsao.messageuisample;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.samsao.messageui.interfaces.OnAskToLoadMoreCallback;
import com.samsao.messageui.models.Message;
import com.samsao.messageui.views.MessagesWindow;

import java.util.ArrayList;
import java.util.List;

/**
 * Not ready yet!
 */
public class MoreCustomizationSampleActivity extends AppCompatActivity {

    private static final int MESSAGE_COUNT = 20;
    private MessagesWindow mMessagesWindow;
    List<Message> mMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customization);

        mMessages = getMessages("");

        mMessagesWindow = (MessagesWindow) findViewById(R.id.customized_messages_window);
        mMessagesWindow.setBackgroundColor(ContextCompat.getColor(this, android.R.color.darker_gray));
        mMessagesWindow.setTimestampMode(MessagesWindow.INSIDE_BALLOON_TOP);
        mMessagesWindow.setTimestampTextColor(ContextCompat.getColor(this, android.R.color.black));
        mMessagesWindow.setTimestampInsideBalloonGravity(MessagesWindow.TIMESTAMP_INSIDE_BALLOON_GRAVITY_CORNER);
        mMessagesWindow.setTimestampMargins(0, 0, 0, 0);
        mMessagesWindow.loadMessages(mMessages);

        // use this callback to know when the user scrolled through
        // all of his messages so you can load more
        mMessagesWindow.setOnAskToLoadMoreCallback(new OnAskToLoadMoreCallback() {
            @Override
            public void onAskToLoadMore() {

                // simulating a slow data fetch
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mMessages = getMessages("old ");
                        mMessagesWindow.loadOldMessages(mMessages);

                        // be sure to call this when you don't want0
                        // the user to see the progress indicator anymore
                        mMessagesWindow.removeOnAskToLoadMoreCallback();
                    }
                }, 4000);
            }
        });
    }

    private List<Message> getMessages(String identification) {
        List<Message> messages = new ArrayList<>();
        for (int i = 0; i < MESSAGE_COUNT; i++) {
            Message message = new Message(identification + "message " + i, i % 3 == 0 ? Message.THIS_SIDE : Message.THAT_SIDE);
            message.setTime("7:20 pm");
            messages.add(message);
        }
        return messages;
    }

}
