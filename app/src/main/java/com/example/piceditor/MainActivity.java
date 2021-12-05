package com.example.piceditor;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
	private static final int REQUEST_GALLERY = 0;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //　メディア参照を有効にする
        int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
        }

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
        if(ShareInfo.LastFile != null) {
            lastTextFile.setText(ShareInfo.SaveFilePath + "/" + ShareInfo.LastFile);
        }
        //前回編集したファイルのファイル名がTextViewの枠内に入らなかったときにスクロールする。
        lastTextFile.setMovementMethod(new ScrollingMovementMethod());
        lastTextFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
				//前回編集したファイル名タッチイベント
                if(ShareInfo.LastFile != null) {
                    File file = new File(ShareInfo.SaveFilePath, ShareInfo.LastFile);
                    ShareInfo.LoadFileUri = Uri.fromFile(file);
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
                //「+」ボタンタッチイベント
                if(ShareInfo.FileDrowType == 0) {
                    //読み込まない設定
                    Intent intent = new Intent(getApplication(), PicEditorActivity.class);
                    startActivity(intent);
                    finish();
                }else if(ShareInfo.FileDrowType == 1) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent, REQUEST_GALLERY);
                }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_GALLERY && resultCode == RESULT_OK) {
            ShareInfo.LoadFileUri = data.getData();
            Intent intent = new Intent(getApplication(), PicEditorActivity.class);
            startActivity(intent);
            finish();
        }
    }
}