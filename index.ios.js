import React from 'react';
import PropTypes from 'prop-types';
import { NativeModules, requireNativeComponent, View, ViewPropTypes } from 'react-native';
import RNFS from 'react-native-fs';

const SketchManager = NativeModules.RNSketchManager || {};

// Fallback when RN version is < 0.44
const viewPropTypes = ViewPropTypes || View.propTypes;

export default class Sketch extends React.Component {
  static propTypes = {
    fillColor: PropTypes.string,
    imageType: PropTypes.oneOf(['jpg', 'png']),
    onChange: PropTypes.func,
    onClear: PropTypes.func,
    strokeColor: PropTypes.string,
    strokeThickness: PropTypes.number,
    imageData: PropTypes.string,
    style: viewPropTypes.style,
  };

  static defaultProps = {
    fillColor: null,
    imageType: 'png',
    onChange: () => {},
    onClear: () => {},
    strokeColor: '#000000',
    strokeThickness: 1,
    imageData: null,
    style: null
  };

  static getImageDataFromFilePath(filePath) {
    const formats = {
      "jpg": "image/jpg",
      "jpeg": "image/jpg",
      "png": "image/png"
    }
    const fileExtension = filePath.split('.').pop();
    const format = formats[fileExtension];

    if (!format) {
      throw new Error(`Unable to parse file extension .${fileExtension}`)
    }

    return RNFS.readFile(filePath.path, 'base64')
      .then((base64) => {
          return `data:${format};base64,${base64}`;
      })
  }

  constructor(props) {
    super(props);

    // Sketch base style properties
    this.style = {
      flex: 1,
      backgroundColor: 'transparent',
    };

    this.state = {
      imageData: props.imageData
    }
  }

  componentWillReceiveProps(nextProps) {
    this.setState({
        imageData: nextProps.imageData
    })
  }

  onChange = (event) => {
    const { imageData } = event.nativeEvent;

    this.setState({ imageData });
    this.props.onChange(imageData);
  };

  onClear = () => {
    this.setState({ imageData: null });
    this.props.onClear();
  };

  clear = () => SketchManager.clearDrawing();

  save = () => {
    if (!this.state.imageData) return Promise.reject('No image provided!');

    return SketchManager.saveDrawing(this.state.imageData, this.props.imageType);
  };

  render() {
    const { fillColor, strokeColor, strokeThickness, ...props } = this.props;

    return (
      <RNSketch
        {...props}
        fillColor={fillColor}
        onChange={this.onChange}
        onClear={this.onClear}
        strokeColor={strokeColor}
        strokeThickness={strokeThickness}
        style={[this.style, this.props.style]}
        imageData={this.state.imageData}
      />
    );
  }
}

const RNSketch = requireNativeComponent('RNSketch', Sketch);
