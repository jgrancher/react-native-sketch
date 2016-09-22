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
import android.view.MotionEvent;
import android.view.View;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;

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

  public SketchView(ReactContext context) {
    super(context);
    this.context = context;
    brush.setStyle(Paint.Style.STROKE);
    brush.setStrokeWidth(5);
    this.setDrawingCacheEnabled(true);
  }

  @Override
  protected void onDraw(Canvas c) {
    canvas = c;
    canvas.drawPath(path, brush);
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
    return true;
  }

  private Bitmap getBitmap() {
    return getDrawingCache();
  }

}
