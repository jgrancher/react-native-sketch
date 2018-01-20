package com.stefannew.rnsketch;

import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;

import javax.annotation.Nullable;

public class RNSketchViewManager extends SimpleViewManager<RNSketchView> {

    protected static final String REACT_CLASS = "RNSketch";

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    protected RNSketchView createViewInstance(ThemedReactContext reactContext) {
        return new RNSketchView(reactContext);
    }

    @ReactProp(name = "strokeColor")
    public void setStrokeColor(RNSketchView view, String strokeColor) {
        view.setStrokeColor(strokeColor);
    }
}
