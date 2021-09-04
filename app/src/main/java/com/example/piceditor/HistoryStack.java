package com.example.piceditor;

import android.graphics.Point;

import java.util.Stack;

public class HistoryStack<PointF> {

    private final Stack<PointF> undoStack = new Stack<PointF>();
    private final Stack<PointF> redoStack = new Stack<PointF>();

    //　アンドゥ
    public PointF undo(){
        PointF result = null;
        if(!undoStack.empty()){
            result  = undoStack.pop();
            redoStack.push(result);
        }
        return result;
    }

    //　リドゥ
    public PointF redo(){
        PointF result = null;
        if (!redoStack.empty()){
            result = redoStack.pop();
            undoStack.push(result);
        }
        return result;
    }

    // 履歴追加
    public void add (PointF histroy){
        undoStack.push(histroy);
        redoStack.clear();
    }

    // アンドゥの列挙
    public final Iterable<PointF> iterateUndo(){
        return  undoStack;
    }
}
