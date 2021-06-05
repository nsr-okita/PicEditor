package com.example.piceditor;

import android.graphics.Bitmap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PictureSave {
    public PictureSave(){}
    public void Init(){}

    public String saveAsPngImage(String filePath,Bitmap basebitmap){
        String fileName = null;
        try {
            fileName = getFileName();
            File file = new File(filePath, fileName);
            FileOutputStream outStream = new FileOutputStream(file);
            basebitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }

    public void saveAsPngImage(String filePath,String fileName,Bitmap basebitmap){
        try {
            File file = new File(filePath, fileName);
            FileOutputStream outStream = new FileOutputStream(file);
            basebitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.close();
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
