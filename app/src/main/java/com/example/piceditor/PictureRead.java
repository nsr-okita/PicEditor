package com.example.piceditor;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
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

    public Bitmap readBitmapFileRotate(Uri filePath)
    {
        // Uriを指定してファイルを取得する(写真の場合、向きを調整する)
        Bitmap readPicture = null;
        Matrix matrix = new Matrix();
        matrix = getRotatedMatrix(filePath, matrix);
        readPicture = getTemporaryFile(filePath, matrix);
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

    private Matrix getRotatedMatrix(Uri filePath, Matrix matrix)
    {
        // 画像の向きを取得し、変換用のmatrixを取得する
        ExifInterface exifInterface = null;

        try {
            BufferedInputStream inputStream = new BufferedInputStream(context.getContentResolver().openInputStream(filePath));
            exifInterface = new ExifInterface(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            return matrix;
        }

        // 画像の向きを取得
        int orientation = exifInterface.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED);

        // 画像を回転させる処理をマトリクスに追加
        switch (orientation) {
            case ExifInterface.ORIENTATION_UNDEFINED:
                break;
            case ExifInterface.ORIENTATION_NORMAL:
                break;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                // 水平方向にリフレクト
                matrix.postScale(-1f, 1f);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                // 180度回転
                matrix.postRotate(180f);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                // 垂直方向にリフレクト
                matrix.postScale(1f, -1f);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                // 反時計回り90度回転
                matrix.postRotate(90f);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                // 時計回り90度回転し、垂直方向にリフレクト
                matrix.postRotate(-90f);
                matrix.postScale(1f, -1f);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                // 反時計回り90度回転し、垂直方向にリフレクト
                matrix.postRotate(90f);
                matrix.postScale(1f, -1f);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                // 反時計回りに270度回転（時計回りに90度回転）
                matrix.postRotate(-90f);
                break;
        }
        return matrix;
    }

    private Bitmap getTemporaryFile(Uri filePath, Matrix matrix) {
        // 画像を変換する
        Bitmap readPicture = null;
        try {
            // 元画像の取得
            BufferedInputStream inputStream = new BufferedInputStream(context.getContentResolver().openInputStream(filePath));
            Bitmap originalPicture = BitmapFactory.decodeStream(inputStream);
            int height = originalPicture.getHeight();
            int width = originalPicture.getWidth();

            // マトリクスをつけることで縮小、向きを反映した画像を生成
            readPicture = Bitmap.createBitmap(originalPicture, 0, 0, width, height, matrix, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return readPicture;
    }
}
