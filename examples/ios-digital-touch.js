/**
 * React Native Sketch - iOS Digital Touch example
 * Reproduces the look and feel of iOS10 Messages Digital Touch (simplified)
 */

import React, { Component } from 'react';
import Sketch from 'react-native-sketch';
import {
  AppRegistry,
  Button,
  Image,
  Modal,
  StatusBar,
  StyleSheet,
  Text,
  TouchableOpacity,
  View,
} from 'react-native';

export default class DigitalTouch extends Component {
  state = {
    color: '#FFFFFF',
    path: null,
  };

  onColorChange = (color) => {
    this.setState({ color });
  };

  onChange = () => {
    console.log('onChange event'); // eslint-disable-line no-console
  };

  clear = () => {
    this.sketch.clear();
  };

  save = () => {
    this.sketch.save().then(({ path }) => {
      this.setState({ path });
    });
  };

  renderColorButton = (color) => {
    const active = color === this.state.color;

    return (
      <TouchableOpacity
        onPress={() => this.onColorChange(color)}
        style={[
          styles.colorButton,
          {
            backgroundColor: active ? '#000' : color,
            borderColor: color,
          },
        ]}
      />
    );
  };

  render() {
    return (
      <View style={styles.container}>
        <StatusBar barStyle={this.state.path ? 'default' : 'light-content'} />
        <View style={styles.colorsBar}>
          {this.renderColorButton('#20BBFC')}
          {this.renderColorButton('#2DFD2F')}
          {this.renderColorButton('#FD28F9')}
          {this.renderColorButton('#EA212E')}
          {this.renderColorButton('#FD7E24')}
          {this.renderColorButton('#FFFA38')}
          {this.renderColorButton('#FFFFFF')}
        </View>
        <Sketch
          fillColor="#000"
          imageType="png"
          onChange={this.onChange}
          ref={(sketch) => {
            this.sketch = sketch;
          }}
          strokeColor={this.state.color}
          strokeThickness={3}
          style={styles.sketch}
        />
        <View style={styles.actionsBar}>
          <Button color="#EA212E" onPress={this.clear} title="❌ Clear" />
          <Button color="#1DBD21" onPress={this.save} title="Save  ✅" />
        </View>
        <Modal animationType="slide" visible={!!this.state.path}>
          <View style={styles.modal}>
            <Text style={styles.title}>Here's the image you created.</Text>
            <Image
              resizeMode="contain"
              source={{ uri: `file://${this.state.path}` }}
              style={styles.image}
            />
          </View>
        </Modal>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    backgroundColor: '#191919',
    flex: 1,
    paddingTop: 20,
  },
  colorsBar: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    paddingHorizontal: 20,
    paddingTop: 10,
  },
  colorButton: {
    borderRadius: 50,
    borderWidth: 8,
    width: 25,
    height: 25,
  },
  sketch: {
    borderRadius: 20,
    margin: 20,
  },
  actionsBar: {
    alignItems: 'stretch',
    flexDirection: 'row',
    justifyContent: 'space-between',
    paddingBottom: 20,
    paddingHorizontal: 20,
  },
  modal: {
    backgroundColor: '#F5FCFF',
    paddingTop: 20,
    flex: 1,
  },
  title: {
    color: '#333333',
    fontSize: 20,
    marginTop: 20,
    textAlign: 'center',
  },
  image: {
    flex: 1,
    margin: 20,
  },
});

AppRegistry.registerComponent('DigitalTouch', () => DigitalTouch);
