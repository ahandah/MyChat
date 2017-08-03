package com.ahah.lz.mychat.message;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ahah.lz.mychat.R;
import com.ahah.lz.mychat.common.Global;
import com.ahah.lz.mychat.widget.CircleImageView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessageFragment extends Fragment {

    private RecyclerView msgRecyclerView;
    private MsgAdapter adapter;

    public MessageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_message, container, false);
//        initMessageFragment(view);
        return initMessageFragment(view);
    }

    private View initMessageFragment(View view) {
        System.out.println("init--------");
        msgRecyclerView = (RecyclerView) view.findViewById(R.id.messageList);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext() , LinearLayoutManager.VERTICAL , false);
        msgRecyclerView.setLayoutManager(manager);
        adapter = new MsgAdapter();
        adapter.addData();
        msgRecyclerView.setAdapter(adapter);
        return view;
    }

    public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.MsgHolder>{

        private ArrayList<ChatRoom> mData = new ArrayList<ChatRoom>();

        public void addData(){
            mData.add(new ChatRoom());
            mData.add(new ChatRoom());
            mData.add(new ChatRoom());
            System.out.println("init--addData--"+mData.size());
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
        }

        @Override
        public int getItemCount() {
            System.out.println("init--message--fragment--"+mData.size());
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

}
