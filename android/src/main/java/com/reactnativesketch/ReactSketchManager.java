package com.reactnativesketch;

import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;

/**
 * Created by asoto on 18/09/16.
 */
public class ReactSketchManager extends SimpleViewManager<SketchView> {
    private ThemedReactContext mContext = null;
    @Override
    public String getName() {
        return "Sketch";
    }


    @Override
    protected SketchView createViewInstance(ThemedReactContext reactContext) {
        mContext = reactContext;
        return new SketchView(mContext);
    }
}
