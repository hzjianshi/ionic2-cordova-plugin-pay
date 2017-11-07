package com.lyx.pay.ali;


import android.content.Context;
import android.text.TextUtils;
import com.alipay.sdk.app.PayTask;
import com.lyx.pay.Pay;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;


/**
 * Created by FollowWinter on 9/15/16.
 */
public class Alipay extends Pay{
    private Context context;
    @Override
    public void pay(final CordovaInterface cordova, final String param, final CallbackContext callbackContext) {
        cordova.getThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(cordova.getActivity());
                // 调用支付接口，获取支付结果
                String result = alipay.pay(param, true);
                PayResult payResult = new PayResult(result);

//                String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                String resultStatus = payResult.getResultStatus();

                if (TextUtils.equals(resultStatus, "9000")) {
                    callbackContext.success();
                } else {
                    callbackContext.error(resultStatus);
                }
            }
        });
    }
}
