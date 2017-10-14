package com.ahah.lz.mychat.jpush;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ahah.lz.mychat.R;
import com.ahah.lz.mychat.common.Global;
import com.ahah.lz.mychat.common.ImageLoadTool;
import com.ahah.lz.mychat.net.NetworkCallback;
import com.ahah.lz.mychat.net.NetworkImpl;
import com.ahah.lz.mychat.widget.CircleImageView;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONException;
import org.json.JSONObject;


public class ConfirmFriendActivity extends AppCompatActivity implements NetworkCallback{

    private RecyclerView fRequestList;
    private fRequestAdapter adapter;
    private NetworkImpl network;
    private String[] fGroup;
    private String group , fname;
    private ArrayAdapter<String> arrayAdapter;
    private String TAG_CONFIRM = "TAG_CONFIRM";


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

        network = new NetworkImpl(this ,this);

        arrayAdapter =
                new ArrayAdapter<String>(this , android.R.layout.simple_spinner_item , Global.fGroup);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        System.out.println("-----"+Global.fGroup.size());

        fRequestList = (RecyclerView) findViewById(R.id.friendRequestList);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(ConfirmFriendActivity.this , LinearLayoutManager.VERTICAL , false);
        fRequestList.setLayoutManager(manager);
        adapter = new fRequestAdapter();
        fRequestList.setAdapter(adapter);


    }

    @Override
    public void parseJson(int code, JSONObject respanse, String tag) throws JSONException {

    }

    @Override
    public void getNetwork(String url, String tag) {

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
            bindHolder.holder.groupSpinner.setAdapter(arrayAdapter);
            bindHolder.holder.groupSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    group = Global.fGroup.get(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }

            });
            bindHolder.holder.accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    fname = Global.fRequest.get(position).name;
                    RequestParams params = new RequestParams();
                    params.put("uid" , Global.Account.id);
                    params.put("uname" , Global.Account.name);
                    params.put("group" , group);
                    params.put("fname" , fname);
                    params.put(Global.ACCPET , Global.ACCPET+2);

                    network.loadData(Global.HOST_ADDFRIEND , params ,TAG_CONFIRM , NetworkImpl.Request.Post);
                    Global.fRequest.remove(position);
                    notifyDataSetChanged();
                }
            });

            bindHolder.holder.refuse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RequestParams params = new RequestParams();
                    params.put("uid" , Global.Account.id);
                    params.put("uname" , Global.Account.name);
                    params.put("group" , group);
                    params.put("fname" , fname);
                    params.put(Global.ACCPET , Global.REFUSE);
                    network.loadData(Global.HOST_ADDFRIEND , params ,TAG_CONFIRM , NetworkImpl.Request.Post);;
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
                holder.groupSpinner = (Spinner) itemView.findViewById(R.id.groupSpinner);
            }
        }

        class ViewHolder{

            CircleImageView uIcon;
            Spinner groupSpinner;
            TextView uName;
            Button accept;
            Button refuse;
        }
    }
}
