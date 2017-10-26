### cordova-plugin-pay

```
* 修改`CDVAppDelegate.m` 新增下面的方法 
```
// ios9 or greater
- (BOOL)application:(UIApplication *)app openURL:(NSURL *)url options:(NSDictionary<NSString*, id> *)options {
    if (!url) {
        return NO;
    }

    NSMutableDictionary * openURLData = [[NSMutableDictionary alloc] init];
    
    [openURLData setValue:url forKey:@"url"];
    
    //options = {UIApplicationOpenURLOptionsOpenInPlaceKey = 0;UIApplicationOpenURLOptionsSourceApplicationKey = "";}
    //if (sourceApplication) {
    //    [openURLData setValue:sourceApplication forKey:@"sourceApplication"];
   // }
    
    //if (annotation) {
    //    [openURLData setValue:annotation forKey:@"annotation"];
    //}
    
    // all plugins will get the notification, and their handlers will be called
    [[NSNotificationCenter defaultCenter] postNotification:[NSNotification notificationWithName:CDVPluginHandleOpenURLNotification object:url]];
    [[NSNotificationCenter defaultCenter] postNotification:[NSNotification notificationWithName:CDVPluginHandleOpenURLWithAppSourceAndAnnotationNotification object:openURLData]];
    
    return YES;
}

// NOTE: 9.0以后使用新API接口
- (BOOL)application:(UIApplication *)app openURL:(NSURL *)url options:(NSDictionary<NSString*, id> *)options
{
    if ([url.host isEqualToString:@"safepay"]) {
        //跳转支付宝钱包进行支付，处理支付结果
        [[AlipaySDK defaultService] processOrderWithPaymentResult:url standbyCallback:^(NSDictionary *resultDic) {
            NSLog(@"result = %@",resultDic);
        }];
    }
    return YES;
}
```
- 调用` Lyxpay.pay(1,data,{success:{},failure:{}});`
    - 参数一是支付类型：1是支付宝，2是微信
    - 参数二是支付字符串
    - 参数三是回调方法


