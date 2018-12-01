package com.imageloader.imageloader;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.imageloader.imageloader.imageUtils.DiskCache;
import com.imageloader.imageloader.imageUtils.IImageCache;
import com.imageloader.imageloader.imageUtils.ImageConfigs;
import com.imageloader.imageloader.imageUtils.ImageLoader;
import com.imageloader.imageloader.utils.LogUtils;

public class MainActivity extends AppCompatActivity {
    private Button b_load;
    private ImageView iv_display;
    private int num =1;
    private final String urlTest = "http://39.105.0.212/shop/image/r___________renleipic_01/logo.jpg";
    private final int REQUEST_WRITE_EXTERNAL_STORAGE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b_load = findViewById(R.id.b_load);
        iv_display = findViewById(R.id.iv_display);
        checkPermission();
        b_load.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //LogUtils.i("onClick","in===" + num++);
                IImageCache cache = new DiskCache();
                ImageConfigs configs = new ImageConfigs.Builder(MainActivity.this)
                        .setCache(cache)
                        .loading(R.drawable.rick)
                        .fail(R.drawable.rick2)
                        .build();
                ImageLoader.getInstance()
                        .init(configs)
                        .displayImage(urlTest,iv_display);
            }
        });
    }

    private void checkPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //用户已经拒绝过一次，再次弹出权限申请对话框需要给用户一个解释,用户选择不再询问时，此方法返回 false。
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "请开通相关权限，否则无法正常使用本应用！", Toast.LENGTH_SHORT).show();
            }
            //申请权限,第三个参数是请求码便于在onRequestPermissionsResult 方法中根据requestCode进行判断.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE);

        }
    }
}
