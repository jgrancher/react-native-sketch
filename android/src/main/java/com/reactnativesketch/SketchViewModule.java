package com.reactnativesketch;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Log;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.PromiseImpl;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

/**
 * Created by asoto on 9/20/16.
 */

public class SketchViewModule extends ReactContextBaseJavaModule {
  private ReactContext mReactContext;
  public SketchViewModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.mReactContext = reactContext;
  }


  @Override
  public String getName() {
    return "RNSketch";
  }

  /**
   * Create a new file
   *
   * @return an empty file
   */
  private File createNewFile() {
    String filename = "image-" + UUID.randomUUID().toString() + ".png";
    File path;
    path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
    File f = new File(path, filename);
    try {
      path.mkdirs();
      f.createNewFile();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return f;
  }

  @ReactMethod
  public void saveImage(String image64, Promise promise) {
    Log.d("Fuck my", "life");
    if(permissionsCheck()) {
      File outputFile = createNewFile();
      OutputStream fout = null;
      try {
        fout = new FileOutputStream(outputFile);
        fout.write(Base64.decode(image64, Base64.NO_WRAP));
        fout.flush();
        fout.close();
        WritableMap map = Arguments.createMap();
        map.putString("uri", outputFile.getAbsolutePath());
        Log.d("Saving Image", outputFile.getAbsolutePath());
        promise.resolve(map);
      } catch (IOException e) {
        promise.reject(e);
        e.printStackTrace();
      }
    }
  }

  private boolean permissionsCheck() {
    int writePermission = ContextCompat.checkSelfPermission(mReactContext, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    if (writePermission != PackageManager.PERMISSION_GRANTED ) {
      String[] PERMISSIONS = {
          Manifest.permission.WRITE_EXTERNAL_STORAGE,
      };
      String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
      ActivityCompat.requestPermissions(getCurrentActivity(), permissions,1);
      return false;

    }
    return true;
  }

}
