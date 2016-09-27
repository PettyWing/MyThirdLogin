package com.example.xieyucheng.mythirdlogin;

import android.content.Context;
import android.content.Intent;

import com.tencent.connect.common.AssistActivity;

/**
 * Created by XieYucheng on 2016/9/27.
 */
public class QQManager {
    private Context mContext;
    private Intent mIntent;
    public QQManager(Context context){
        mContext = context;
        mIntent = new Intent(mContext,AssistActivity.class);

    }

}
