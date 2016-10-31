# MessageUI
A library to create the UI for chatting apps for Android.

![Message](/assets/MessageSample.png)

## Supported Android versions
3.0.x and higher

## Adding to your project

Add the dependency to your build.gradle:

	dependencies {
		compile 'com.github.samsao:messageui:1.0.0'
	}

## Usage
### Adding the chat window
1) Open your layout xml file and add the application namespace to the root element in the XML

~~~xml
xmlns:app="http://schemas.android.com/apk/res-auto"
~~~

2) Add the chat window to your layout

~~~xml
<com.samsao.messageui.views.MessagesWindow
        android:id="@+id/customized_messages_window"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:balloonMargins="20dp"
        app:thatBalloonBackground="@drawable/other_balloon_shape"
        app:thatBalloonTextColor="@android:color/white"
        app:thisBalloonBackground="@drawable/my_balloon_shape"
        app:thisBalloonTextColor="@android:color/white"/>
~~~
Everything with 'this' refers to the side where your messages are shown. 'that' refers to the side of the other person's messages.

These are the attributes you can add to your MessagesWindow: 

~~~xml
thisBalloonBackground
thatBalloonBackground
thisBalloonTextColor
thatBalloonTextColor
balloonTextSize
balloonPadding
balloonMargins (the spacing between the ballonns)
progressBarColor (the color of the progress indicator to be shown when the user asks for more messages)
~~~

### Display the messages
Display the messages by calling `sendMessage()` or `receiveMessage()` and they will appear on the appropriate sides:

~~~java
MessagesWindow messagesWindow = (MessagesWindow) findViewById(R.id.messages_window);
messagesWindow.sendMessage("Hi!");
messagesWindow.receiveMessage("Hello!");
~~~

### Adding timestamps

You can display the timestamps inside or outside of the message balloon.

#### Outside

Just call `showTimestamp()` with the side where you want to display it. The current time will be displayed according to the device's time format.

~~~java
messagesWindow.showTimestamp(Message.THIS_SIDE);
~~~
![Timestamp outside](/assets/TSOut.png)

Or you can set it to be displayed after every message

~~~java
messagesWindow.setTimestampMode(MessagesWindow.ALWAYS_AFTER_BALLOON);
~~~
![Timestamp after](/assets/TSAfter.png)

#### Inside

Set the timestamp mode to display the timestamp inside the balloons. Then you can adjust its position using `setTimestampInsideBalloonGravity()`.

~~~java
messagesWindow.setTimestampMode(MessagesWindow.INSIDE_BALLOON_TOP);
messagesWindow.setTimestampInsideBalloonGravity(MessagesWindow.TIMESTAMP_INSIDE_BALLOON_GRAVITY_CORNER);
~~~
![Timestamp inside](/assets/TSInside.png)

#### Styling
There are some methods that allow you to style your timestamps.

~~~java
setTimestampMargins()
setTimestampTextSize()
setTimestampTextColor()
setTimestampBackground()
~~~
They will work for all timestamp modes.

### Loading old messages

You can display messages as soon as the user opens the chat by calling `loadMessages()`.

~~~java
List<Message> messages = new ArrayList<>();
Message message = new Message(":)", Message.THIS_SIDE);
message.setTime("7:20 pm");
messages.add(message);
messagesWindow.loadMessages(messages);
~~~

If you don't want to load all the messages at once, you can wait until the user scrolls to the top of the chat to load more. Set a callback to know when the user requests for more. A progress indicator will automatically be shown.

~~~java
mMessagesWindow.setOnAskToLoadMoreCallback(new OnAskToLoadMoreCallback() {
            @Override
            public void onAskToLoadMore() {
                List<Message> messages = getMessages();
                if (messages.isEmpty()) {
                    messagesWindow.removeOnAskToLoadMoreCallback();
                } else {
                    messagesWindow.loadOldMessages(messages);
                }
            }
        });
~~~

If all the messages were already loaded, you can simply call `removeOnAskToLoadMoreCallback` and the progress indicator won't show up anymore.


### Extra customization
The library comes with its own view for typing and sending text, but you can make your own. Just call `setWritingMessageView()` to replace it with your view. You don't have to add any inner views.

## Future improvements
* Support for group chats
* Typing indicator
* User pictures
* Animations
* Balloon grouping



## License
MessageUI is released under the MIT license. See the LICENSE file for details.
