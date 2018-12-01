package com.imageloader.imageloader.utils;

import java.io.Closeable;
import java.io.IOException;

public class CloseUtils {
    public static void close(Closeable closeable){
        if (closeable == null){
            return;
        }
        try {
            closeable.close();
        } catch (IOException e) {
            e.printStackTrace();
            LogUtils.e("closeable","close失败");
        }
    }
}
