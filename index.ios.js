import React from 'react';
import {
  NativeModules,
  requireNativeComponent,
  StyleSheet,
  View,
} from 'react-native';

const { func, number, string, oneOf } = React.PropTypes;

const SketchManager = NativeModules.RNSketchManager || {};

const styles = StyleSheet.create({
  base: {
    flex: 1,
    height: 200,
  },
});

export default class Sketch extends React.Component {

  static propTypes = {
    fillColor: string,
    onReset: func,
    onUpdate: func,
    strokeColor: string,
    strokeThickness: number,
    style: View.propTypes.style,
    imageType: oneOf(['jpg', 'png'])
  };

  static defaultProps = {
    fillColor: '#ffffff',
    onReset: () => {},
    onUpdate: () => {},
    strokeColor: '#000000',
    strokeThickness: 1,
    style: null,
    imageType: 'jpg'
  };

  constructor(props) {
    super(props);
    this.onReset = this.onReset.bind(this);
    this.onUpdate = this.onUpdate.bind(this);
    this.getBase64Code = this.getBase64Code.bind(this);
  }

  onReset() {
    this.props.onUpdate(null);
    this.props.onReset();
  }

  getBase64Code() {
    return `data:image/${this.props.imageType};base64,`;
  }

  onUpdate(e) {
    const { onUpdate, imageType } = this.props;

    onUpdate(`${this.getBase64Code()}${e.nativeEvent.image}`);
  }

  saveImage(image) {
    if (typeof image !== 'string') {
      return Promise.reject('You need to provide a valid base64 encoded image.');
    }

    const base64Code = this.getBase64Code();
    const src = image.indexOf(base64Code) === 0 ? image.replace(base64Code, '') : image;
    return SketchManager.saveImage(src, this.props.imageType);
  }

  render() {
    return (
      <RNSketch
        {...this.props}
        onChange={this.onUpdate}
        onReset={this.onReset}
        style={[styles.base, this.props.style]}
      />
    );
  }

}

const RNSketch = requireNativeComponent('RNSketch', Sketch);
