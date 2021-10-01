package com.example.piceditor;

import android.content.Context;
import android.util.AttributeSet;

public class PushButton extends androidx.appcompat.widget.AppCompatButton {
    public PushButton(Context context) {
        super(context);
    }

    public PushButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //ボタンタッチ時に小さくする
    @Override
    public void setPressed(boolean pressed) {
        if(pressed){
            this.setScaleY(0.99f);
            this.setScaleX(0.99f);
        }else{
            this.setScaleY(1.0f);
            this.setScaleX(1.0f);
        }
        super.setPressed(pressed);
    }

}
