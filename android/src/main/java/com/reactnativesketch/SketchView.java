package com.reactnativesketch;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.RCTEventEmitter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

public class SketchView extends View {

  private Paint brush = new Paint(Paint.ANTI_ALIAS_FLAG);
  private Path path = new Path();
  private Canvas canvas;
  private ReactContext context;
  private Bitmap bitmap;
  public SketchView(ReactContext context) {
    super(context);
    this.context = context;
    brush.setStyle(Paint.Style.STROKE);
    brush.setStrokeWidth(5);
    this.setDrawingCacheEnabled(true);
    this.setDrawingCacheQuality(DRAWING_CACHE_QUALITY_AUTO);
  }

  @Override
  protected void onDraw(Canvas c) {
    c.drawPath(path, brush);
  }


  @Override
  public boolean onTouchEvent(MotionEvent event) {
    getParent().requestDisallowInterceptTouchEvent(true);
    float x = event.getX();
    float y = event.getY();

    switch (event.getAction()) {
      case MotionEvent.ACTION_DOWN:
        path.moveTo(x, y);
        break;
      case MotionEvent.ACTION_MOVE:
        path.lineTo(x, y);
        break;
      default:
        return false;
    }
    invalidate();

    WritableMap events = Arguments.createMap();
    String image = encodeToBase64(getDrawingCache(), Bitmap.CompressFormat.PNG, 100);
    events.putString("image", image);
    context.getJSModule(RCTEventEmitter.class).receiveEvent(
        getId(),
        "topChange",
        events);

    return true;
  }

  private String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality) {
    ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
    image.compress(compressFormat, quality, byteArrayOS);
    return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.NO_WRAP);
  }


}
