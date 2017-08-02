package com.ahah.lz.mychat;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 * Created by 40660 on 2017/7/31.
 */

public class MyApp extends MultiDexApplication {

//    public static void initImageLoader(Context context) {
//        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
//                .threadPriority(Thread.NORM_PRIORITY - 2)
//                .denyCacheImageMultipleSizesInMemory()
//                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
//                .diskCacheSize(50 * 1024 * 1024) // 50 Mb
//                .diskCacheFileCount(300)
//                .imageDownloader(new MyImageDownloader(context))
//                .tasksProcessingOrder(QueueProcessingType.LIFO)
////                .writeDebugLogs() // Remove for release app
//                .diskCacheExtraOptions(sWidthPix / 3, sWidthPix / 3, null)
//                .build();
//
//        ImageLoader.getInstance().init(config);
//    }
}
