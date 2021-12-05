package com.example.piceditor;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
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

        FunctionVisible();

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

        //隠しボタンの設定
        Button subButton = findViewById(R.id.sub_button);
        subButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareInfo.stampNo = ShareInfo.stampNo + 1;
                ShareInfo.stampNo = ShareInfo.stampNo % ShareInfo.stampMaxNo;
            }
        });

        //その他ボタン(...)の設定
        Button otherButton = findViewById(R.id.other_button);
        otherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OtherDialog();
            }
        });

        //ペンボタンの設定
        Button penButton = findViewById(R.id.setPen_button);
        penButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareInfo.peintType = 0;
                FunctionVisible();
            }
        });

        // 色調補正ボタンの設定
        Button toneButton = findViewById(R.id.setColorTone_button);
        toneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareInfo.peintType = 2;
                FunctionVisible();
            }
        });

        //スタンプボタンの設定
        Button stampButton = findViewById(R.id.setStamp_button);
        stampButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareInfo.peintType = 1;
                FunctionVisible();
            }
        });

        //トリミングボタンの設定
        Button trimmingButton = findViewById(R.id.trimming_button);
        trimmingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareInfo.peintType = 3;
                FunctionVisible();
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
                ShareInfo.SaveFileName = picture.saveAsPngImage(getApplicationContext(),ShareInfo.SaveFilePath, ShareInfo.Basebitmap);
                TextRead file = new TextRead();
                file.Init(getApplicationContext(),"Last_file_data.txt",2);
                file.saveFile(ShareInfo.SaveFileName + "\n",false);
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

    //その他ダイアログ
    private void OtherDialog() {
        // タッチ時にペイント種別を変える(仮)
        final String[] items = {"ペン", "スタンプ", "色調補正"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //ダイアログのメッセージを設定
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // item_which pressed
                ShareInfo.peintType = which;
                FunctionVisible();
            }
        });
        AlertDialog dialog = builder.create();

        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.gravity = Gravity.TOP;      //画面上部に
        dialog.getWindow().setAttributes(lp);
        dialog.show(); //ここでad.show();とすると通常の表示になってしまう
    }

    // 機能表示・非表示
    private void FunctionVisible() {
        switch (ShareInfo.peintType) {
            case 0:
                findViewById(R.id.pen_panel).setVisibility(View.VISIBLE);
                findViewById(R.id.tone_panel).setVisibility(View.INVISIBLE);
                findViewById(R.id.stamp_panel).setVisibility(View.INVISIBLE);
                break;
            case 1:
                findViewById(R.id.pen_panel).setVisibility(View.INVISIBLE);
                findViewById(R.id.tone_panel).setVisibility(View.INVISIBLE);
                findViewById(R.id.stamp_panel).setVisibility(View.VISIBLE);
                break;
            case 2:
                findViewById(R.id.pen_panel).setVisibility(View.INVISIBLE);
                findViewById(R.id.tone_panel).setVisibility(View.VISIBLE);
                findViewById(R.id.stamp_panel).setVisibility(View.INVISIBLE);
                break;
            default:
                findViewById(R.id.pen_panel).setVisibility(View.INVISIBLE);
                findViewById(R.id.tone_panel).setVisibility(View.INVISIBLE);
                findViewById(R.id.stamp_panel).setVisibility(View.INVISIBLE);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            EditEndDialog();
            return true;
        }
        return false;
    }
}
