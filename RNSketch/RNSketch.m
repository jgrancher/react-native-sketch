//
//  RNSketch.m
//  RNSketch
//
//  Created by Jeremy Grancher on 28/04/2016.
//  Copyright © 2016 Jeremy Grancher. All rights reserved.
//

#import "RNSketch.h"
#import "RCTEventDispatcher.h"
#import "RCTView.h"
#import "UIView+React.h"

#define UIColorFromRGB(rgbValue) \
[UIColor colorWithRed:((float)((rgbValue & 0xFF0000) >> 16))/255.0 \
green:((float)((rgbValue & 0x00FF00) >>  8))/255.0 \
blue:((float)((rgbValue & 0x0000FF) >>  0))/255.0 \
alpha:1.0]

@implementation RNSketch
{
    // Internal
    RCTEventDispatcher *_eventDispatcher;
    UIButton *_resetButton;
    UIBezierPath *_path;
    UIImage *_image;
    CGPoint _points[5];
    uint _counter;
    
    // Configuration settings
    UIColor *_backgroundColor;
    UIColor *_strokeColor;
}


#pragma mark - UIViewHierarchy methods

- (instancetype)initWithEventDispatcher:(RCTEventDispatcher *)eventDispather
{
    // TODO: Make this frame dynamic
    self = [super initWithFrame:CGRectMake(0, 0, 335, 300)];
    
    if (self) {
        // Event dispatcher
        _eventDispatcher = eventDispather;
        
        // View configuration
        self.multipleTouchEnabled = NO;
        
        // Drawing configuration
        _backgroundColor = UIColorFromRGB(0xf5f5f5);
        _strokeColor = [UIColor blackColor];
        
        // Bezier path creation
        _path = [UIBezierPath bezierPath];
        _path.lineWidth = 2;
        
        [self setupView];
    }
    
    return self;
}

- (void)layoutSubviews
{
    [super layoutSubviews];
    [self drawBitmap];
}


#pragma mark - Setup UI

- (void)setupView
{
    // Reset button creation
    CGRect frame = CGRectMake(self.frame.size.width - 40, 0, 40, 40);
    _resetButton = [UIButton buttonWithType:UIButtonTypeSystem];
    _resetButton.frame = frame;
    _resetButton.enabled = false;
    _resetButton.tintColor = [UIColor blackColor];
    _resetButton.titleLabel.font = [UIFont boldSystemFontOfSize:18];
    [_resetButton setTitle:@"x" forState:UIControlStateNormal];
    [_resetButton addTarget:self action:@selector(resetDrawing) forControlEvents:UIControlEventTouchUpInside];
    
    // Reset background
    UIButton *background = [UIButton buttonWithType:UIButtonTypeCustom];
    background.frame = frame;
    
    // Add subviews
    [self addSubview:background];
    [self addSubview:_resetButton];
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
    // Enable to reset
    [_resetButton setEnabled:true];
    
    [self drawBitmap];
    [self setNeedsDisplay];
    
    [_path removeAllPoints];
    _counter = 0;
    
    // Send event
    NSDictionary *bodyEvent = @{
                                @"target": self.reactTag,
                                @"image": [self drawingToString],
                                };
    [_eventDispatcher sendInputEventWithName:@"topChange" body:bodyEvent];
}

- (void)touchesCancelled:(NSSet<UITouch *> *)touches withEvent:(UIEvent *)event
{
    [self touchesEnded:touches withEvent:event];
}


#pragma mark - UIViewRendering methods

- (void)drawRect:(CGRect)rect
{
    [_image drawInRect:rect];
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
    UIGraphicsBeginImageContextWithOptions(self.bounds.size, YES, 0);
    
    // If first time, paint background
    if (!_image) {
        [_backgroundColor setFill];
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
    return [UIImageJPEGRepresentation(_image, 1) base64EncodedStringWithOptions:NSDataBase64Encoding64CharacterLineLength];
}


#pragma mark - Reset drawing

- (void)resetDrawing
{
    // Disable to reset
    [_resetButton setEnabled:false];
    
    _image = nil;
    
    [self drawBitmap];
    [self setNeedsDisplay];
    
    // Send event
    NSDictionary *bodyEvent = @{
                                @"target": self.reactTag,
                                };
    [_eventDispatcher sendInputEventWithName:@"onReset" body:bodyEvent];
}

@end
