package com.imageloader.imageloader.imageUtils;

import android.graphics.Bitmap;

/**
 * 图片缓存策略接口
 */
public interface IImageCache {
    Bitmap getImage(String name);
    void putImage(String name,Bitmap bitmap);
//    void remove(String url);
    //存储大小、质量
}
