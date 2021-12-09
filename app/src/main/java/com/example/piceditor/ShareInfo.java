package com.example.piceditor;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

import java.util.ArrayList;

public class ShareInfo {
    static int peintType = 3; // ペイントタイプ 0:ペン 1:スタンプ
    static int stampNo = 0; // スタンプ番号
    static int stampMaxNo = 0; // スタンプ最大番号
    static String LastFile = null; // 前回編集したファイル
    static String SaveFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + Environment.DIRECTORY_PICTURES; // 保存するファイルパス
    static String SaveFileName = "test.png"; // 保存するファイル名
    static Bitmap Basebitmap = null;
    static Uri LoadFileUri = null; // 読込ファイル
    static int FileDrowType = 1; //  0:ファイル指定なし 1:ファイル指定
    static ArrayList<Bitmap> StampBmpList = null;
}
