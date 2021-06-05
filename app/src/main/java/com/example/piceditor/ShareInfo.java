package com.example.piceditor;

import android.graphics.Bitmap;
import android.os.Environment;

public class ShareInfo {
    static int peintType = 0; // ペイントタイプ 0:ペン 1:スタンプ
    static String LastFile = null; // 前回編集したファイル
    static String SaveFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + Environment.DIRECTORY_PICTURES; // 保存するファイルパス
    static String SaveFileName = "test.png"; // 保存するファイル名
    static Bitmap Basebitmap = null;
}
