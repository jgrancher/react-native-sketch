import React from 'react';
import {
  NativeModules,
  requireNativeComponent,
  StyleSheet,
  View,
} from 'react-native';

const { func, number, string } = React.PropTypes;

const SketchManager = NativeModules.RNSketchManager || {};
const BASE_64_CODE = 'data:image/jpg;base64,';

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
    image: string,
    style: View.propTypes.style,
  };

  static defaultProps = {
    fillColor: '#ffffff',
    onReset: () => {},
    onUpdate: () => {},
    strokeColor: '#000000',
    strokeThickness: 1,
    image: null,
    style: null
  };

  constructor(props) {
    super(props);
    this.onReset = this.onReset.bind(this);
    this.onUpdate = this.onUpdate.bind(this);

    this.state = {}
  }

  onReset() {
    this.props.onUpdate(null);
    this.props.onReset();
  }

  onUpdate(e) {
    this.props.onUpdate(`${BASE_64_CODE}${e.nativeEvent.image}`);
  }

  saveImage(image) {
    if (typeof image !== 'string') {
      return Promise.reject('You need to provide a valid base64 encoded image.');
    }

    const src = image.indexOf(BASE_64_CODE) === 0 ? image.replace(BASE_64_CODE, '') : image;
    return SketchManager.saveImage(src);
  }

  setImage(image) {
    if (this.state.image == image) {
      this.setState({
          image: null
      })
    }
    this.setState({
        image
    })
  }

  render() {
    return (
      <RNSketch
        {...this.props}
        image={this.state.image}
        onChange={this.onUpdate}
        onReset={this.onReset}
        style={[styles.base, this.props.style]}
      />
    );
  }

}

const RNSketch = requireNativeComponent('RNSketch', Sketch);
