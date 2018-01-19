package com.stefannew.rnsketch;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;

import com.facebook.react.uimanager.SimpleViewManager;

public class RNSketchView extends View {
    private Path path;
    private Paint paint;
    private Color penColor;

    public RNSketchView(Context context) {
        super(context);

        path = new Path();
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
    }

    private void touchStart(float x, float y) {
        path.moveTo(x, y);
    }

    private void touchMove(float x, float y) {
        path.lineTo(x, y);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(path, paint);
    }

    @SuppressLint("ClickableViewAccessibility")
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        int pointerCount = event.getPointerCount();

        if (pointerCount > 1) {
            event.setAction(MotionEvent.ACTION_CANCEL);
            return true;
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchStart(x, y);
                return true;
            case MotionEvent.ACTION_MOVE:
                touchMove(x, y);
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                return false;
        }

        postInvalidate();
        return true;
    }

    public void clearPath() {
        path.reset();
        invalidate();
    }

    public void savePath() {
        System.out.println("SAVE!");
    }
}
