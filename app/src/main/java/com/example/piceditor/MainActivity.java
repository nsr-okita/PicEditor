package com.example.piceditor;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setScreenMain();
    }

    //スタート画面
    private void setScreenMain(){
        setContentView(R.layout.activity_main);

        TextRead file = new TextRead();
        file.Init(getApplicationContext(),"Last_file_data.txt",2);
        //前回編集したファイルのファイル名を取得
        String[] last_file = file.readFile();

        TextView lastTextFile = findViewById(R.id.lastFilePath);
        lastTextFile.setText(file.readTextStr[0]);
        //前回編集したファイルのファイル名がTextViewの枠内に入らなかったときにスクロールする。
        lastTextFile.setMovementMethod(new ScrollingMovementMethod());

        Button sendButton = findViewById(R.id.send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), PicEditorActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}