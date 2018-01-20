package com.stefannew.rnsketch;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RNSketchView extends View {
    private Path path;
    private ArrayList<Path> paths = new ArrayList<Path>();
    private Paint paint;
    private String imageType = "png";
    int paintColor = Color.BLACK;
    private Map<Path, Integer> colorMap = new HashMap<Path, Integer>();

    public RNSketchView(Context context) {
        super(context);

        paint = new Paint();
        path = new Path();

        paint.setStrokeWidth(1f);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
    }

    private void touchStart(float x, float y) {
        path.reset();
        path.moveTo(x, y);
    }

    private void touchMove(float x, float y) {
        path.lineTo(x, y);
    }

    private void touchUp(float x, float y) {
        path.lineTo(x, y);
        paths.add(path);
        colorMap.put(path, paintColor);
        path = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (Path p : paths) {
            paint.setColor(colorMap.get(p));
            canvas.drawPath(p, paint);
        }

        paint.setColor(paintColor);
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
                touchUp(x, y);
                break;
            default:
                return false;
        }

        postInvalidate();
        return true;
    }

    public void clear() {
        path.reset();
        invalidate();
    }

    public void savePath() {
        System.out.println("SAVE!");
    }

    public void setStrokeColor(String strokeColor) {
        paintColor = Color.parseColor(strokeColor);
    }

    public void setStrokeThickness(int strokeThickness) {
        paint.setStrokeWidth(strokeThickness);
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }
}
