//
//  RNSketchManager.m
//  RNSketch
//
//  Created by Jeremy Grancher on 28/04/2016.
//  Copyright © 2016 Jeremy Grancher. All rights reserved.
//

#if __has_include(<React/RCTViewManager.h>)
// React Native >= 0.40
#import <React/RCTViewManager.h>
#else
// React Native <= 0.39
#import "RCTViewManager.h"
#endif
#import "RNSketch.h"

@interface RNSketchManager : RCTViewManager

@property (strong) RNSketch *sketchView;

@end;
