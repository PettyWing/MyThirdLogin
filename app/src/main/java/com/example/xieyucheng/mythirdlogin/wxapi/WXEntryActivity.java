package com.example.xieyucheng.mythirdlogin.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * Created by XieYucheng on 2016/9/26.
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler{

    private IWXAPI api;
    static final String APP_ID = "wxf357ab40af96b05f";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.flash_activity);

        if(api==null){
            //wx30a9402cd2a87cf3
            api=WXAPIFactory.createWXAPI(this, APP_ID, false);
            api.registerApp(APP_ID);
        }

        api.handleIntent(getIntent(), this);
        handleIntent(getIntent());
    }

    // 微信发送请求到第三方应用时，会回调到该方法
    @Override
    public void onReq(BaseReq req) {
        switch (req.getType()) {
            case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:
//          goToGetMsg();
                break;
            case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:
//          goToShowMsg((ShowMessageFromWX.Req) req);
                break;
            default:
                break;
        }
    }

    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
    @Override
    public void onResp(BaseResp resp) {
//      int result = 0;
        String result="";
        String code = "fail";
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                code = ((SendAuth.Resp)resp).code;
                result = "errcode_success";
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = "errcode_cancel";
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = "errcode_deny";
                break;
            default:
                result = "errcode_unknown";
                break;
        }
        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
        Toast.makeText(this, code, Toast.LENGTH_LONG).show();

    }



    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        SendAuth.Resp resp = new SendAuth.Resp(intent.getExtras());
        if (resp.errCode == BaseResp.ErrCode.ERR_OK) {
            //用户同意
        }
    }
}