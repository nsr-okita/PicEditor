package com.example.piceditor;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class PictureRead {
    public PictureRead(){}
    private Context context;
    public void Init(Context contextInit){
        context = contextInit;
    }

    public Bitmap readAssetFile(String fileName) {
        Bitmap readPicture = null;
        //アセットマネージャーのテキストファイルを読み込む
        AssetManager assetManager = context.getResources().getAssets();
        try {
            InputStream inputStream = null;
            inputStream = assetManager.open(fileName);
            readPicture = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return readPicture;
    }

    public Bitmap readBitmapFile(Uri fileName) {
        // Uriを指定してファイルを取得する
        Bitmap readPicture = null;
        try {
            BufferedInputStream inputStream = new BufferedInputStream(context.getContentResolver().openInputStream(fileName));
            readPicture = BitmapFactory.decodeStream(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return readPicture;
    }

    public Bitmap readBitmapFile(Uri fileName, int viewWidth, int viewHeight) {
        // Uriを指定してファイルを取得する(サイズ調整あり)
        Bitmap readPicture = null;
        try {
            BufferedInputStream inputStream = new BufferedInputStream(context.getContentResolver().openInputStream(fileName));
            readPicture = BitmapFactory.decodeStream(inputStream);
            readPicture = bitmapResize(readPicture,viewWidth,viewHeight);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return readPicture;
    }

    public Bitmap bitmapResize(Bitmap readPicture,int viewWidth, int viewHeight) {
        // リサイズ比
        double resizeScale;
        // 横長画像の場合
        if (readPicture.getWidth() >= readPicture.getHeight()) {
            resizeScale = (double) viewWidth / readPicture.getWidth();
            
        }
        // 縦長画像の場合
        else {
            resizeScale = (double) viewHeight / readPicture.getHeight();
        }
        // リサイズ
        Bitmap afterResizeBitmap = Bitmap.createScaledBitmap(readPicture,
                (int) (readPicture.getWidth() * resizeScale),
                (int) (readPicture.getHeight() * resizeScale),
                true);
        return afterResizeBitmap;
    }
}
