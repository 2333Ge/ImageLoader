package com.imageloader.imageloader.imageUtils;

import android.graphics.Bitmap;
import android.util.LruCache;

public class MemoryCache implements IImageCache {
    private LruCache<String,Bitmap> mCache;
    MemoryCache(){
        initCache();
    }

    private void initCache() {
        final int maxMemory = (int)(Runtime.getRuntime().maxMemory()/1024);
        final int cacheSize = maxMemory/4;
        mCache = new LruCache<String,Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {//这个方法的作用？
                return value.getRowBytes()*value.getHeight()/1024;
            }
        };
    }

    @Override
    public Bitmap getImage(String url) {
        return mCache.get(url);
    }

    @Override
    public void putImage(String url, Bitmap image) {
        mCache.put(url,image);
    }
}
