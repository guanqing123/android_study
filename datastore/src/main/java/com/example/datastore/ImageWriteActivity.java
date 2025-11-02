package com.example.datastore;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.datastore.util.FileUtil;

import java.io.File;

public class ImageWriteActivity extends AppCompatActivity implements View.OnClickListener {

    private String path;
    private String directory;
    private ImageView iv_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_write);

        findViewById(R.id.btn_write).setOnClickListener(this);
        findViewById(R.id.btn_read).setOnClickListener(this);

        iv_image = findViewById(R.id.iv_image);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_write: {
                Dialog dialog = new Dialog(this);
                dialog.setContentView(R.layout.custom_image_dialog);
                directory = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString();
                dialog.findViewById(R.id.btn_bird).setOnClickListener(btn -> {
                    path = directory + File.pathSeparator + "bird.jpeg";
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bird);
                    FileUtil.saveImage(path, bitmap);
                    dialog.dismiss();
                });
                dialog.findViewById(R.id.btn_flower).setOnClickListener(btn -> {
                    path = directory + File.pathSeparator + "flower.jpeg";
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.flower);
                    FileUtil.saveImage(path, bitmap);
                    dialog.dismiss();
                });
                dialog.show();
                break;
            }
            case R.id.btn_read: {
                Dialog dialog = new Dialog(this);
                dialog.setContentView(R.layout.custom_image_dialog);
                directory = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString();
                dialog.findViewById(R.id.btn_bird).setOnClickListener(btn -> {
                    path = directory + File.pathSeparator + "bird.jpeg";
                    //Bitmap bitmap = FileUtil.readImage(path);
                    //Bitmap bitmap = BitmapFactory.decodeFile(path);
                    //iv_image.setImageBitmap(bitmap);

                    // 直接调用setImageURI方法,设置图像视图的路径对象
                    iv_image.setImageURI(Uri.parse(path));
                    dialog.dismiss();
                });
                dialog.findViewById(R.id.btn_flower).setOnClickListener(btn -> {
                    path = directory + File.pathSeparator + "flower.jpeg";
                    //Bitmap bitmap = FileUtil.readImage(path);
                    //Bitmap bitmap = BitmapFactory.decodeFile(path);
                    //iv_image.setImageBitmap(bitmap);

                    // 直接调用setImageURI方法,设置图像视图的路径对象
                    iv_image.setImageURI(Uri.parse(path));
                    dialog.dismiss();
                });
                dialog.show();
                break;
            }
        }
    }
}