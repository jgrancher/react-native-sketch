# react-native-sketch

A react-native component to draw with touch events. (*Only for iOS for now*)

![Screenshots](https://cloud.githubusercontent.com/assets/5517450/15202227/ca865758-183b-11e6-8c4e-41080bc04538.jpg "Disclaimer: This is not my signature ;)")

## Why?

For a work project, I needed a react-native component that would allow a user to draw their signature with their fingers, then save it as an image and upload it.

As I could not find anything pre-existing ready to use (I tried some experiments with the unmaintained [react-native-canvas](https://github.com/lwansbrough/react-native-canvas) but I encountered performances issues), I developed this component, which shamelessly uses this [smooth drawing technique](http://code.tutsplus.com/tutorials/smooth-freehand-drawing-on-ios--mobile-13164) under the hood.

## Getting started

Install [rnpm](https://github.com/rnpm/rnpm) to make things easy:
```bash
$ npm i -g rnpm
```

Then, with rnpm installed:
```bash
$ rnpm install react-native-sketch
```

## Usage

```javascript
import React, { Component } from 'react';
import { StyleSheet, Text, TouchableOpacity, View } from 'react-native';
import Sketch from 'react-native-sketch';

const styles = StyleSheet.create({
  container: {
    padding: 20,
  },
  instructions: {
    fontSize: 16,
    marginBottom: 20,
    textAlign: 'center',
  },
  sketch: {
    height: 250, // Height needed; Default: 200px
    marginBottom: 20,
  },
  button: {
    alignItems: 'center',
    backgroundColor: '#111111',
    padding: 20,
  },
  buttonText: {
    color: '#ffffff',
    fontSize: 16,
  },
});

class Signature extends Component {

  constructor(props) {
    super(props);
    this.onSave = this.onSave.bind(this);
    this.onUpdate = this.onUpdate.bind(this);
  }

  state = {
    encodedSignature: null,
  };

  /**
   * The Sketch component provides a 'saveImage' function (promise),
   * so that you can save the drawing in the device and get an object
   * once the promise is resolved, containing the path of the image.
   */
  onSave() {
    this._sketch.saveImage(this.state.encodedSignature)
      .then((data) => console.log(data))
      .catch((error) => console.log(error));
  }

  /**
   * On every update (touch up from the drawing),
   * you'll receive the base64 representation of the drawing as a callback.
   */
  onUpdate(base64Image) {
    this.setState({ encodedSignature: base64Image });
  }

  render() {
    return (
      <View style={styles.container}>
        <Text style={styles.instructions}>
          Use your finger on the screen to sign.
        </Text>
        <Sketch
          fillColor="#f5f5f5"
          strokeColor="#111111"
          strokeThickness={2}
          onReset={this.onUpdate}
          onUpdate={this.onUpdate}
          ref={(sketch) => { this._sketch = sketch; }}
          style={styles.sketch}
        />
        <TouchableOpacity
          style={styles.button}
          onPress={this.onSave}
        >
          <Text style={styles.buttonText}>Save</Text>
        </TouchableOpacity>
      </View>
    );
  }

}

export default Signature;
```

## Roadmap

- [ ] Define a way for external components to clear the current drawing. (ie. Trigger an action from a button).
- [ ] Improve the documentation
- [ ] Make some tests!
- [ ] Android support (help wanted ¯\\_(ツ)_/¯)
