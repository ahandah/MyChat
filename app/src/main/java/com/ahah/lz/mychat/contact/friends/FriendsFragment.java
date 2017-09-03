package com.ahah.lz.mychat.contact.friends;


import android.content.Intent;
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
import com.ahah.lz.mychat.message.ChatActivity;
import com.ahah.lz.mychat.model.Friends;
import com.ahah.lz.mychat.widget.CircleImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FriendsFragment extends BaseFragment {

    private RecyclerView ctRecyclerView;
    private FrAdapter adapter;
    private ArrayList<Friends> thFriends = new ArrayList<>();
    private String HOST_FRIENDS = Global.HOST + Global.FRIENDS;
    private String TAG_FRIENDS = "TAG_FRIENDS";

    public FriendsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_friends, container, false);
        getNetwork(HOST_FRIENDS , TAG_FRIENDS);
        return initContactFragment(view);
    }

        private View initContactFragment(View view) {
        ctRecyclerView = (RecyclerView) view.findViewById(R.id.friendsList);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext() , LinearLayoutManager.VERTICAL , false);
        ctRecyclerView.setLayoutManager(manager);
        adapter = new FrAdapter();
        ctRecyclerView.setAdapter(adapter);
        ImageLoad();
        return view;
    }

    //使用默认图片读取配置
    public void ImageLoad(){
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(getContext());
        ImageLoader.getInstance().init(configuration);
    }

    public class FrAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        private static final int TYPE_PARENT = 0;
        private static final int TYPE_CHILD = 1;

        private ArrayList<Friends> mData = new ArrayList<Friends>();

//      判断是否是title
        private boolean isTitle(int i){

            if (mData.get(i).friendTag == TYPE_PARENT){
                return true;
            }else {
                return false;
            }
        }

        //获得用户头像图面
        private ImageLoadTool imageLoadTool = new ImageLoadTool();
        protected void iconfromNetwork(ImageView view, String url) {
            imageLoadTool.loadImage(view, url);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            switch (viewType){
                case TYPE_PARENT :
                    return new PtHolder(LayoutInflater.from(getContext()).inflate(R.layout.contact_parent_item , parent ,false));

                case TYPE_CHILD :
                    return new FrHolder(LayoutInflater.from(getContext()).inflate(R.layout.contact_item , parent , false));
            }

            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

            switch (holder.getItemViewType()){

                case TYPE_PARENT:
                    PtHolder ptHolder = (PtHolder) holder;
                    ptHolder.holder.parentId.setText(mData.get(position).frName);
                    ptHolder.holder.parentId.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            System.out.println("-----"+adapter.mData.get(position).isExpand);
                            if (!adapter.mData.get(position).isExpand){
                                System.out.println("-----"+adapter.mData.get(position).friends.size());
                                for (int i = adapter.mData.get(position).friends.size() ; i > 0 ; i--){
                                    adapter.mData.add(position + 1 , adapter.mData.get(position).friends.get(i-1));
//                                    插入后数据与UI不一致
//                                    notifyItemInserted(position + 1);
                                    adapter.notifyDataSetChanged();
                                }
                                adapter.mData.get(position).isExpand = true;
                            }else {
                                for (int i = 0 ; i < adapter.mData.get(position).friends.size() ; i++){

                                    adapter.mData.remove(position + 1);
//                                    插入后数据与UI不一致
//                                    notifyItemRemoved(position + 1);
                                    adapter.notifyDataSetChanged();
                                }

                                adapter.mData.get(position).isExpand = false;
                            }
                        }
                    });
                    break;

                case TYPE_CHILD:
                    FrHolder frHolder = (FrHolder) holder;
                    frHolder.holder.ctItem.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            System.out.println("onclick--no--"+position);
                            //在这里传入好友的用户类
                            Intent it = new Intent(getContext() , ChatActivity.class);
                            startActivity(it);
                        }
                    });

                    frHolder.holder.ctName.setText(mData.get(position).name);
                    iconfromNetwork(frHolder.holder.ctIcon , Global.HOST+"icon/"+mData.get(position).icon);
                    break;

            }



        }

        @Override
        public int getItemCount() {
            return mData != null ? mData.size() : 0 ;
//            return 1;
        }

        @Override
        public int getItemViewType(int position) {
            int viewType;
            if (isTitle(position)){
                viewType = TYPE_PARENT;
            }else {
                viewType = TYPE_CHILD;
            }
            return viewType;
        }

        public class PtHolder extends RecyclerView.ViewHolder{

            ViewHolder holder = new ViewHolder();

            public PtHolder(View itemView) {
                super(itemView);

                holder.parentId = (TextView) itemView.findViewById(R.id.parentId);
            }

            class ViewHolder{
                TextView parentId;

            }
        }

        public class FrHolder extends RecyclerView.ViewHolder{

            ViewHolder holder = new ViewHolder();
            public FrHolder(View itemView) {
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

        ArrayList<Friends> jsonFriends = new ArrayList<>();
        Boolean lastNameChange = false;
        String lastFName = null;
        JSONObject jsonObject = null;
        Friends frObject = null;
        if (code == 2){
            if (tag.equals(TAG_FRIENDS)){

                JSONArray jsonArray = respanse.getJSONArray("data");
                for (int i = 0 ; i < jsonArray.length() ; i ++){

                    jsonObject = jsonArray.getJSONObject(i);
                    Friends friend = new Friends(jsonObject);
                    jsonFriends.add(friend);
//                  记录下最后一次的分类名
                    if (i == 0){
                        lastFName = jsonObject.getString("frgroup");
                    }
//                  只有一组数据的情况
                    if (i == jsonArray.length() && !lastNameChange){

                        frObject = new Friends(0 , lastFName , jsonFriends);
                        adapter.mData.add(frObject);
                        thFriends.add(frObject);
                        jsonFriends.clear();
                    }

                    if (!lastFName.equals(jsonObject.getString("frgroup"))){

                        lastNameChange = true;
                        frObject = new Friends(0 , lastFName , jsonFriends);
                        adapter.mData.add(frObject);
                        thFriends.add(frObject);
                        jsonFriends.clear();
                        lastFName = jsonObject.getString("frgroup");
                        System.out.println("--mData.size---"+adapter.mData.size()+
                                "---mData.friends.size----"+adapter.mData.get(0).friends.size());
                    }

                }
                adapter.notifyDataSetChanged();
            }
        }
    }

}
