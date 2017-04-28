//
//  RNSketch.h
//  RNSketch
//
//  Created by Jeremy Grancher on 28/04/2016.
//  Copyright © 2016 Jeremy Grancher. All rights reserved.
//

#import <UIKit/UIKit.h>

@class RCTEventDispatcher;

@interface RNSketch : UIView

- (instancetype)initWithEventDispatcher:(RCTEventDispatcher *)eventDispather NS_DESIGNATED_INITIALIZER;
- (void)setFillColor:(UIColor *)fillColor;
- (void)setStrokeColor:(UIColor *)strokeColor;
- (void)setClearButtonHidden:(BOOL)hidden;
- (void)clearDrawing;
- (void)setImage:(NSURL *)image;

@end
