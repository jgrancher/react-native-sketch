//
//  RNSketchManager.m
//  RNSketch
//
//  Created by Jeremy Grancher on 28/04/2016.
//  Copyright © 2016 Jeremy Grancher. All rights reserved.
//

#import "RCTViewManager.h"

@interface RNSketchManager : RCTViewManager

@property (nonatomic, strong) NSString *fillColor;
@property (nonatomic, strong) NSString *strokeColor;
@property (nonatomic, assign) NSInteger strokeThickness;

@end;
