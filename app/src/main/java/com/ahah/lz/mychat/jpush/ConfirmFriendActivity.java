package com.ahah.lz.mychat.jpush;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahah.lz.mychat.R;
import com.ahah.lz.mychat.common.Global;
import com.ahah.lz.mychat.common.ImageLoadTool;
import com.ahah.lz.mychat.widget.CircleImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;


public class ConfirmFriendActivity extends AppCompatActivity {

    private RecyclerView fRequestList;
    private fRequestAdapter adapter;


    //使用默认图片读取配置
    public void ImageLoad(){
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(this);
        ImageLoader.getInstance().init(configuration);
    }

    private ImageLoadTool imageLoadTool = new ImageLoadTool();
    protected void iconfromNetwork(ImageView view, String url) {
        imageLoadTool.loadImage(view, url);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_friend);

        System.out.println("confirmFriend-----Gl.fRequest---size--"+ Global.fRequest.size());

        fRequestList = (RecyclerView) findViewById(R.id.friendRequestList);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(ConfirmFriendActivity.this , LinearLayoutManager.VERTICAL , false);
        fRequestList.setLayoutManager(manager);
        adapter = new fRequestAdapter();
        fRequestList.setAdapter(adapter);


    }

    public class fRequestAdapter extends RecyclerView.Adapter<fRequestAdapter.FViewHolder>{


        @Override
        public FViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            FViewHolder viewHolder = new FViewHolder(LayoutInflater.from(ConfirmFriendActivity.this)
                    .inflate(R.layout.add_friend_item , parent , false));
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(FViewHolder bindHolder, final int position) {
            ImageLoad();
            iconfromNetwork(bindHolder.holder.uIcon , Global.HOST+"icon/"+Global.fRequest.get(position).icon);
            bindHolder.holder.uName.setText("您好，我是 "+Global.fRequest.get(position).name);

            bindHolder.holder.accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            bindHolder.holder.refuse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Global.fRequest.remove(position);
                    notifyDataSetChanged();
                }
            });

        }

        @Override
        public int getItemCount() {
            return Global.fRequest != null ? Global.fRequest.size() : 0;
        }

        public class FViewHolder extends RecyclerView.ViewHolder{

            ViewHolder holder = new ViewHolder();
            public FViewHolder(View itemView) {
                super(itemView);
                holder.uIcon = (CircleImageView) itemView.findViewById(R.id.uIcon);
                holder.uName = (TextView) itemView.findViewById(R.id.uname);
                holder.accept = (Button) itemView.findViewById(R.id.accpet);
                holder.refuse = (Button) itemView.findViewById(R.id.refuse);
            }
        }

        class ViewHolder{

            CircleImageView uIcon;
            TextView uName;
            Button accept;
            Button refuse;
        }
    }
}
