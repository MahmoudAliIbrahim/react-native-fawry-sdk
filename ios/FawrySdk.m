#import <React/RCTBridgeModule.h>

@interface RCT_EXTERN_MODULE(FawrySdk, NSObject)

RCT_EXTERN_METHOD(initFawry:(NSString)style
                 withResolver:(RCTPromiseResolveBlock)resolve
                 withRejecter:(RCTPromiseRejectBlock)reject)

+ (BOOL)requiresMainQueueSetup
{
  return YES;
}

@end
