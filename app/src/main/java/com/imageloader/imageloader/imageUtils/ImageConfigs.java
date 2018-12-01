package com.imageloader.imageloader.imageUtils;

import android.app.Activity;
import android.graphics.BitmapFactory;

import com.imageloader.imageloader.utils.LogUtils;

public class ImageConfigs {//builder模式
    private IImageCache cache;
    private Activity activity;//这样设置不会内存泄漏？？
    //加载时配置(加载中图片)、线程数、加载策略
    private int loadFailImgId,loadingImgId;
    public static final int NULL_IMG = -1;
    private ImageConfigs(){}

    public IImageCache getCache() {
        return cache;
    }
    public Activity getActivity(){return activity;}
    public int getLoadFailImgId() {
        return loadFailImgId;
    }
    public int getLoadingImgId() {
        return loadingImgId;
    }

    public static class Builder{
        /**
         * 图片缓存策略
         */
        public Builder(Activity activity){
            this.activity = activity;
        }
        IImageCache cache = new MemoryCache();
        Activity activity;
        int loadFailImgId = -1,loadingImgId = -1;
        public Builder fail(int loadFailImgId){
            this.loadFailImgId = loadFailImgId;
            return this;
        }
        //加载时配置、线程数、加载策略
        public Builder setCache(IImageCache imageCache){
            cache = imageCache;
            return this;
        }
        public Builder loading(int loadingImgId){
            this.loadingImgId = loadingImgId;
            return this;
        }

        /**
         * 加载配置
         * @param config
         */
        void applyConfig(ImageConfigs config){
            config.cache = this.cache;
            config.activity = this.activity;
            config.loadingImgId = this.loadingImgId;
            config.loadFailImgId = this.loadFailImgId;
            if (activity == null){
                LogUtils.e("ImageConfigs/Builder","activity == null");
            }
        }
        public ImageConfigs build(){
            ImageConfigs config = new ImageConfigs();
            applyConfig(config);
            return config;
        }

    }
}
