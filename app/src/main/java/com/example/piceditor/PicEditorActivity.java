package com.example.piceditor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PicEditorActivity  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setScreenEditor();
    }

    //画像編集画面
    private void setScreenEditor(){
        setContentView(R.layout.activity_canvas);
        String[] test;
        TextRead file = new TextRead();
        file.Init(getApplicationContext(),"Last_file_data.txt",2);

        ImageView imageView = findViewById(R.id.image_view);
        imageView.setImageResource(R.drawable.img);
        test = file.readFile();
        TextView textView2 = findViewById(R.id.text3);
        textView2.setText(test[0]);
        file.saveFile("00000,1,2,3,4\n",false);

        Button returnButton = findViewById(R.id.return_button);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ボタンタップでスタート画面に遷移
                Intent intent = new Intent(getApplication(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
