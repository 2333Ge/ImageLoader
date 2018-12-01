package com.imageloader.imageloader.imageUtils;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.imageloader.imageloader.utils.CollectionUtils;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ImageLoader {
    private ImageLoader(){}//私有化
    private ImageConfigs config;
    private ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    public static ImageLoader getInstance(){//静态内部类单例
        return InnerInstance.imageLoader;
    }
    public ImageLoader init(ImageConfigs config){
        this.config = config;
        return this;
    }

    static class InnerInstance{
        static final ImageLoader imageLoader = new ImageLoader();
    }

    /**
     * 加载图片到ImageView
     * @param imageUrl 图片url
     * @param imageView imageView
     */
    public void displayImage(final String imageUrl,final ImageView imageView){
        String imageName = CollectionUtils.getNameFromUrl(imageUrl);
        if(imageName == null){
            return;
        }
        Bitmap bitmap = config.getCache().getImage(imageName);
        if(bitmap == null){
            submitLoadRequest(imageUrl,imageView);//网络加载
        }else{
            imageView.setImageBitmap(bitmap);
        }
    }


    private void submitLoadRequest(final String imageUrl,final ImageView imageView){
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                displayImage(imageView,config.getLoadingImgId());//加载中的图片
                final Bitmap bitmap = CollectionUtils.getBitmapFromUrl(imageUrl);
                if(bitmap == null){
                    displayImage(imageView,config.getLoadFailImgId());//加载失败图片
                    return;
                }
                displayImage(imageView,bitmap);
                String imageName = CollectionUtils.getNameFromUrl(imageUrl);
                config.getCache().putImage(imageName,bitmap);
            }
        });
    }

    /**
     * 切换到主线程加载图片
     * @param imageView imageView
     * @param bitmap bitmap
     */
    private void displayImage(final ImageView imageView,final Bitmap bitmap){
        //直接使用这个，无runOnUIThread,第一次没更新，第二次更新了，非主线程更新了
        config.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                imageView.setImageBitmap(bitmap);//直接使用这个，无runOnUIThread,第一次没更新，第二次更新了，非主线程更新了？？？？，
            }
        });
    }
    /**
     * 切换到主线程加载图片
     * @param imageView imageView
     * @param imgId img ID
     */
    private void displayImage(final ImageView imageView,final int imgId){
        if(imgId == ImageConfigs.NULL_IMG){
            return;
        }
        //直接使用这个，无runOnUIThread,第一次没更新，第二次更新了，非主线程更新了
        config.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                imageView.setImageResource(imgId);
            }
        });
    }
}
