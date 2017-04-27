import React from 'react';
import {
  NativeModules,
  requireNativeComponent,
  View,
} from 'react-native';

const { func, number, string, bool, oneOf } = React.PropTypes;

const SketchManager = NativeModules.RNSketchManager || {};

export default class Sketch extends React.Component {

  static propTypes = {
    fillColor: string,
    onReset: func,
    onUpdate: func,
    clearButtonHidden: bool,
    strokeColor: string,
    strokeThickness: number,
    style: View.propTypes.style,
    imageType: oneOf(['jpg', 'png']),
  };

  static defaultProps = {
    fillColor: '#ffffff',
    onReset: () => {},
    onUpdate: () => {},
    clearButtonHidden: false,
    strokeColor: '#000000',
    strokeThickness: 1,
    style: null,
    imageType: 'jpg',
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

  onUpdate(e) {
    this.props.onUpdate(`${this.getBase64Code()}${e.nativeEvent.image}`);
  }

  getBase64Code() {
    return `data:image/${this.props.imageType};base64,`;
  }

  saveImage(image) {
    if (typeof image !== 'string') {
      return Promise.reject('You need to provide a valid base64 encoded image.');
    }

    const base64Code = this.getBase64Code();
    const src = image.indexOf(base64Code) === 0 ? image.replace(base64Code, '') : image;
    return SketchManager.saveImage(src, this.props.imageType);
  }

  clear() {
    return SketchManager.clear();
  }

  render() {
    return (
      <RNSketch
        {...this.props}
        onChange={this.onUpdate}
        onReset={this.onReset}
        style={[{
          flex: 1,
          backgroundColor: this.props.fillColor,
        }, this.props.style]}
      />
    );
  }

}

const RNSketch = requireNativeComponent('RNSketch', Sketch);
