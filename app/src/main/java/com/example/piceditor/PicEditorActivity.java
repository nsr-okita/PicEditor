package com.example.piceditor;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class PicEditorActivity  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        StampButtonInit();
        setScreenEditor();
    }

    //画像編集画面
    private void setScreenEditor(){
        setContentView(R.layout.activity_canvas);

        CanvasView canvasView = findViewById(R.id.custom_View);
        if(canvasView.getErrorStatus() != 0){
            EditErrorDialog();
        }

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

        // スタンプタッチ
        StampTouchSet();
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
                ShareInfo.peintType = 3;
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

    //エラーダイアログ
    private void EditErrorDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //ダイアログのタイトルを設定
        builder.setTitle("エラーメッセージ");
        //ダイアログのメッセージを設定
        builder.setMessage("ファイルの読み込みに失敗しました。\nお絵描きモードに移行します。");
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

    // スタンプ画像の設定
    private void StampButtonInit() {
        ShareInfo.StampBmpList = new ArrayList<Bitmap>();
        // スタンプのリストを作成
        TextRead stampStrList = new TextRead();
        stampStrList.Init(this,"stamplist.txt");
        ArrayList<String> strList = new ArrayList<String>();
        strList = stampStrList.readAssetFile();

        //ビットマップファイルの読込処理を行う。
        PictureRead readStamp = new PictureRead();
        readStamp.Init(this);

        //ビットマップファイルを取得する。
        int stampNum = 0;
        for (String str : strList) {
            if(str.isEmpty() == false) {
                //スタンプを登録する
                ShareInfo.StampBmpList.add(readStamp.readAssetFile(str));
                stampNum = stampNum + 1;
            }
        }
        ShareInfo.stampMaxNo = stampNum;
    }

    // ボタンの画像を設定とタッチ時の動作
    private void StampTouchSet() {
        int stampButtonMax = 12;
        ImageButton[] stampButton = new ImageButton[stampButtonMax];
        stampButton[0] = findViewById(R.id.stamp1_button);
        stampButton[1] = findViewById(R.id.stamp2_button);
        stampButton[2] = findViewById(R.id.stamp3_button);
        stampButton[3] = findViewById(R.id.stamp4_button);
        stampButton[4] = findViewById(R.id.stamp5_button);
        stampButton[5] = findViewById(R.id.stamp6_button);
        stampButton[6] = findViewById(R.id.stamp7_button);
        stampButton[7] = findViewById(R.id.stamp8_button);
        stampButton[8] = findViewById(R.id.stamp9_button);
        stampButton[9] = findViewById(R.id.stamp10_button);
        stampButton[10] = findViewById(R.id.stamp11_button);
        stampButton[11] = findViewById(R.id.stamp12_button);

        StampUpdateDisplay();

        int bmpNum = 0;
        for (Bitmap bmp : ShareInfo.StampBmpList) {
            final int finalBmpNum = bmpNum;
            stampButton[bmpNum].setImageBitmap(ShareInfo.StampBmpList.get(bmpNum));
            stampButton[bmpNum].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShareInfo.stampNo = finalBmpNum;
                    StampUpdateDisplay();
                }
            });
            bmpNum++;
            if(bmpNum >= stampButtonMax){
                break;
            }
        }
    }

    // 設定中のスタンプを設定中と前面に表示する
    private void StampUpdateDisplay() {
        int stampButtonMax = 12;
        ImageButton[] stampButton = new ImageButton[stampButtonMax];
        stampButton[0] = findViewById(R.id.stamp1_button);
        stampButton[1] = findViewById(R.id.stamp2_button);
        stampButton[2] = findViewById(R.id.stamp3_button);
        stampButton[3] = findViewById(R.id.stamp4_button);
        stampButton[4] = findViewById(R.id.stamp5_button);
        stampButton[5] = findViewById(R.id.stamp6_button);
        stampButton[6] = findViewById(R.id.stamp7_button);
        stampButton[7] = findViewById(R.id.stamp8_button);
        stampButton[8] = findViewById(R.id.stamp9_button);
        stampButton[9] = findViewById(R.id.stamp10_button);
        stampButton[10] = findViewById(R.id.stamp11_button);
        stampButton[11] = findViewById(R.id.stamp12_button);

        int bmpNum = 0;
        while(bmpNum < stampButtonMax){
            if(ShareInfo.stampNo == bmpNum) {
                stampButton[bmpNum].setForeground(getDrawable(R.drawable.setting));
            }
            else
            {
                stampButton[bmpNum].setForeground(null);
            }
            bmpNum++;
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
