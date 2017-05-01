import React from 'react';
import { NativeModules, requireNativeComponent, View } from 'react-native';

const SketchManager = NativeModules.RNSketchManager || {};

export default class Sketch extends React.Component {

  static propTypes = {
    fillColor: React.PropTypes.string,
    imageType: React.PropTypes.oneOf(['jpg', 'png']),
    onChange: React.PropTypes.func,
    onClear: React.PropTypes.func,
    strokeColor: React.PropTypes.string,
    strokeThickness: React.PropTypes.number,
    style: View.propTypes.style,
  }

  static defaultProps = {
    fillColor: null,
    imageType: 'png',
    onChange: () => {},
    onClear: () => {},
    strokeColor: '#000000',
    strokeThickness: 1,
    style: null,
  }

  constructor(props) {
    super(props);

    // Sketch base style properties
    this.style = {
      flex: 1,
      backgroundColor: 'transparent',
    };
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

  clear = () => SketchManager.clearDrawing()

  save = () => SketchManager.saveDrawing(this.state.imageData, this.props.imageType)

  render() {
    return (
      <RNSketch
        {...this.props}
        onChange={this.onChange}
        onClear={this.onClear}
        style={[this.style, this.props.style]}
      />
    );
  }

}

const RNSketch = requireNativeComponent('RNSketch', Sketch);
