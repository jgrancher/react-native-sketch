package com.stefannew.rnsketch;

import android.widget.Toast;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

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
    public void setStrokeColor(String strokeColor) {
        RNSketchView sketchView = new ViewInstanceManager().getView();
        sketchView.setStrokeColor(strokeColor);
    }

    @ReactMethod
    public void saveDrawing(String imageData, String imageType, final Promise promise) {
        RNSketchView sketchView = new ViewInstanceManager().getView();

        try {
            String path = sketchView.saveDrawing(imageData, imageType);
            WritableMap map = Arguments.createMap();

            map.putString("path", path);

            promise.resolve(map);
        } catch (IOException e) {
            promise.reject("FILE_SAVE_ERROR", e.getMessage());
        }
    }
}
