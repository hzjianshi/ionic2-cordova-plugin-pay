package com.lyx.pay;

import android.util.Log;

import com.lyx.pay.ali.Alipay;
import com.lyx.pay.wx.Wxpay;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by FollowWinter on 9/15/16.
 */
public class PayFactory extends CordovaPlugin{
  public static final int TYPE_WXPAY=1;
    public static final int TYPE_ALIPAY=2;

  private String wx_app_id;
  private String alipay_app_id;
    private Pay pay;

  @Override
  public void initialize(CordovaInterface cordova, CordovaWebView webView) {
    wx_app_id = preferences.getString("wx_app_id","");
    alipay_app_id = preferences.getString("ALIPAY_APP_ID","");

    super.initialize(cordova, webView);
  }
  @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if ("pay".equals(action)){
            int type=args.getInt(0);
            String param=args.getString(1);
            switch (type){
                case TYPE_ALIPAY:{
                    pay=new Alipay();
                    break;
                }
                case TYPE_WXPAY:{
                    pay=new Wxpay(wx_app_id,cordova);
                    break;
                }
            }
            pay.pay(cordova,param,callbackContext);
            return true;
        }

        return  false;
    }
}
