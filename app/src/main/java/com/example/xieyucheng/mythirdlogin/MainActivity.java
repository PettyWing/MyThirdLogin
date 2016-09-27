package com.example.xieyucheng.mythirdlogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cmcc.aoe.data.AOEError;
import com.cmcc.aoe.push.aoeSDK.AoiSDK;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private Tencent mTencent;
    private String APP_ID = "1105114651";
    private IUiListener loginListener;
    private String SCOPE = "all";
    private TextView text1;
    private IUiListener userInfoListener;

    private IWXAPI api;
    static final String APP_Wechat_ID = "wxf357ab40af96b05f";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text1 = (TextView)findViewById(R.id.text1);

        AoiSDK mAoiSDK = new AoiSDK();
        AOECallback callback = new AOECallback(getApplicationContext() );
        int error = mAoiSDK.init( getApplicationContext(), "108500866903", callback );
        if (error == AOEError.AOE_ERROR_NO_NETWORK) {
            Log.d("####TAG","注册出错:无网络");
        } else if (error == AOEError.AOE_ERROR_NOT_SUPPORT) {
            Log.d("####TAG","注册出错:不支持无IMEI或MAC地址的手机终端");
        }


    }

    //wechat login
    public void onwechatLoginClick(View v){
        if(api==null){
            api=WXAPIFactory.createWXAPI(MainActivity.this, APP_Wechat_ID, true);
            api.registerApp(APP_Wechat_ID);
        }
        if (!api.isWXAppInstalled()) {
            //提醒用户没有按照微信
            Toast.makeText(MainActivity.this, "没有安装微信,请先安装微信!", Toast.LENGTH_SHORT).show();
            return;
        }

        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_sdk_demo_test";
        api.sendReq(req);
    }



    //qq login
    public void onqqLoginClick(View v){
        initQqLogin();
        mTencent.login(this, SCOPE, loginListener);
    }

    private void initQqLogin(){
        mTencent =  Tencent.createInstance(APP_ID, this);
        //创建QQ登录回调接口
        loginListener = new IUiListener() {
            @Override
            public void onComplete(Object o) {
                //登录成功后回调该方法
                JSONObject jo = (JSONObject) o;
                Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                String openID;
                try {
                    openID = jo.getString("openid");
                    String accessToken = jo.getString("access_token");
                    String expires = jo.getString("expires_in");
                    Toast.makeText(MainActivity.this, openID+"\n"+accessToken, Toast.LENGTH_SHORT).show();
                    mTencent.setOpenId(openID);
                    mTencent.setAccessToken(accessToken, expires);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(UiError uiError) {
                //登录失败后回调该方法
                Toast.makeText(MainActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                Log.e("LoginError:", uiError.toString());
            }

            @Override
            public void onCancel() {
                //取消登录后回调该方法
                Toast.makeText(MainActivity.this, "取消登录", Toast.LENGTH_SHORT).show();
            }
        };
        userInfoListener = new IUiListener() {
            @Override
            public void onComplete(Object o) {
                if(o == null){
                    return;
                }
                try {
                    JSONObject jo = (JSONObject) o;
                    Log.e("JO:",jo.toString());
                    int ret = jo.getInt("ret");
                    String nickName = jo.getString("nickname");
                    String gender = jo.getString("gender");
                    Toast.makeText(MainActivity.this, "你好，" + nickName,Toast.LENGTH_LONG).show();

                } catch (Exception e) {
                }
            }
            @Override
            public void onError(UiError uiError) {
            }
            @Override
            public void onCancel() {
            }
        };
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//  官方文档上面的是错误的
//        if(requestCode == Constants.REQUEST_API) {
//            if(resultCode == Constants.RESULT_LOGIN) {
//                mTencent.handleLoginData(data, loginListener);
//            }
//  resultCode 是log出来的，官方文档里给的那个属性是没有的

        if (requestCode == Constants.REQUEST_LOGIN) {
            if (resultCode == -1) {
                Tencent.onActivityResultData(requestCode, resultCode, data, loginListener);
                Tencent.handleResultData(data, loginListener);
                UserInfo info = new UserInfo(this, mTencent.getQQToken());
                info.getUserInfo(userInfoListener);
            }
        }
    }
}
