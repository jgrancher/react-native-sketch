package com.reactnativesketch;

import android.app.Activity;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;

/**
 * Created by asoto on 18/09/16.
 */
public class ReactSketchViewManager extends SimpleViewManager<SketchView> {
  private ThemedReactContext mContext;
  private SketchView mSketchView;


  @Override
  public String getName() {
    return "RNSketch";
  }

  @Override
  protected SketchView createViewInstance(ThemedReactContext reactContext) {
    mContext = reactContext;
    mSketchView = new SketchView(mContext);
    return mSketchView;
  }
}
