//
//  RNSketchManager.m
//  RNSketch
//
//  Created by Jeremy Grancher on 28/04/2016.
//  Copyright © 2016 Jeremy Grancher. All rights reserved.
//

#import "RNSketchManager.h"
#import "RCTEventDispatcher.h"
#import "RCTBridge.h"
#import "RNSketch.h"

@implementation RNSketchManager

@synthesize bridge = _bridge;


#pragma mark - React exports properties

RCT_EXPORT_MODULE()


#pragma mark - Lifecycle

- (RNSketch *)view
{
    return [[RNSketch alloc] initWithEventDispatcher:_bridge.eventDispatcher];
}


#pragma mark - Event types

- (NSArray *)customDirectEventTypes
{
    return @[
             @"onReset",
             ];
}

@end
