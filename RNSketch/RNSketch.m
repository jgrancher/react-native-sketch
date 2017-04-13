//
//  RNSketch.m
//  RNSketch
//
//  Created by Jeremy Grancher on 28/04/2016.
//  Copyright © 2016 Jeremy Grancher. All rights reserved.
//

#if __has_include(<React/RCTView.h>)
// React Native >= 0.40
#import <React/RCTView.h>
#import <React/UIView+React.h>
#else
// React Native <= 0.39
#import "RCTView.h"
#import "UIView+React.h"
#endif
#import "RNSketch.h"
#import "RNSketchManager.h"

@implementation RNSketch
{
  // Internal
  UIBezierPath *_path;
  UIImage *_image;
  CGPoint _points[5];
  uint _counter;
}

#pragma mark - UIViewHierarchy methods

- (instancetype)initWithFrame:(CGRect) frame
{
  if ((self = [super initWithFrame:frame])) {
    // Internal setup
    self.multipleTouchEnabled = NO;

    // For borderRadius property to work (CALayer's cornerRadius).
    self.layer.masksToBounds = YES;

    _path = [UIBezierPath bezierPath];
  }

  return self;
}

RCT_NOT_IMPLEMENTED(- (instancetype)initWithCoder:(NSCoder *)aDecoder)

- (void)layoutSubviews
{
  [super layoutSubviews];
  [self drawBitmap];
}

#pragma mark - UIResponder methods

- (void)touchesBegan:(NSSet<UITouch *> *)touches withEvent:(UIEvent *)event
{
  _counter = 0;
  UITouch *touch = [touches anyObject];
  _points[0] = [touch locationInView:self];
}

- (void)touchesMoved:(NSSet<UITouch *> *)touches withEvent:(UIEvent *)event
{
  _counter++;
  UITouch *touch = [touches anyObject];
  _points[_counter] = [touch locationInView:self];

  if (_counter == 4) [self drawCurve];
}

- (void)touchesEnded:(NSSet<UITouch *> *)touches withEvent:(UIEvent *)event
{
  [self drawBitmap];
  [self setNeedsDisplay];

  [_path removeAllPoints];
  _counter = 0;

  // Send event
  if (_onChange) _onChange(@{ @"imageData": [self drawingToString] });
}

- (void)touchesCancelled:(NSSet<UITouch *> *)touches withEvent:(UIEvent *)event
{
  [self touchesEnded:touches withEvent:event];
}

#pragma mark - UIViewRendering methods

- (void)drawRect:(CGRect)rect
{
  [_image drawInRect:rect];
  [_strokeColor setStroke];
  [_path stroke];
}

#pragma mark - Drawing methods

- (void)drawCurve
{
  // Move the endpoint to the middle of the line
  _points[3] = CGPointMake((_points[2].x + _points[4].x) / 2, (_points[2].y + _points[4].y) / 2);

  [_path moveToPoint:_points[0]];
  [_path addCurveToPoint:_points[3] controlPoint1:_points[1] controlPoint2:_points[2]];

  [self setNeedsDisplay];

  // Replace points and get ready to handle the next segment
  _points[0] = _points[3];
  _points[1] = _points[4];
  _counter = 1;
}

- (void)drawBitmap
{
  UIGraphicsBeginImageContextWithOptions(self.bounds.size, NO, 0);

  // Paint background if fillColor property provided
  if (!_image && _fillColor) {
    [_fillColor setFill];
    [[UIBezierPath bezierPathWithRect:self.bounds] fill];
  }

  // Draw with context
  [_image drawAtPoint:CGPointZero];
  [_strokeColor setStroke];
  [_path stroke];
  _image = UIGraphicsGetImageFromCurrentImageContext();

  UIGraphicsEndImageContext();
}

#pragma mark - Export drawing

- (NSString *)drawingToString
{
  NSString *imageData = nil;

  if ([_imageType isEqualToString:@"jpg"]) {
    imageData = [UIImageJPEGRepresentation(_image, 1) base64EncodedStringWithOptions:NSDataBase64Encoding64CharacterLineLength];
  } else if ([_imageType isEqualToString:@"png"]) {
    imageData = [UIImagePNGRepresentation(_image) base64EncodedStringWithOptions:NSDataBase64Encoding64CharacterLineLength];
  } else {
    [NSException raise:@"Invalid image type" format:@"%@ is not a valid image type for exporting the drawing.", _imageType];
  }

  return [[self base64Code] stringByAppendingString:imageData];
}

#pragma mark - Clear drawing

- (void)clear
{
  _image = nil;

  [self drawBitmap];
  [self setNeedsDisplay];

  // Send event
  if (_onClear) _onClear(@{});
  if (_onChange) _onChange(@{});
}

#pragma mark - Helpers

- (NSString *)base64Code
{
  return [NSString stringWithFormat:@"data:image/%@;base64,", _imageType];
}

#pragma mark - Setters

- (void)setFillColor:(UIColor *)fillColor
{
  _fillColor = fillColor;
}

- (void)setStrokeColor:(UIColor *)strokeColor
{
  _strokeColor = strokeColor;
}

- (void)setImageURL:(NSURL *)imageURL
{
  NSData *imageData = [NSData dataWithContentsOfURL:imageURL];
  _image = [UIImage imageWithData:imageData];
  [self drawBitmap];
  [self setNeedsDisplay];
}

- (void)setStrokeThickness:(NSInteger)strokeThickness
{
  _path.lineWidth = strokeThickness;
}

@end
