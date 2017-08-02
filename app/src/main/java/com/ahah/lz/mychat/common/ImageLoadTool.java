package com.ahah.lz.mychat.common;

import android.widget.ImageView;

import com.ahah.lz.mychat.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by 40660 on 2017/7/31.
 */

public class ImageLoadTool {

    public ImageLoadTool(){
    }

    public static DisplayImageOptions options = new DisplayImageOptions
            .Builder()
            .showImageOnLoading(R.drawable.ic_launcher)
            .showImageForEmptyUri(R.drawable.ic_launcher)
            .showImageOnFail(R.drawable.ic_launcher)
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .considerExifParams(true)
            .build();

    public ImageLoader imageLoader = ImageLoader.getInstance();

    public void loadImage(ImageView imageView , String url){

        System.out.println("ImageLoadTool---"+url);
        imageLoader.displayImage(url , imageView , options);
    }
}
