package com.ahah.lz.mychat.WildDogChat;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wilddog.client.Query;
import com.ahah.lz.mychat.R;

/**
 * @author Jeen
 * @since 9/16/15
 *
 * This class is an example of how to use WilddogListAdapter. It uses the <code>Chat</code> class to encapsulate the
 * data for each individual chat message
 */
public class ChatListAdapter extends WilddogListAdapter<Chat> {

    // The mUsername for this client. We use this to indicate which messages originated from this user
    public String mUsername;

    public ChatListAdapter(Query ref, Activity activity, String mUsername) {
        super(ref, Chat.class, activity);
        this.mUsername = mUsername;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Chat model;
        System.out.println("-------"+getItemViewType(i)+"---"+mModels.get(i).getMessage());
        switch (getItemViewType(i)){
            case 1:
                 view = mInflater.inflate(R.layout.chat_item_right , viewGroup , false);
                 model = mModels.get(i);
                // Call out to subclass to marshall this model into the provided view
                populateView(view, model);
                return view;

            case 2:
                 view = mInflater.inflate(R.layout.chat_item_left , viewGroup , false);
                 model = mModels.get(i);
                // Call out to subclass to marshall this model into the provided view
                populateView(view, model);
                return view;
        }

        return null;
    }

    @Override
    public int getItemViewType(int position) {
        System.out.println("--"+mModels.get(position).getAuthor()+"---"+mUsername);
        return mModels.get(position).getAuthor().equals(mUsername) ? 1 : 2;
    }

    /**
     * Bind an instance of the <code>Chat</code> class to our view. This method is called by <code>WilddogListAdapter</code>
     * when there is a data change, and we are given an instance of a View that corresponds to the layout that we passed
     * to the constructor, as well as a single <code>Chat</code> instance that represents the current data to bind.
     *
     * @param view A view instance corresponding to the layout we passed to the constructor.
     * @param chat An instance representing the current state of a chat message
     */
    @Override
    protected void populateView(View view, Chat chat) {
        // Map a Chat object to an entry in our listview
//        String author = chat.getAuthor();
//        TextView authorText = (TextView) view.findViewById(R.id.chatTV);
//        authorText.setText(author);
//        // If the message was sent by this user, color it differently
//        if (author != null && author.equals(mUsername)) {
//            authorText.setTextColor(Color.RED);
//        } else {
//            authorText.setTextColor(Color.BLUE);
//        }
        ((TextView) view.findViewById(R.id.chatTV)).setText(chat.getMessage());
    }
}
