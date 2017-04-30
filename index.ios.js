import React from 'react';
import { NativeModules, requireNativeComponent, View } from 'react-native';

const SketchManager = NativeModules.RNSketchManager || {};

export default class Sketch extends React.Component {

  static propTypes = {
    backgroundColor: React.PropTypes.string,
    color: React.PropTypes.string,
    imageType: React.PropTypes.oneOf(['jpg', 'png']),
    onChange: React.PropTypes.func,
    onClear: React.PropTypes.func,
    style: View.propTypes.style,
    thickness: React.PropTypes.number,
  }

  static defaultProps = {
    backgroundColor: 'rgba(0, 0, 0, 0)',
    color: '#000000',
    imageType: 'png',
    onChange: () => {},
    onClear: () => {},
    style: null,
    thickness: 1,
  }

  state = {
    imageData: null,
  }

  onChange = (event) => {
    const { imageData } = event.nativeEvent;

    this.setState({ imageData });
    this.props.onChange(imageData);
  }

  onClear = () => {
    this.setState({ imageData: null });
    this.props.onClear();
  }

  clear = () => SketchManager.clear()

  save = () => SketchManager.saveImage(this.state.imageData, this.props.imageType)

  render() {
    const baseStyle = {
      flex: 1,
      backgroundColor: this.props.backgroundColor,
    };

    return (
      <RNSketch
        color={this.props.color}
        imageType={this.props.imageType}
        onChange={this.onChange}
        onClear={this.onClear}
        style={[baseStyle, this.props.style]}
        thickness={this.props.thickness}
      />
    );
  }

}

const RNSketch = requireNativeComponent('RNSketch', Sketch);
