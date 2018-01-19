package com.stefannew.rnsketch;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;

public class RNSketchModule extends ReactContextBaseJavaModule {
    public RNSketchModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "RNSketch";
    }
}
