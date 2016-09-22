package com.reactnativesketch;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

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
    String filename = "image-" + UUID.randomUUID().toString() + ".jpg";
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
  public void saveImage(final Callback callback) {
    Log.d("Fuck my", "life");
    /*if(permissionsCheck()) {
      File outputFile = createNewFile();
      OutputStream fout = null;
      try {
        fout = new FileOutputStream(outputFile);
        //getBitmap().compress(Bitmap.CompressFormat.JPEG, 100, fout);
        fout.flush();
        fout.close();
        callback.invoke(outputFile.getAbsolutePath());
      } catch (IOException e) {
        callback.invoke(null);
        e.printStackTrace();
      }
    }*/
    callback.invoke("Hello world");
  }

  private boolean permissionsCheck() {
    int writePermission = ContextCompat.checkSelfPermission(mReactContext, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    if (writePermission != PackageManager.PERMISSION_GRANTED ) {
      String[] PERMISSIONS = {
          Manifest.permission.WRITE_EXTERNAL_STORAGE,
      };
      return false;

    }
    return true;
  }

}
