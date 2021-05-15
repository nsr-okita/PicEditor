package com.example.piceditor;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setScreenMain();
    }

    //スタート画面
    private void setScreenMain(){
        setContentView(R.layout.activity_main);

        TextRead file = new TextRead();
        file.Init(getApplicationContext(),"Last_file_data.txt",2);
        //前回編集したファイルのファイル名を取得
        ShareInfo.LastFile = file.readFile()[0];

        TextView lastTextFile = findViewById(R.id.lastFilePath);
        lastTextFile.setText(ShareInfo.LastFile);
        //前回編集したファイルのファイル名がTextViewの枠内に入らなかったときにスクロールする。
        lastTextFile.setMovementMethod(new ScrollingMovementMethod());
        lastTextFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ShareInfo.LastFile != null) {
                    Intent intent = new Intent(getApplication(), PicEditorActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    EditErrorDialog();
                }
            }
        });

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
    //エラーダイアログ
    private void EditErrorDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //ダイアログのタイトルを設定
        builder.setTitle("エラーメッセージ");
        //ダイアログのメッセージを設定
        builder.setMessage("前回のファイルが存在しません。");
        //「はい」をタッチしたときの処理
        builder.setPositiveButton("確認", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int idx) {

            }
        });
        AlertDialog dialog = builder.create();
        //ダイアログを表示する
        dialog.show();
    }
}