package com.example.piceditor;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

public class PenPaint extends BasePaint{
    protected Paint paint;
    protected Path  Peint_Path;

    protected int Peint_AlphaValue;
    protected int Peint_RedValue;
    protected int Peint_GreenValue;
    protected int Peint_BlueValue;
    protected int PenWidth;

    public PenPaint(){
        Init();
    }
    //透明度を設定
    public void setAlphaValue(int _alphaValue){Peint_AlphaValue = _alphaValue;}
    //Red値を設定
    public void setRedValue(int _redValue){Peint_RedValue = _redValue;}
    //Green値を設定
    public void setGreenValue(int _greenValue){Peint_GreenValue = _greenValue;}
    //Blue値を設定
    public void setBlueValue(int _blueValue){Peint_BlueValue = _blueValue;}
    //ペン幅を設定
    public void setPenWidth(int _penWidth){PenWidth = _penWidth;}

    //ペンの開始位置を設定
    public void setPenStart(float _x,float _y){Peint_Path.moveTo(_x, _y);}
    //ペンの移動位置を設定
    public void setPenPoint(float _x,float _y){Peint_Path.lineTo(_x, _y);}
    //ペンの開始位置をリセットする
    public void setPenReset(){Peint_Path.reset();}

    //RGB値を設定
    public void setPenRGBValue(int _r,int _g,int _b){
        Peint_RedValue = _r;
        Peint_GreenValue = _g;
        Peint_BlueValue = _b;
    }
    //ARGB値を設定
    public void setPenARGBValue(int _a,int _r,int _g,int _b){
        Peint_AlphaValue = _a;
        Peint_RedValue = _r;
        Peint_GreenValue = _g;
        Peint_BlueValue = _b;
    }
    @Override
    public void Init(){
        paint = new Paint();

        Peint_Path = new Path();
        Peint_AlphaValue = 255;
        Peint_RedValue = 0;
        Peint_GreenValue = 0;
        Peint_BlueValue = 0;
        PenWidth = 10;
    }

    @Override
    public void draw(Canvas canvas){
        //ペンの色を設定
        paint.setColor(Color.argb(Peint_AlphaValue,Peint_RedValue,Peint_GreenValue,Peint_BlueValue));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        //ペン幅を変更する
        paint.setStrokeWidth(PenWidth);

        //ペンを描写する
        canvas.drawPath(Peint_Path, paint);
    }
}
