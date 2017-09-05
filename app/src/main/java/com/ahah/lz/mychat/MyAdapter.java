package com.ahah.lz.mychat;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahah.lz.mychat.common.Global;
import com.ahah.lz.mychat.message.ChatRoom;
import com.ahah.lz.mychat.common.ImageLoadTool;
import com.ahah.lz.mychat.model.ChatModel;
import com.ahah.lz.mychat.widget.CircleImageView;

import java.util.ArrayList;

/**
 * Created by 40660 on 2017/7/12.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder>{

    public ArrayList<ChatModel> mData = new ArrayList<ChatModel>();

    public void addData(ChatModel chat){
        mData.add(chat);
        ChatRoom.sendMessage(chat);
        notifyDataSetChanged();
    }

    //获得用户头像图面
    private ImageLoadTool imageLoadTool = new ImageLoadTool();
    protected void iconfromNetwork(ImageView view, String url) {
        imageLoadTool.loadImage(view, url);
    }

    public MyAdapter(ArrayList<ChatModel> list){
//        System.out.println("---MyAdapter---"+list.get(0).user.name);
        mData.clear();
        if (list != null && list.size() > 0) {
            if (mData != null && list != null) {
                System.out.println("-----1111------");
                mData.addAll(list);
                notifyItemRangeChanged(mData.size(),list.size());
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType){
            case 1:
                return new MyHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_right, parent , false));
            case 2:
                return new MyHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_left, parent , false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

        MyHolder.ViewHolder bindHolder = holder.holder;
        holder.setData(mData.get(position).content);
        iconfromNetwork(bindHolder.userIcon , Global.HOST+"icon/"+mData.get(position).user.icon);
    }

    @Override
    public int getItemViewType(int position) {

        if (mData.get(position).user.name.equals(Global.Account.name)){
            return 1;
        }else {
            return 2;
        }
//        return mData.get(position).user.id;
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }


    public class MyHolder extends RecyclerView.ViewHolder{


        class ViewHolder {

            int i;
            CircleImageView userIcon;
            TextView message;
        }

        ViewHolder holder = new ViewHolder();

        public MyHolder(View itemView) {
            super(itemView);

            holder.userIcon = (CircleImageView) itemView.findViewById(R.id.userIcon);
            holder.message = (TextView) itemView.findViewById(R.id.chatTV);

        }

        public void setData(String data){

            holder.message.setText(data);

        }

    }

}




