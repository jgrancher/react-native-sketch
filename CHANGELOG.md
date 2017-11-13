# Change Log

All notable changes to this project will be documented in this file. This project adheres to
[Semantic Versioning](http://semver.org/).

## [v1.0.2] - 2017-11-13

### Changed

* Fixed compatibility with RN 0.49 by removing references to
  [View.propTypes.style](https://github.com/jgrancher/react-native-sketch/pull/34) (Thanks
  [@hnryjms](https://github.com/hnryjms)!).

## [v1.0.1] - 2017-04-29

### Added

* Added backward compatibility for React Native < 0.40 (Thanks
  [@peacechen](https://github.com/peacechen)!).

## [v1.0.0] - 2017-08-28

### Changed

* Breaking change: Changed iOS native headers for compatibility with
  [React Native >= 0.40](https://github.com/facebook/react-native/releases/tag/v0.40.0).
* The `onReset` event has been renamed `onClear`.
* The `saveImage` method has been renamed `save` and doesn't require any argument.
* The README.md has been updated with more documentation.

### Added

* Created an `examples` folder.

## [v0.5.0] - 2017-04-28

### Changed

* Supports of `fillColor="transparent"` attribute value. Requires `imageType="png"` to export a
  transparent image (Thanks [@aprihodko](https://github.com/aprihodko)
  [@vccabral](https://github.com/vccabral)!).

## [v0.4.0] - 2017-04-27

### Added

* Added `imageType` attribute to choose the image type to export: 'png' or 'jpg' (Thanks
  [@blargity](https://github.com/blargity)!).

## [v0.3.0] - 2017-01-19

### Added

* Added `clear` method to reset the drawing from outside (Thanks
  [@blargity](https://github.com/blargity)!).

### Changed

* Fix to make borderRadius property on the sketch view working (Thanks
  [@blargity](https://github.com/blargity)!).

## [v0.2.4] - 2016-06-06

### Changed

* Corrected `PropTypes` import in index.ios.js - Removed from 'react-native' package...

## [v0.2.3] - 2016-05-16

### Changed

* Corrected `onReset` callback: Call `onUpdate` with a null value, then the onReset property.

## [v0.2.2] - 2016-05-13

### Changed

* Updated package.json with new keywords

## [v0.2.1] - 2016-05-12

### Changed

* Fix: Replaced a custom UIColor category with RCTConvert (To prevent linking issues...)

## [v0.2.0] - 2016-05-12

### Changed

* The sketch frame is not static anymore (height is 200px by default)
* The clear button is now on the top left corner. (will be hopefully removed in the future)
* Completed README.md with more informations

### Added

* Property `fillColor` (hexa string) to change the background color of the canvas
* Property `strokeColor` (hexa string) to change the stroke color
* Property `strokeThickness` (number) to change the stroke thickness
* Method `saveImage` (async) which resolves with the drawing image path.

## [v0.1.0] - 2016-05-10

### Added

* Initial commit (static sketch frame size & background)
