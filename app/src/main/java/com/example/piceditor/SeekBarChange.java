package com.example.piceditor;

import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import androidx.appcompat.app.AppCompatActivity;

public class SeekBarChange extends AppCompatActivity {
//public class SeekBarChange {
    protected SeekBar red_seekbar;          // Red値シークバー
    protected SeekBar green_seekbar;        // Green値シークバー
    protected SeekBar blue_seekbar;         // Blue値シークバー
    protected SeekBar alpha_Seekbar;        // 透明度シークバー
    protected SeekBar hue_seekbar;          // 色調シークバー
    protected SeekBar chroma_seekbar;       // 彩度シークバー
    protected SeekBar brightness_seekbar;   // 明度シークバー
    protected SeekBar luminance_Seekbar;    // 輝度シークバー
    protected SeekBar contrast_Seekbar;     // 対比シークバー


//    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Init();
    }

    // シークバーの初期化＆リスナーを設定
    public void Init() {
        // シークバーの定義
        red_seekbar = this.findViewById(R.id.red_seekbar);
        green_seekbar = this.findViewById(R.id.green_seekbar);
        blue_seekbar = this.findViewById(R.id.blue_seekbar);
        alpha_Seekbar = this.findViewById(R.id.alpha_seekbar);
        hue_seekbar = this.findViewById(R.id.hue_seekbar);
        chroma_seekbar = this.findViewById(R.id.chroma_seekbar);
        brightness_seekbar = this.findViewById(R.id.brightness_seekbar);
        luminance_Seekbar = this.findViewById(R.id.luminance_seekbar);
        contrast_Seekbar = this.findViewById(R.id.contrast_seekbar);

        //　リスナーを設定
        SetSeekBarListener(red_seekbar);
        SetSeekBarListener(green_seekbar);
        SetSeekBarListener(blue_seekbar);
        SetSeekBarListener(alpha_Seekbar);
        SetSeekBarListener(hue_seekbar);
        SetSeekBarListener(chroma_seekbar);
        SetSeekBarListener(brightness_seekbar);
        SetSeekBarListener(luminance_Seekbar);
        SetSeekBarListener(contrast_Seekbar);
     }

    // リスナーの制御
    public void SetSeekBarListener(SeekBar _seekbar){
        // シークバー インターフェイスクラスの生成
        _seekbar.setOnSeekBarChangeListener(
            new OnSeekBarChangeListener() {
                // シークバーを移動したときに呼ばれる
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    PenPaint clsPen = new PenPaint(); // PenPaintクラス
                    // シークバーIDを取得
                    switch (seekBar.getId()) {
                        case R.id.red_seekbar:
                            // 赤シークバーの値をペンの値に設定する
                            clsPen.setRedValue(red_seekbar.getProgress());
                            break;
                        case R.id.green_seekbar:
                            // 緑シークバーの値をペンの値に設定する
                            clsPen.setGreenValue(green_seekbar.getProgress());
                            break;
                        case R.id.blue_seekbar:
                            // 青シークバーの値をペンの値に設定する
                            clsPen.setBlueValue(blue_seekbar.getProgress());
                            break;
                        default:
                            return;
                    }
                }

                // シークバーに触れたときに呼ばれる
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                // シークバーに離したときに呼ばれる
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }
            }
        );
    }
}
