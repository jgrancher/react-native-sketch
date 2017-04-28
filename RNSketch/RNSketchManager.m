//
//  RNSketchManager.m
//  RNSketch
//
//  Created by Jeremy Grancher on 28/04/2016.
//  Copyright © 2016 Jeremy Grancher. All rights reserved.
//

#import <React/RCTEventDispatcher.h>
#import <React/RCTView.h>
#import <React/UIView+React.h>
#import "RNSketch.h"
#import "RNSketchManager.h"

#define ERROR_IMAGE_INVALID @"ERROR_IMAGE_INVALID"
#define ERROR_FILE_CREATION @"ERROR_FILE_CREATION"

@implementation RNSketchManager

RCT_EXPORT_MODULE()

#pragma mark - Properties


RCT_CUSTOM_VIEW_PROPERTY(fillColor, UIColor, RNSketch)
{
  [view setFillColor:json ? [RCTConvert UIColor:json] : [UIColor whiteColor]];
}
RCT_CUSTOM_VIEW_PROPERTY(strokeColor, UIColor, RNSketch)
{
  [view setStrokeColor:json ? [RCTConvert UIColor:json] : [UIColor clearColor]];
}
RCT_CUSTOM_VIEW_PROPERTY(image, NSURL, RNSketch)
{
    [view setImage:json ?[RCTConvert NSURL:json] : nil];
}
RCT_CUSTOM_VIEW_PROPERTY(clearButtonHidden, BOOL, RNSketch)
{
  [view setClearButtonHidden:json ? [RCTConvert BOOL:json] : NO];
}
RCT_EXPORT_VIEW_PROPERTY(strokeThickness, NSInteger)

#pragma mark - Lifecycle

- (instancetype)init
{
  if ((self = [super init])) {
    self.sketchView = nil;
  }
  
  return self;
}

- (UIView *)view
{
  if (!self.sketchView) {
    self.sketchView = [[RNSketch alloc] initWithEventDispatcher:self.bridge.eventDispatcher];
  }
  
  return self.sketchView;
}

#pragma mark - Event types


- (NSArray *)customDirectEventTypes
{
  return @[
           @"onReset",
           ];
}

#pragma mark - Exported methods

RCT_EXPORT_METHOD(saveImage:(NSString *)encodedImage
                  resolve:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)
{
  // Create image data with base64 source
  NSData *imageData = [[NSData alloc] initWithBase64EncodedString:encodedImage options:NSDataBase64DecodingIgnoreUnknownCharacters];
  if (!imageData) {
    return reject(ERROR_IMAGE_INVALID, @"You need to provide a valid base64 encoded image.", nil);
  }

  // Create full path of image
  NSArray *paths = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
  NSString *documentsDirectory = [paths firstObject];
  NSFileManager *fileManager = [NSFileManager defaultManager];
  NSString *fullPath = [[documentsDirectory stringByAppendingPathComponent:[[NSUUID UUID] UUIDString]] stringByAppendingPathExtension:@"jpg"];

  // Save image and return the path
  BOOL fileCreated = [fileManager createFileAtPath:fullPath contents:imageData attributes:nil];
  if (!fileCreated) {
    return reject(ERROR_FILE_CREATION, @"An error occured. Impossible to save the file.", nil);
  }
  resolve(@{@"path": fullPath});
}

RCT_EXPORT_METHOD(clear)
{
  dispatch_async(dispatch_get_main_queue(), ^{
    [self.sketchView clearDrawing];
  });
}

@end
