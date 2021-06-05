package com.example.piceditor;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class PicEditorActivity  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setScreenEditor();
    }

    //画像編集画面
    private void setScreenEditor(){
        setContentView(R.layout.activity_canvas);

        //編集終了ボタン(＜)の設定
        Button returnButton = findViewById(R.id.return_button);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ダイアログを表示
                EditEndDialog();
            }
        });

        //保存ボタン(□)の設定
        Button saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ダイアログを表示
                EditSaveDialog();
            }
        });

        //その他ボタン(...)の設定
        Button otherButton = findViewById(R.id.other_button);
        otherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // タッチ時にペイント種別を変える(仮)
                ShareInfo.peintType = ShareInfo.peintType + 1;
                ShareInfo.peintType = ShareInfo.peintType % 2;
            }
        });
    }

    //編集終了ダイアログ
    private void EditEndDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //ダイアログのタイトルを設定
        builder.setTitle("確認メッセージ");
        //ダイアログのメッセージを設定
        builder.setMessage("編集内容は保存されません。\n本当に終了しますか？");
        //「はい」をタッチしたときの処理
        builder.setPositiveButton("はい", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int idx) {
                ShareInfo.peintType = 0;
                //ボタンタップでスタート画面に遷移
                Intent intent = new Intent(getApplication(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        //「いいえ」をタッチしたときの処理
        builder.setNegativeButton("いいえ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int idx) {
                //何も処理せず編集画面に戻る
            }
        });
        AlertDialog dialog = builder.create();
        //ダイアログを表示する
        dialog.show();
    }
    //保存ダイアログ
    private void EditSaveDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //ダイアログのタイトルを設定
        builder.setTitle("確認メッセージ");
        //ダイアログのメッセージを設定
        builder.setMessage("編集内容を保存しますか？");
        //「はい」をタッチしたときの処理
        builder.setPositiveButton("はい", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int idx) {
                PictureSave picture= new PictureSave();
                ShareInfo.SaveFileName = picture.saveAsPngImage(ShareInfo.SaveFilePath, ShareInfo.Basebitmap);
                TextRead file = new TextRead();
                file.Init(getApplicationContext(),"Last_file_data.txt",2);
                file.saveFile(ShareInfo.SaveFilePath + "/" + ShareInfo.SaveFileName + "\n",false);
            }
        });
        //「いいえ」をタッチしたときの処理
        builder.setNegativeButton("いいえ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int idx) {
                //何も処理せず編集画面に戻る
            }
        });
        AlertDialog dialog = builder.create();
        //ダイアログを表示する
        dialog.show();
    }
}
