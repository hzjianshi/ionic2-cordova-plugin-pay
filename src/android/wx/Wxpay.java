package com.lyx.pay.wx;

import android.content.Context;
import android.util.Log;

import com.lyx.pay.Pay;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.json.JSONObject;


/**
 * Created by FollowWinter on 9/15/16.
 */
public class Wxpay extends Pay {
    final IWXAPI msgApi;
    public static String APP_ID;
    public static CallbackContext callbackContext;


  public Wxpay(String app_id,CordovaInterface cordova) {
      APP_ID = app_id;
      msgApi = WXAPIFactory.createWXAPI(cordova.getActivity(), null);
      msgApi.registerApp(APP_ID);
  }

    @Override
    public void pay(CordovaInterface cordova, String param, CallbackContext callbackContext) {
        this.callbackContext = callbackContext;
        try {
            JSONObject json = new JSONObject(param);
            PayReq req = new PayReq();
            req.appId = APP_ID;
            req.partnerId = json.getString("partnerid");
            req.prepayId = json.getString("prepayid");
            req.nonceStr = json.getString("noncestr");
            req.timeStamp = json.getString("timestamp");
            req.packageValue = json.getString("package");
            req.sign = json.getString("sign");
            msgApi.sendReq(req);
        } catch (Exception e) {

        }
    }
}
