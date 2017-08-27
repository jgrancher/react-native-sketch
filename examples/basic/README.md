# Basic example

Uses the bare minimum to make `react-native-sketch` working.

### Source Code

```javascript
import React, { Component } from 'react';
import Sketch from 'react-native-sketch';
import { AppRegistry, Button, StyleSheet, View } from 'react-native';

export default class Basic extends Component {
  clear = () => {
    this.sketch.clear();
  };

  save = () => {
    this.sketch.save().then(({ path }) => {
      console.log(`The image is saved there: ${path}`); // eslint-disable-line no-console
    });
  };

  render() {
    return (
      <View style={styles.container}>
        <Sketch
          ref={sketch => {
            this.sketch = sketch;
          }}
        />
        <View style={styles.actionsBar}>
          <Button color="#EA212E" onPress={this.clear} title="❌ Clear" />
          <Button color="#1DBD21" onPress={this.save} title="Save  ✅" />
        </View>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    backgroundColor: '#F5FCFF',
    flex: 1,
    paddingTop: 20,
  },
  actionsBar: {
    alignItems: 'stretch',
    flexDirection: 'row',
    justifyContent: 'space-between',
    padding: 20,
  },
});

AppRegistry.registerComponent('Basic', () => Basic);
```

### Results
![Screenshot](https://user-images.githubusercontent.com/5517450/29750247-9fd0f640-8b7f-11e7-8085-1e40933a9fd2.png)
