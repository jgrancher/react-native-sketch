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

- (instancetype)initWithFrame:(CGRect)frame NS_UNAVAILABLE;
- (instancetype)initWithCoder:(NSCoder *)aDecoder NS_UNAVAILABLE;
- (instancetype)initWithEventDispatcher:(RCTEventDispatcher *)eventDispather NS_DESIGNATED_INITIALIZER;

@end