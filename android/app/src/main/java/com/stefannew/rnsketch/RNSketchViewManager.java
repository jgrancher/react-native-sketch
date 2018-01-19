package com.stefannew.rnsketch;

import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;

public class RNSketchViewManager extends SimpleViewManager<RNSketchView> {
    @Override
    public String getName() {
        return "RNSketch";
    }

    @Override
    protected RNSketchView createViewInstance(ThemedReactContext reactContext) {
        return new RNSketchView(reactContext);
    }
}
