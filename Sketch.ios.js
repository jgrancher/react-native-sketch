import React, { requireNativeComponent } from 'react-native';

const { StyleSheet, View } = React;
const { bool, func } = React.PropTypes;

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
});

export default class Sketch extends React.Component {

  static propTypes = {
    enabled: bool,
    onUpdate: func,
    onReset: func,
    style: View.propTypes.style,
  };

  static defaultProps = {
    enabled: true,
    onUpdate: () => {},
    onReset: () => {},
    style: null,
  };

  constructor(props) {
    super(props);
    this.onChange = this.onChange.bind(this);
    this.onReset = this.onReset.bind(this);
  }

  onChange(e) {
    const imageSource = `data:image/jpg;base64,${e.nativeEvent.image}`;
    this.props.onUpdate(imageSource);
  }

  onReset() {
    this.props.onReset();
  }

  render() {
    return (
      <RNSketch
        {...this.props}
        onChange={this.onChange}
        onReset={this.onReset}
        style={[styles.container, this.props.style]}
      />
    );
  }

}

const RNSketch = requireNativeComponent('RNSketch', Sketch);
