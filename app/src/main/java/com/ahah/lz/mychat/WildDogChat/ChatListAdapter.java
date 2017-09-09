package com.ahah.lz.mychat.WildDogChat;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahah.lz.mychat.common.Global;
import com.ahah.lz.mychat.common.ImageLoadTool;
import com.ahah.lz.mychat.model.UserObject;
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
    public UserObject friend;


    //获得用户头像图面
    private ImageLoadTool imageLoadTool = new ImageLoadTool();
    protected void iconfromNetwork(ImageView view, String url) {
        imageLoadTool.loadImage(view, url);
    }

    public ChatListAdapter(Query ref, Activity activity, UserObject friend) {
        super(ref, Chat.class, activity);
        this.friend = friend;
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
                populateView(view, model , mModels.get(i).getAuthor());
                return view;

            case 2:
                 view = mInflater.inflate(R.layout.chat_item_left , viewGroup , false);
                 model = mModels.get(i);
                // Call out to subclass to marshall this model into the provided view
                populateView(view, model , friend.name);
                return view;
        }

        return null;
    }

    @Override
    public int getItemViewType(int position) {
        System.out.println("--"+mModels.get(position).getAuthor()+"---"+ Global.Account.name);
        return mModels.get(position).getAuthor().equals(Global.Account.name) ? 1 : 2;
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
    protected void populateView(View view, Chat chat , String author) {
        // Map a Chat object to an entry in our listview
        TextView chatTv = (TextView) view.findViewById(R.id.chatTV);
        chatTv.setText(chat.getMessage());

        ImageView Icon = (ImageView) view.findViewById(R.id.userIcon);
        String iconUrl = "";
        if (author.equals(Global.Account.name)){
            iconUrl = Global.Account.icon;
        } else {
            iconUrl = friend.icon;
        }
        iconfromNetwork( Icon , Global.HOST+"icon/"+iconUrl);
    }
}
