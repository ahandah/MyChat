package com.ahah.lz.mychat.message;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ahah.lz.mychat.R;
import com.ahah.lz.mychat.common.BaseFragment;
import com.ahah.lz.mychat.common.Global;
import com.ahah.lz.mychat.common.ImageLoadTool;
import com.ahah.lz.mychat.model.AddFriend;
import com.ahah.lz.mychat.widget.CircleImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.wilddog.client.ChildEventListener;
import com.wilddog.client.DataSnapshot;
import com.wilddog.client.SyncError;
import com.wilddog.client.SyncReference;
import com.wilddog.client.WilddogSync;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessageFragment extends BaseFragment {

    private RecyclerView msgRecyclerView;
    private MsgAdapter adapter;
    protected ArrayList<ChatRoom> msgData = new ArrayList<>();
    private String HOST_MESSAGE = Global.HOST + Global.MESSAGE;
    private String TAG_MESSAGE = "TAG_MESSAGE";
    private SyncReference mAddFriendMsg ;
    private ChildEventListener mListener;
    private Class<AddFriend> mModelClass;

    public MessageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        mAddFriendMsg = WilddogSync.getInstance().getReference().child("com_mychat_addfriend").child("addfriend"+Global.Account.id);
        System.out.println("addfriend"+Global.Account.id);
        mListener = mAddFriendMsg.limitToLast(50).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                System.out.println("Message---onChildAdded"+dataSnapshot.getKey());
                AddFriend addFriend = (AddFriend) dataSnapshot.getValue(AddFriend.class);
                System.out.println("addfrien----"+Global.Account.id+addFriend.getFriend());
                System.out.println("addfrien----"+Global.Account.id+dataSnapshot.getValue());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                System.out.println(dataSnapshot.toString());
                AddFriend addFriend = (AddFriend) dataSnapshot.getValue();
                System.out.println("addfrien----"+Global.Account.id+addFriend.getFriend());
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(SyncError syncError) {
                System.out.println("------"+syncError);
            }
        });
//        getNetwork(HOST_MESSAGE , TAG_MESSAGE);
        return view;
    }

    private View initMessageFragment(View view) {
//        msgRecyclerView = (RecyclerView) view.findViewById(R.id.messageList);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext() , LinearLayoutManager.VERTICAL , false);
        msgRecyclerView.setLayoutManager(manager);
        adapter = new MsgAdapter();
//        adapter.addData();
//        adapter.mData = msgData;
        msgRecyclerView.setAdapter(adapter);
        ImageLoad();
        return view;
    }

    //使用默认图片读取配置
    public void ImageLoad(){
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(getContext());
        ImageLoader.getInstance().init(configuration);
    }

    public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.MsgHolder>{

        private ArrayList<ChatRoom> mData = new ArrayList<ChatRoom>();

//        public void addData(){
//            mData.add(new ChatRoom());
//            mData.add(new ChatRoom());
//            mData.add(new ChatRoom());
//        }

        //获得用户头像图面
        private ImageLoadTool imageLoadTool = new ImageLoadTool();
        protected void iconfromNetwork(ImageView view, String url) {
            imageLoadTool.loadImage(view, url);
        }

        @Override
        public MsgHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MsgHolder(LayoutInflater.from(getContext()).inflate(R.layout.message_item , parent , false));
        }

        @Override
        public void onBindViewHolder(MsgHolder holder, final int position) {

            MsgHolder.ViewHolder bindHolder = holder.holder;

            bindHolder.messageItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("onclick--no--"+position);
                }
            });

            bindHolder.chatRmName.setText(mData.get(position).roomName);
            iconfromNetwork(bindHolder.chatRmIcon , Global.HOST+"icon/"+mData.get(position).icon);

        }

        @Override
        public int getItemCount() {
            return mData != null ? mData.size() : 0 ;
//            return 1;
        }

        public class MsgHolder extends RecyclerView.ViewHolder{

            ViewHolder holder = new ViewHolder();
            public MsgHolder(View itemView) {
                super(itemView);

                holder.messageItem = itemView.findViewById(R.id.messageItem);
                holder.chatRmIcon = (CircleImageView) itemView.findViewById(R.id.chatRmIcon);
                holder.chatRmName = (TextView) itemView.findViewById(R.id.chatRmName);
                holder.chatMessage = (TextView) itemView.findViewById(R.id.chatMessage);
            }

            class ViewHolder{

                View messageItem;
                CircleImageView chatRmIcon;
                TextView chatRmName;
                TextView chatMessage;
            }
        }
    }


    @Override
    public void parseJson(int code, JSONObject respanse, String tag) throws JSONException {

        if (code == 2){
            if (tag.equals(TAG_MESSAGE)){
                JSONArray jsonArray = respanse.getJSONArray("data");
                for (int i = 0 ; i < jsonArray.length() ; i ++){
                    ChatRoom chatRoom = new ChatRoom(jsonArray.getJSONObject(i));
//                    msgData.add(chatRoom);
                    adapter.mData.add(chatRoom);
                    System.out.println("parseJson--msgData--"+adapter.mData.size());
                }

                adapter.notifyDataSetChanged();
            }
        }
    }


}
