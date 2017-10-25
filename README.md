### cordova-plugin-pay

> 其实是给ionic2做的插件，未经过cordova测试，需要将ionic-native替换成我的源，等做完再详细写

### 说明
由于项目问题,该插件暂时停止维护,虽然大部分功能可用,但是,还没有将配置集成到配置文件中,也还没经过完整的项目测试,目前只能算是demo 性的插件,如有需要,可以自行进行改造。之后重新着手`cordova`相关开发之后,会再次开始该插件开发和维护。



#### 调用示例

- 引入LyxPay： `import {StatusBar, Lyxpay,PayCallBack} from 'ionic-native';`
- 继承 `PayCallBack`接口，重写`success`和`failure`方法

```
class CallBack implements PayCallBack{
  success(): any {
    alert("success");
  }

  failure(msg: string): any {
    alert(msg);
  }

}
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


