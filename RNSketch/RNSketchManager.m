//
//  RNSketchManager.m
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

#define ERROR_INVALID_BASE_64 @"ERROR_INVALID_BASE_64"
#define ERROR_UNABLE_SAVE_DRAWING @"ERROR_UNABLE_SAVE_DRAWING"

@implementation RNSketchManager

RCT_EXPORT_MODULE()

#pragma mark - Events

RCT_EXPORT_VIEW_PROPERTY(onChange, RCTBubblingEventBlock);
RCT_EXPORT_VIEW_PROPERTY(onClear, RCTBubblingEventBlock);

#pragma mark - Properties

RCT_EXPORT_VIEW_PROPERTY(fillColor, UIColor);
RCT_EXPORT_VIEW_PROPERTY(imageType, NSString);
RCT_EXPORT_VIEW_PROPERTY(strokeColor, UIColor);
RCT_EXPORT_VIEW_PROPERTY(strokeThickness, NSInteger);
RCT_EXPORT_VIEW_PROPERTY(imageData, NSURL);

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
    self.sketchView = [[RNSketch alloc] initWithFrame:CGRectZero];
  }

  return self.sketchView;
}

#pragma mark - Exported methods

RCT_EXPORT_METHOD(saveDrawing:(NSString *)encodedImage
                  ofType:(NSString *)imageType
                  resolve:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)
{
  // Strip the Base 64 Code out if it's there.
  NSString *base64Code = [self.sketchView base64Code];
  encodedImage = [encodedImage stringByReplacingOccurrencesOfString:base64Code
                                                         withString:@""
                                                            options:NULL
                                                              range:NSMakeRange(0, [base64Code length])];

  // Create image data with base64 source
  NSData *imageData = [[NSData alloc] initWithBase64EncodedString:encodedImage options:NSDataBase64DecodingIgnoreUnknownCharacters];
  if (!imageData) {
    return reject(ERROR_INVALID_BASE_64, @"You need to provide a valid base64 encoded image.", nil);
  }

  // Create full path of image
  NSArray *paths = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
  NSString *documentsDirectory = [paths firstObject];
  NSFileManager *fileManager = [NSFileManager defaultManager];
  NSString *fullPath = [[documentsDirectory stringByAppendingPathComponent:[[NSUUID UUID] UUIDString]] stringByAppendingPathExtension:imageType];

  // Save image and return the path
  BOOL fileCreated = [fileManager createFileAtPath:fullPath contents:imageData attributes:nil];
  if (!fileCreated) {
    return reject(ERROR_UNABLE_SAVE_DRAWING, @"An error occured. Impossible to save the drawing.", nil);
  }

  resolve(@{ @"path": fullPath });
}

RCT_EXPORT_METHOD(clearDrawing:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)
{
  dispatch_async(dispatch_get_main_queue(), ^{
    [self.sketchView clear];
    resolve(@YES);
  });
}

#pragma mark - Class methods

+ (BOOL)requiresMainQueueSetup
{
    return YES;
}

@end
