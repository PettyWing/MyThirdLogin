package com.example.xieyucheng.mythirdlogin;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.cmcc.aoe.data.AOEError;
import com.cmcc.aoe.push.aoeSDK.AoiCallback;

/**
 * Created by XieYucheng on 2016/9/26.
 */
public class AOECallback implements AoiCallback {
    private static final String TAG = "####TAG";
    private Context mContext;

    AOECallback( Context aContext ){
        mContext = aContext;
    }

    public void onInit( int error, String token ) {
        Log.d( TAG, "onInit = " + error + ", token: " + token );
        if ( error == AOEError.AOE_ERROR_NONE ) {
             Toast.makeText( mContext, "成功注册", Toast.LENGTH_LONG ).show();
        }
        else {
             Toast.makeText( mContext, "注册失败", Toast.LENGTH_LONG ).show();
        }
    }

    @Override
    public void onPostData(int error, byte[] bytes) {}

    @Override
    public void onUnregister(int error) {
        if (error == AOEError.AOE_ERROR_NONE) {
            Log.d(TAG, "成功注销");
        }
    }

    public void onNotifyData( int error, byte[] buff ) {
        String buf = new String( buff );
        Log.d( TAG, "onNotifyData = " + error + " buf:" + buf );
        if ( error == AOEError.AOE_ERROR_NONE ) {
            Toast.makeText( mContext, "收到服务器发送的下行数据 buff = "+ buf, Toast.LENGTH_LONG ).show();
        }
        else {
             Toast.makeText( mContext, "服务器发送的下行数据接收失败", Toast.LENGTH_LONG ).show();
        }
    }

    @Override
    public void onSetPushState(int error) {
        if (error == AOEError.AOE_ERROR_NONE) {
            Log.d(TAG, "Push状态设置成功");
        } else if (error == AOEError.AOE_ERROR_TIME_OUT) {
            Log.d(TAG, "超时");
        } else {
            Log.d(TAG, "Push状态设置失败");
        }
    }

    @Override
    public void onSetTags(int error) {
        if (error == AOEError.AOE_ERROR_NONE) {
            Log.d(TAG, "Tag设置成功");
        } else {
            Log.d(TAG, "Tag设置失败");
        }
    }
}

