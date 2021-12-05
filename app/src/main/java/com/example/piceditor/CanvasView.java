package com.example.piceditor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class CanvasView extends View implements
        GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener {
    private int RedValue = 0;
    private Bitmap basePicture = null;
    private PenPaint  penPaint;
    private StampPaint testStamp;
    private Canvas canvas;

    GestureDetector gestureDetector = null;

    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        penPaint = new PenPaint();
        testStamp = new StampPaint();

        ShareInfo.Basebitmap = null;

        ShareInfo.stampNo = 0;

        if(ShareInfo.FileDrowType == 1) {
            //ビットマップファイルの読込処理を行う。
            PictureRead readFile = new PictureRead();
            readFile.Init(context);
            basePicture = readFile.readBitmapFile(ShareInfo.LoadFileUri);
        }

        gestureDetector = new GestureDetector(context, this);
    }

    public Bitmap bitmapResize(Bitmap readPicture,int viewWidth, int viewHeight) {
        // リサイズ比
        double resizeScale;
        int widthScale = readPicture.getWidth();
        int heightScale = readPicture.getHeight();
        // 横長画像の場合
        if (readPicture.getWidth() >= readPicture.getHeight()) {
            resizeScale = (double) viewWidth / readPicture.getWidth();
        }
        // 縦長画像の場合
        else {
            resizeScale = (double) viewHeight / readPicture.getHeight();
        }

        widthScale = (int) (readPicture.getWidth() * resizeScale);
        heightScale = (int) (readPicture.getHeight() * resizeScale);

        // リサイズ
        Bitmap afterResizeBitmap = Bitmap.createScaledBitmap(readPicture,
                widthScale,
                heightScale,
                true);
        return afterResizeBitmap;
    }

    @Override
    protected void onDraw(Canvas canvas) {          //描画処理
        canvas.drawBitmap(ShareInfo.Basebitmap, 0, 0, null);
        if(ShareInfo.peintType == 0) {
            //ペイントタイプがペンの時、描画登録前のペンを描画する。
            penPaint.draw(canvas);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h,int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        ShareInfo.Basebitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(ShareInfo.Basebitmap);
        if(ShareInfo.FileDrowType == 1) {
            //前回のファイルを取得した場合(仮実装)
            basePicture = bitmapResize(basePicture, w, h);
            canvas.drawBitmap(basePicture, 0, 0, null);
        }else{
            //ファイル選択で取得した場合(仮実装)
            canvas.drawColor(0xFFFFFFFF);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        event.setLocation(x,y);
        gestureDetector.onTouchEvent(event);

        if(ShareInfo.peintType == 0) {
            //ペイントタイプがペンの時の設定を行う
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    //画面をタッチさしたとき
                    //ペンのRed値を決める(仮)
                    RedValue = RedValue + 100;
                    if(RedValue > 255) {
                        RedValue = 0;
                    }

                    penPaint = new PenPaint();
                    //ペンのRGB値を設定する
                    penPaint.setPenRGBValue(RedValue,0,0);
                    //ペンの開始位置を決める
                    penPaint.setPenStart(x,y);
                    break;
                case MotionEvent.ACTION_MOVE:
                    //タッチしたまま動かしたとき
                    //ペンの移動した位置を取得する
                    penPaint.setPenPoint(x,y);
                    break;
                case MotionEvent.ACTION_UP:
                    //放したとき
                    //ペンの移動した位置を取得する
                    penPaint.setPenPoint(x,y);
                    //ペンの描画を登録する
                    penPaint.draw(canvas);
                    break;
            }
            //画面の再描画を依頼する。
            this.invalidate();
        }

        return true;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent motionEvent) {		//シングルタップの時には呼び出されるがダブルタップのとき反応しない
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent motionEvent) {		//2回タップしたとき
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent motionEvent) {		//ダブルタッチしたときに他の作業したとき
        return false;
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {		//画面を押したときに実行
        float x = motionEvent.getX();
        float y = motionEvent.getY();

        if(ShareInfo.peintType == 1) {
            //ペイントタイプがスタンプの時、スタンプを設定する
            testStamp = new StampPaint();
            //描画するビットマップファイルを登録する。
            testStamp.setStampBmp(ShareInfo.StampBmpList.get(ShareInfo.stampNo));
            //描画する位置とサイズを設定する
            testStamp.setStampDrawHeightScale((int) x, (int) y,100);
            //スタンプの描画を登録する。
            testStamp.draw(canvas);
            //画面の再描画を依頼する。
            this.invalidate();
        }
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {	//押したとき（すぐに離すとダメ）

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {	//すぐに離されたとき
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {			//押したまま動かす
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {			//一定時間押されたとき

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {		//はじいたとき引数（,,X軸の加速度,Y軸の加速度）
        return false;
    }
}