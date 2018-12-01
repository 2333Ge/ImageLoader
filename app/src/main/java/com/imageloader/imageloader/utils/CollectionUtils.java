package com.imageloader.imageloader.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class CollectionUtils {
    public static Bitmap getBitmapFromUrl(final String imageUrl) {
        Bitmap bitmap = null;
        try{
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            bitmap = BitmapFactory.decodeStream(connection.getInputStream());
            connection.disconnect();//不用担心处理不到这一句吗？？？？
        }catch (IOException e) {
            e.printStackTrace();
        }
        if(bitmap == null){
            LogUtils.w("getBitmap","bitmap == null");
        }
        return bitmap;
    }
    public static String getNameFromUrl(final String url) {
        if (url.contains("/")){
            return url.substring(url.lastIndexOf("/"));
        }else{
            LogUtils.e("displayImage","url无/");
            return url;
        }
    }
}
