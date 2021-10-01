package com.example.piceditor;

import android.content.Context;
import android.util.AttributeSet;

public class AlphaButton extends androidx.appcompat.widget.AppCompatButton {
    public AlphaButton(Context context) {
        super(context);
    }

    public AlphaButton(Context context, AttributeSet attrs) {
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
