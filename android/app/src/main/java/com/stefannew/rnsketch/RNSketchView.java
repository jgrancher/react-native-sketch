package com.stefannew.rnsketch;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.View;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.uimanager.events.RCTEventEmitter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SuppressLint("ViewConstructor")
public class RNSketchView extends View {
    private ReactContext context;
    private Path path;
    private ArrayList<Path> paths = new ArrayList<Path>();
    private Paint paint;
    private String imageType = "png";
    private Map<Path, Integer> colorMap = new HashMap<Path, Integer>();
    private ArrayList<Float> strokeWidths = new ArrayList<>();
    private float strokeThickness = 1f;

    int paintColor = Color.BLACK;

    public RNSketchView(ReactContext context) {
        super(context);

        this.context = context;

        paint = new Paint();
        path = new Path();

        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(strokeThickness);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStyle(Paint.Style.STROKE);

        setDrawingCacheEnabled(true);
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
        strokeWidths.add(strokeThickness);

        WritableMap events = Arguments.createMap();
        events.putString("imageData", drawingToString(getDrawingCache()));
        context.getJSModule(RCTEventEmitter.class).receiveEvent(
            getId(),
            "topChange",
            events
        );
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < paths.size(); i++) {
            Path path = paths.get(i);

            paint.setColor(colorMap.get(path));
            paint.setStrokeWidth(strokeWidths.get(i));
            canvas.drawPath(path, paint);
        }

        paint.setColor(paintColor);
        paint.setStrokeWidth(strokeThickness);
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
        this.strokeThickness = (float) strokeThickness;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public void clearDrawing() {
        paths.clear();
        strokeWidths.clear();
        invalidate();

        WritableMap events = Arguments.createMap();
        context.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(
                "onClear",
                events
        );
    }

    public Bitmap.CompressFormat getImageFormat(String imageType) {
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

        return format;
    }

    public String saveDrawing(String imageData, String imageType) throws IOException {
        File directory = context.getFilesDir();
        File filename = new File(directory, UUID.randomUUID().toString() + "." + imageType);
        FileOutputStream fileOutputStream;

        fileOutputStream = new FileOutputStream(filename);
        byte[] decodedString = Base64.decode(imageData, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        decodedByte.compress(getImageFormat(imageType), 100, fileOutputStream);

        fileOutputStream.close();

        return filename.getAbsolutePath();
    }

    public String drawingToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(getImageFormat(imageType), 100, byteArrayOutputStream);
        return Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
    }
}
