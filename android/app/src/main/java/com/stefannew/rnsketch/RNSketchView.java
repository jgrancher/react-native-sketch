package com.stefannew.rnsketch;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.View;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RNSketchView extends View {
    FileOutputStream stream;
    private Path path;
    private ArrayList<Path> paths = new ArrayList<Path>();
    private Paint paint;
    private String imageType = "png";
    private Map<Path, Integer> colorMap = new HashMap<Path, Integer>();
    private Bitmap bitmap;
    private ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

    int paintColor = Color.BLACK;

    public RNSketchView(Context context) {
        super(context);

        paint = new Paint();

        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(1f);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas();

        if (bitmap != null) {
            canvas.drawBitmap(bitmap, 0, 0, null);
        }
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

    public void setStrokeColor(String strokeColor) {
        paintColor = Color.parseColor(strokeColor);
    }

    public void setFillColor(String fillColor) {}

    public void setStrokeThickness(int strokeThickness) {
        paint.setStrokeWidth(strokeThickness);
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public void clearDrawing() {
        paths.clear();
        invalidate();
    }

    public void saveDrawing() { }

    public String drawingToString() {
        Bitmap.CompressFormat format;

        switch (imageType) {
            case "png":
                format = Bitmap.CompressFormat.PNG;
                break;
            case "jpg":
                format = Bitmap.CompressFormat.JPEG;
                break;
            default:
                throw new Error("imageType " + imageType + " is not a valid image type for exporting the drawing.");
        }

        bitmap.compress(format, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();

        return Base64.encodeToString(byteArray, Base64.DEFAULT);
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
