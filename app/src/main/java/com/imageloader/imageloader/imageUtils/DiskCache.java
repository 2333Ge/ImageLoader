package com.imageloader.imageloader.imageUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.imageloader.imageloader.utils.CloseUtils;
import com.imageloader.imageloader.utils.LogUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class DiskCache implements IImageCache {
    private static String PATH = Environment.getExternalStorageDirectory().getAbsolutePath()+"/设计模式ImageLoader/";

    @Override
    public Bitmap getImage(String name) {
        LogUtils.e("===",PATH);
        return BitmapFactory.decodeFile(PATH + name);
    }

    @Override
    public void putImage(String name, Bitmap bitmap) {
        mkDir(PATH);
        File img = new File(PATH + name);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(img);
            bitmap.compress(Bitmap.CompressFormat.PNG,100,fos);//压缩函数，只压缩存储大小，解压后大小不变
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
                CloseUtils.close(fos);
        }
    }
    private void mkDir(String path){
        File dir = new File(path);
        if(!dir.exists()){
            boolean isMakeDirSuccess = dir.mkdir();
            if ( !isMakeDirSuccess){
                LogUtils.e("dirPath","DiskCache/创建目录失败");
                //此时应该抛出异常
            }
        }
    }
}
