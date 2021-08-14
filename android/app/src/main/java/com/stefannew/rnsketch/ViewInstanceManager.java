package com.stefannew.rnsketch;

class ViewInstanceManager {

    private static RNSketchView sketchView;

    void setView(RNSketchView view) {
        sketchView = view;
    }

    RNSketchView getView() {
        return sketchView;
    }
}
