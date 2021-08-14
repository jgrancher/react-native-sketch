# 🎨 React Native Sketch [Unmaintained]

[![npm](https://img.shields.io/npm/v/react-native-sketch.svg)](https://www.npmjs.com/package/react-native-sketch)
[![Downloads](https://img.shields.io/npm/dm/react-native-sketch.svg)](https://www.npmjs.com/package/react-native-sketch)
[![MIT License](https://img.shields.io/npm/l/react-native-sketch.svg)](http://opensource.org/licenses/MIT)

**⚠️ NOTE [12/08/2021]: Archiving this repository because I haven't had the resources to maintain it for a while. Apologies.**

A React Native component for touch-based drawing.

![Capture](https://user-images.githubusercontent.com/5517450/29750544-da3be86c-8b84-11e7-8996-49521f7a13b3.gif)

## Features

- 👆 Draw with your finger, and export an image from it.
- 🖍 Change the stroke color and thickness on the fly.
- 👻 Generate a transparent image (or not).

## Setup

Install the module from npm:

```bash
npm i -S react-native-sketch
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
| `imageData` | `String` | `null` | PNG/JPEG image intepretation encoded with base64 to render on the drawing canvas. |
| `onChange` | `Function` | `() => {}` | Callback function triggered after every change on the drawing. The function takes one argument: the actual base64 representation of your drawing.|
| `onClear` | `Function` | `() => {}` | Callback function triggered after a `clear` has been triggered. |
| `strokeColor` | `String` | `'#000000'` | The stroke color you want to draw with. |
| `strokeThickness` | `Number` | `1` | The stroke thickness, in pixels. |
| `style` | Style object | `null` | Some `View` styles if you need. |

The component also has some instance methods:

| Name | Return type | Comment |
| ---- | ----------- | ------- |
| `clear()` | `Promise` | Clear the drawing. This method is a Promise mostly to be consistent with `save()`, but you could simply type: `this.sketch.clear();` |
| `save()` | `Promise` | Save the drawing to an image, using the defined props as settings (`imageType`, `fillColor`, etc...). The Promise resolves with an object containing the `path` property of that image. Ex: `this.sketch.save().then(image => console.log(image.path));` |

Here are a few static helper functions for the component:

| Name | Return type | Comment |
| ---- | ----------- | ------- |
| `getImageDataFromFilePath` | `Promise` | Reads a JPEG or PNG file into base64 image data that can be rendered using the `imageData` prop. |

## Examples

The project contains a folder `examples` that contains few demos of how to use `react-native-sketch`. Just copy and paste the code to your React Native application.

- [`Basic`](https://github.com/jgrancher/react-native-sketch/tree/master/examples/basic): uses the bare minimum to make it work.
- [`Digital Touch`](https://github.com/jgrancher/react-native-sketch/tree/master/examples/digital-touch): tries to reproduce the look and feel of iOS Message Digital Touch.

Feel free to play with them!

## Known issues

- Rotating the screen gets to a weird behavior of the sketch view: [#23](https://github.com/jgrancher/react-native-sketch/issues/23)

## Notes

- If you're using Expo, you will have to [`detach` to ExpoKit](https://docs.expo.io/versions/latest/guides/detach.html), as this module uses native iOS code.
- The module is available *only on iOS for now*, as I don't know Android development... But if you think you can help on that matter, please feel free to create a Pull Request!
- The module uses this [smooth freehand drawing technique](http://code.tutsplus.com/tutorials/smooth-freehand-drawing-on-ios--mobile-13164) under the hood.

## Contributing

Feel free to contribute by sending a pull request or [creating an issue](https://github.com/jgrancher/react-native-sketch/issues/new).

## License

[MIT](https://github.com/jgrancher/react-native-sketch/tree/master/LICENSE)
