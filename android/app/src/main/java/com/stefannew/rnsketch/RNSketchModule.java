package com.stefannew.rnsketch;

import android.widget.Toast;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

public class RNSketchModule extends ReactContextBaseJavaModule {

    RNSketchModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "RNSketchManager";
    }

    @ReactMethod
    public void clearDrawing(final Promise promise) {
        RNSketchView sketchView = new ViewInstanceManager().getView();
        sketchView.clearDrawing();
        promise.resolve(null);
    }

    @ReactMethod
    public void saveDrawing() {
        Toast.makeText(getReactApplicationContext(), "Save Drawing", Toast.LENGTH_LONG).show();
    }

    @ReactMethod
    public void onChange() {
        Toast.makeText(getReactApplicationContext(), "On Change", Toast.LENGTH_LONG).show();
    }
}
