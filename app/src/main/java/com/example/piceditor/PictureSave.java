package com.example.piceditor;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PictureSave {
    public PictureSave(){}
    public void Init(){}

    public String saveAsPngImage(Context context,String filePath,Bitmap basebitmap){
        String fileName = null;
        String mimetype = "image/png";
        try {
            fileName = getFileName();
            File file = new File(filePath, fileName);
            FileOutputStream outStream = new FileOutputStream(file);
            basebitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.close();
            // ギャラリーに表示させる
            MediaScannerConnection.scanFile( context,new String[] { file.getPath() }, new String[] { mimetype },null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }

    public void saveAsPngImage(Context context,String filePath,String fileName,Bitmap basebitmap){
        try {
            String mimetype = "image/png";
            File file = new File(filePath, fileName);
            FileOutputStream outStream = new FileOutputStream(file);
            basebitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.close();
            // ギャラリーに表示させる
            MediaScannerConnection.scanFile( context,new String[] { file.getPath() }, new String[] { mimetype },null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getFileName(){
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String strDate = dateFormat.format(date)+ ".png";
        return strDate;
    }
}
