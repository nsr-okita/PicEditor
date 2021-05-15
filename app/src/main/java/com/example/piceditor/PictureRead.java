package com.example.piceditor;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class PictureRead {
    public PictureRead(){}
    private String filename;
    public Bitmap readPicture;
    private Context context;
    public void Init(Context contextInit){
        context = contextInit;
    }

    public Bitmap readAssetFile(String filename) {
        //アセットマネージャーのテキストファイルを読み込む
        AssetManager assetManager = context.getResources().getAssets();
        try {
            InputStream inputStream = null;
            inputStream = assetManager.open(filename);
            readPicture = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return readPicture;
    }
}
