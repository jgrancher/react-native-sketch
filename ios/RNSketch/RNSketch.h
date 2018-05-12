//
//  RNSketch.h
//  RNSketch
//
//  Created by Jeremy Grancher on 28/04/2016.
//  Copyright © 2016 Jeremy Grancher. All rights reserved.
//

#import <UIKit/UIKit.h>
#if __has_include(<React/RCTViewManager.h>)
// React Native >= 0.40
#import <React/UIView+React.h>
#else
// React Native <= 0.39
#import "UIView+React.h"
#endif

@interface RNSketch : UIView

- (void)clear;
- (NSString *)base64Code;

// Events
@property (nonatomic, copy) RCTBubblingEventBlock onChange;
@property (nonatomic, copy) RCTBubblingEventBlock onClear;

// Properties
@property (nonatomic, strong) UIColor *fillColor;
@property (nonatomic, strong) NSString *imageType;
@property (nonatomic, strong) UIColor *strokeColor;
@property (nonatomic, assign) NSInteger strokeThickness;

@end
