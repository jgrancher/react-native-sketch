//
//  RNSketchManager.m
//  RNSketch
//
//  Created by Jeremy Grancher on 28/04/2016.
//  Copyright © 2016 Jeremy Grancher. All rights reserved.
//

#import "RNSketchManager.h"
#import "RNSketch.h"
#import "RCTBridge.h"
#import "RCTConvert.h"
#import "RCTEventDispatcher.h"

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
  [view setStrokeColor:json ? [RCTConvert UIColor:json] : [UIColor blackColor]];
}
RCT_CUSTOM_VIEW_PROPERTY(initialImagePath, NSString, RNSketch)
{
  [view setInitialImage:json ? [RCTConvert NSString:json] : nil];
}
RCT_EXPORT_VIEW_PROPERTY(strokeThickness, NSInteger)


#pragma mark - Lifecycle


- (UIView *)view
{
  return [[RNSketch alloc] initWithEventDispatcher:self.bridge.eventDispatcher];
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

@end
