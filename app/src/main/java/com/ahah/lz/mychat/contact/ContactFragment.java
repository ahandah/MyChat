package com.ahah.lz.mychat.contact;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahah.lz.mychat.R;
import com.ahah.lz.mychat.common.BaseFragment;
import com.ahah.lz.mychat.common.Global;
import com.ahah.lz.mychat.common.ImageLoadTool;
import com.ahah.lz.mychat.message.ChatRoom;
import com.ahah.lz.mychat.model.UserObject;
import com.ahah.lz.mychat.widget.CircleImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ContactFragment extends BaseFragment {

    private TextView tv;
    private RecyclerView ctRecyclerView;
    private MsgAdapter adapter;
    protected ArrayList<UserObject> ctData = new ArrayList<>();
    private String HOST_FRIENDS = Global.HOST + Global.FRIENDS;
    private String TAG_CONTACT = "TAG_CONTACT";

    public ContactFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        getNetwork(HOST_FRIENDS , TAG_CONTACT);
        return initContactFragment(view);
//        return view;
    }

    private View initContactFragment(View view) {
        ctRecyclerView = (RecyclerView) view.findViewById(R.id.contactList);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext() , LinearLayoutManager.VERTICAL , false);
        ctRecyclerView.setLayoutManager(manager);
        adapter = new MsgAdapter();
//        adapter.addData();
//        adapter.mData = msgData;
        ctRecyclerView.setAdapter(adapter);
        ImageLoad();
        return view;
    }

    //使用默认图片读取配置
    public void ImageLoad(){
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(getContext());
        ImageLoader.getInstance().init(configuration);
    }

    public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.MsgHolder>{

        private ArrayList<UserObject> mData = new ArrayList<UserObject>();

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
            return new MsgHolder(LayoutInflater.from(getContext()).inflate(R.layout.contact_item , parent , false));
        }

        @Override
        public void onBindViewHolder(MsgHolder holder, final int position) {

            MsgHolder.ViewHolder bindHolder = holder.holder;

            bindHolder.ctItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("onclick--no--"+position);
                }
            });

            bindHolder.ctName.setText(mData.get(position).name);
            iconfromNetwork(bindHolder.ctIcon , Global.HOST+"icon/"+mData.get(position).icon);

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

                holder.ctItem = itemView.findViewById(R.id.ctItem);
                holder.ctIcon = (CircleImageView) itemView.findViewById(R.id.ctIcon);
                holder.ctName = (TextView) itemView.findViewById(R.id.ctName);
                holder.ctTag = (TextView) itemView.findViewById(R.id.ctTag);
            }

            class ViewHolder{

                View ctItem;
                CircleImageView ctIcon;
                TextView ctName;
                TextView ctTag;
            }
        }
    }


    @Override
    public void parseJson(int code, JSONObject respanse, String tag) throws JSONException {

        if (code == 2){
            if (tag.equals(TAG_CONTACT)){
                JSONArray jsonArray = respanse.getJSONArray("data");
                for (int i = 0 ; i < jsonArray.length() ; i ++){
                    UserObject userObject = new UserObject(jsonArray.getJSONObject(i));
//                    msgData.add(chatRoom);
                    adapter.mData.add(userObject);
                    System.out.println("parseJson--msgData--"+adapter.mData.size());
                }

                adapter.notifyDataSetChanged();
            }
        }
    }

}
