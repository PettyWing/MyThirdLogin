package com.example.xieyucheng.mythirdlogin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.cmcc.aoe.push.aoeSDK.AoiSDK;

/**
 * Created by XieYucheng on 2016/9/26.
 */
public class AoiReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent ) {
        String action = intent.getAction();
        if ( action.equals( "com.aoe.action.WAKEUP_APP_REBIND" ) ) {
            AoiSDK mAoiSDK = new AoiSDK();
            AOECallback callback = new AOECallback( context.getApplicationContext() );
            mAoiSDK.init( context.getApplicationContext(), "108500866903", callback );
        }
    }
}
