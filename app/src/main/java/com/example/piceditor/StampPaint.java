package com.example.piceditor;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class StampPaint{
    protected Rect src = new Rect();
    protected Rect dst = new Rect();	//表示先
    protected int BmpPoint_x,BmpPoint_y; // 元画像の切り取り位置
    protected int BmpSize_w,BmpSize_h; // 元画像の切り取りサイズ
    protected int DrawPoint_x,DrawPoint_y; // 表示位置
    protected int DrawSize_w,DrawSize_h; // 表示サイズ
    protected Bitmap bmp;

    public StampPaint(){Init();}
    public void setStampBmpPointX(int x){BmpPoint_x = x;}
    public void setStampBmpPointY(int y){BmpPoint_y = y;}
    public void setStampBmpWidth(int width){BmpSize_w = width;}
    public void setStampBmpHeight(int height){BmpSize_h = height;}

    public void setStampDrawPointX(int x){DrawPoint_x = x;}
    public void setStampDrawPointY(int y){DrawPoint_y = y;}
    public void setStampDrawWidth(int width){DrawSize_w = width;}
    public void setStampDrawHeight(int height){DrawSize_h = height;}

    // ビットマップを登録する
    public void setStampBmp(Bitmap _bmp){
        bmp=_bmp;
        BmpPoint_x = 0;
        BmpPoint_y = 0;
        BmpSize_w = bmp.getWidth();
        BmpSize_h = bmp.getHeight();
    }

    public int getStampBmpPointX(){return BmpPoint_x;}
    public int getStampBmpPointY(){return BmpPoint_y;}
    public int getStampBmpWidth(){return BmpSize_w;}
    public int getStampBmpHeight(){return BmpSize_h;}

    public int getStampDrawPointX(){return DrawPoint_x;}
    public int getStampDrawPointY(){return DrawPoint_y;}
    public int getStampDrawWidth(){return DrawSize_w;}
    public int getStampDrawHeight(){return DrawSize_h;}
    public int getMidX(){return DrawPoint_x-(DrawSize_w/2);}
    public int getMidY(){return DrawPoint_y-(DrawSize_h/2);}

    public void Init() {
        BmpPoint_x = 0;
        BmpPoint_y = 0;
        BmpSize_w=0;
        BmpSize_h=0;
        DrawSize_w=48;
        DrawSize_h=48;
        DrawPoint_x=0;
        DrawPoint_y=0;
        bmp = null;
    }

    // 元画像の切り取り位置を設定
    public void setStampBmpPoint(int x,int y) {
        BmpPoint_x = x;
        BmpPoint_y = y;
    }

    // 元画像の切り取りサイズを設定
    public void setStampBmpSize(int width,int height) {
        BmpSize_w = width;
        BmpSize_h = height;
    }

    // 元画像の切り取りサイズ・位置を設定
    public void setStampBmpSize_BmpPoint(int x,int y,int width,int height) {
        BmpPoint_x = x;
        BmpPoint_y = y;
        BmpSize_w = width;
        BmpSize_h = height;
    }

    // 表示位置を設定
    public void setStampDrawPoint(int x,int y) {
        DrawPoint_x = x;
        DrawPoint_y = y;
    }

    // 表示サイズを設定
    public void setStampDrawSize(int width,int height) {
        DrawSize_w = width;
        DrawSize_h = height;
    }
    // 表示サイズ・位置を設定
    public void setStampDrawPoint_DrawSize(int x,int y,int width,int height) {
        DrawPoint_x = x;
        DrawPoint_y = y;
        DrawSize_w = width;
        DrawSize_h = height;
    }

    // 表示サイズ(高さを基準に変更)・位置を設定
    public void setStampDrawHeightScale(int x,int y,int heightScale) {
        DrawPoint_x = x;
        DrawPoint_y = y;
        // リサイズ比
        double resizeScale;
        resizeScale = (double) heightScale / bmp.getHeight();
        DrawSize_w =  (int) (bmp.getWidth()*resizeScale);
        DrawSize_h = (int) (bmp.getHeight()*resizeScale);
    }

    // 表示サイズ(高さを基準に変更)・位置を設定
    public void setStampDrawWidthScale(int x,int y,int widthScale) {
        DrawPoint_x = x;
        DrawPoint_y = y;
        // リサイズ比
        double resizeScale;
        resizeScale = (double) widthScale / bmp.getHeight();
        DrawSize_w =  (int) (bmp.getWidth()*resizeScale);
        DrawSize_h = (int) (bmp.getHeight()*resizeScale);
    }

    public void draw(Canvas canvas) {
        if(bmp != null) {
            src.set(BmpPoint_x, BmpPoint_y, BmpPoint_x + BmpSize_w, BmpPoint_y + BmpSize_h);
            dst.set(getMidX(), getMidY(), DrawSize_w + getMidX(), DrawSize_h + getMidY());
            canvas.drawBitmap(bmp, src, dst, null);    //（,元の画像の表示元,出力領域,）
        }
    }
}
