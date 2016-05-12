//
//  UIColor+Hexadecimal.m
//  RNSketch
//
//  Created by Jeremy.Grancher on 11/05/2016.
//  Copyright © 2016 Jeremy Grancher. All rights reserved.
//

#import "UIColor+Hexadecimal.h"

@implementation UIColor (Hexadecimal)

+ (UIColor *)colorWithHexString:(NSString *)hexString
{
  unsigned rgbValue = 0;
  NSScanner *scanner = [NSScanner scannerWithString:hexString];

  // Bypass '#' character if any
  if ([hexString rangeOfString:@"#"].location == 0) {
    [scanner setScanLocation:1];
  }

  [scanner scanHexInt:&rgbValue];

  return [UIColor colorWithRed:((rgbValue & 0xFF0000) >> 16)/255.0
                         green:((rgbValue & 0xFF00) >> 8)/255.0
                          blue:(rgbValue & 0xFF)/255.0
                         alpha:1.0
          ];
}

@end
