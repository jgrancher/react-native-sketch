# 🎨 React Native Sketch

[![npm](https://img.shields.io/npm/v/react-native-sketch.svg)](https://www.npmjs.com/package/react-native-sketch)
[![Downloads](https://img.shields.io/npm/dm/react-native-sketch.svg)](https://www.npmjs.com/package/react-native-sketch)
[![MIT License](https://img.shields.io/npm/l/react-native-sketch.svg)](http://opensource.org/licenses/MIT)

A React Native component for touch-based drawing.

![Screenshots](https://cloud.githubusercontent.com/assets/5517450/15202227/ca865758-183b-11e6-8c4e-41080bc04538.jpg "Disclaimer: This is not my signature ;)")

## Features

- 👆 Draw with your finger, and export an image from it.
- 🖍 Change the stroke color and thickness on the fly.
- 👻 Generate a transparent image (or not).

## Setup

Install the module from npm:

```bash
npm install react-native-sketch
```

Link the module to your project:

```bash
react-native link react-native-sketch
```

## Usage

```javascript
import React from 'react';
import { Alert, Button, View } from 'react-native';
import Sketch from 'react-native-sketch';

export default class MyPaint extends React.Component {
  save = () => {
    this.sketch.save().then(({ path }) => {
      Alert.alert('Image saved!', path);
    });
  };

  render() {
    return (
      <View style={{ flex: 1 }}>
        <Sketch
          ref={sketch => {
            this.sketch = sketch;
          }}
          strokeColor="#ff69b4"
          strokeThickness={3}
        />
        <Button onPress={this.save} title="Save" />
      </View>
    );
  }
}
```

## API

Here are the `props` of the the component:

| Name | Type | Default value | Comment |
| ---- | ---- | ------------- | ---- |
| `fillColor` | `String` | `null` | The color of the sketch background. Default to null to keep it transparent! *Note: This is different from the `style.backgroundColor` property, as the former will be seen in your exported drawing image, whereas the latter is only used to style the view.* |
| `imageType` | `String` | `png` | The type of image to export. Can be `png` or `jpg`. Default to `png` to get transparency out of the box. |
| `onChange` | `Function` | `() => {}` | Callback function triggered after every change on the drawing. The function takes one argument: the actual base64 representation of your drawing.|
| `onClear` | `Function` | `() => {}` | Callback function triggered after a `clear` has been triggered. |
| `strokeColor` | `String` | `#000000` | The color of the stroke with which you want to draw. |
| `strokeThickness` | `Number` | `1` | The stroke thickness in pixels. |
| `style` | `` | `null` | Some `View` styles if you need. |

The component also has some instance methods:

| Name | Return type | Comment |
| ---- | ----------- | ------- |
| `clear()` | `Promise` | Clear the drawing. This method is a Promise mostly to be consistent with the `save()` one, but you could simply type: `this.sketch.clear();` |
| `save()` | `Promise` | Save the drawing to an image, using the defined props as settings (`imageType`, `fillColor`, etc...). The Promise resolves with an object containing the `path` property of that image. Ex: `this.sketch.save().then(image => console.log(image.path));` |

## Examples

The project contains a folder `examples` that contains few demos of how to use `react-native-sketch`. Just copy and paste the code to your React Native application.

- [`ios-digital-touch.js`](https://github.com/jgrancher/react-native-sketch/tree/master/examples/ios-digital-touch.js): uses few colors buttons to reproduce the behaviour of iOS10 Message Digital Touch.
- []

Feel free to play with them!

## Known issues

- Rotating the screen gets to a weird behavior of the sketch view: [#23](https://github.com/jgrancher/react-native-sketch/issues/23)
- Taping the screen without dragging your finger causes an update but does not display any point: [#25](https://github.com/jgrancher/react-native-sketch/issues/25)

## Notes

- The module is available *only on iOS* (for now), as I don't know Android development... But if you think you can help on that matter, please feel free to [contact me](https://twitter.com/jgrancher)!
- The module uses this [smooth freehand drawing technique](http://code.tutsplus.com/tutorials/smooth-freehand-drawing-on-ios--mobile-13164) under the hood.

## Contributing

Feel free to contribute by sending a pull request or [creating an issue](https://github.com/jgrancher/react-native-sketch/issues/new).

## License

[MIT](https://github.com/jgrancher/react-native-sketch/tree/master/LICENSE)
