package com.example.piceditor;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class TextRead {
    public TextRead(){}
    private String filename;
    public String[] readTextStr;
    private ArrayList<String> readTextList;
    private Context context;
    public void Init(Context contextInit,String name,int lineNum){
        context = contextInit;
        filename = name;
        readTextStr = new String[lineNum];
    }

    public void Init(Context contextInit,String name){
        context = contextInit;
        filename = name;
        readTextList = new ArrayList<String>();
    }

    public void SetFilename(String name) {
        filename = name;
    }
    public String GetFilename() {
        return filename;
    }

    public ArrayList<String> readAssetFile() {
        //アセットマネージャーのテキストファイルを読み込む
        AssetManager assetManager = context.getResources().getAssets();
        try {
            InputStream inputStream = null;
            inputStream = assetManager.open(filename);
            BufferedReader buffReader = new BufferedReader(new InputStreamReader(inputStream));

            String s;
            while( (s = buffReader.readLine()) != null )
            {
                readTextList.add(s);
            }

            buffReader.close();
            inputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return readTextList;
    }

    // ファイルを保存(write_flg true:上書き　false:追記)
    public void saveFile(String save_str,boolean write_flg){
        //ローカルファイルにテキストを保存
        try {
            FileOutputStream fileOutputstream;
            if(write_flg == true){
                fileOutputstream = context.openFileOutput(filename,Context.MODE_PRIVATE);
            }else{
                fileOutputstream = context.openFileOutput(filename,Context.MODE_APPEND);
            }
            OutputStreamWriter outputWriter = new OutputStreamWriter(fileOutputstream);

            outputWriter.write(save_str);
            outputWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveFile(String save_str){
        saveFile(save_str,true);
    }

    public String[] readFile() {
        //ローカルファイルのテキストを読み込む
        try{
            FileInputStream fileInputstreamin = context.openFileInput(filename);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputstreamin,"UTF-8"));
            String tmp;
            int i = 0;
            while((tmp = reader.readLine()) != null){
                readTextStr[i] = tmp;
            }
            reader.close();
        }catch( IOException e ){
            e.printStackTrace();
        }
        return readTextStr;
    }
}
