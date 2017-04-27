# React Native Sketch

[![Version](https://img.shields.io/npm/v/react-native-sketch.svg?style=flat-square)](http://npm.im/react-native-sketch)
[![Downloads](https://img.shields.io/npm/dm/react-native-sketch.svg?style=flat-square)](http://npm.im/react-native-sketch)
[![Gitter](https://img.shields.io/badge/chat-on%20gitter-1dce73.svg?style=flat-square)](https://gitter.im/jgrancher/react-native-sketch)
[![MIT License](https://img.shields.io/npm/l/react-native-sketch.svg?style=flat-square)](http://opensource.org/licenses/MIT)

*A React Native component for touch-based drawing.*

![Screenshots](https://cloud.githubusercontent.com/assets/5517450/15202227/ca865758-183b-11e6-8c4e-41080bc04538.jpg "Disclaimer: This is not my signature ;)")

## Getting started

Install React Native Sketch:
```bash
$ npm install react-native-sketch
```

Then, link it to your project:
```bash
$ react-native link
```

**Note**: If you are using an older version of React Native than `0.31`, you will need to install [rnpm](https://github.com/rnpm/rnpm) to link the module.

## Usage

```javascript
import React from 'react';
import {
  Button,
  StyleSheet,
  View,
} from 'react-native';
import Sketch from 'react-native-sketch';

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  sketch: {
    height: 250, // Height needed; Default: 200px
  },
});

class Signature extends React.Component {

  constructor(props) {
    super(props);
    this.clear = this.clear.bind(this);
    this.onReset = this.onReset.bind(this);
    this.onSave = this.onSave.bind(this);
    this.onUpdate = this.onUpdate.bind(this);
  }

  state = {
    encodedSignature: null,
  };

  /**
   * Clear / reset the drawing
   */
  clear() {
    this.sketch.clear();
  }

  /**
   * Do extra things after the sketch reset
   */
  onReset() {
    console.log('The drawing has been cleared!');
  }

  /**
   * The Sketch component provides a 'saveImage' function (promise),
   * so that you can save the drawing in the device and get an object
   * once the promise is resolved, containing the path of the image.
   */
  onSave() {
    this.sketch.saveImage(this.state.encodedSignature)
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
        <Sketch
          fillColor="transparent"
          strokeColor="#111111"
          strokeThickness={2}
          imageType="png"
          onReset={this.onReset}
          onUpdate={this.onUpdate}
          ref={(sketch) => { this.sketch = sketch; }}
          style={styles.sketch}
        />
        <Button
          onPress={this.clear}
          title="Clear drawing"
        />
        <Button
          disabled={!this.state.encodedSignature}
          onPress={this.onSave}
          title="Save drawing"
        />
      </View>
    );
  }

}

export default Signature;
```

## Notes

- The module is available *only on iOS* (for now), as I don't know Android development... But if you think you can help on that matter, please feel free to contact me!
- The module uses this [smooth freehand drawing technique](http://code.tutsplus.com/tutorials/smooth-freehand-drawing-on-ios--mobile-13164) under the hood.

## Contributing

Feel free to contribute by sending a pull request or [creating an issue](https://github.com/jgrancher/react-native-sketch/issues/new).

## License

[MIT](https://github.com/jgrancher/react-native-sketch/tree/master/LICENSE)
