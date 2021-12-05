package com.example.piceditor;

import android.content.Context;
import android.util.AttributeSet;

public class AlphaImageButton extends androidx.appcompat.widget.AppCompatImageButton{
    public AlphaImageButton(Context context) {
        super(context);
    }

    public AlphaImageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //ボタンタッチ時に半透明にする
    @Override
    public void setPressed(boolean pressed) {
        if(pressed){
            this.setAlpha(0.7f);
        }else{
            this.setAlpha(1.0f);
        }
        super.setPressed(pressed);
    }
}
