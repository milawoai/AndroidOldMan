package com.example.learn1.CustomComponent;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by a0153-00401 on 15/8/6.
 */
public class BackKeyCompont {

    public static Activity parentActivity;
    public static AlertDialog.Builder isExit;

    private static BackKeyCompont backCompont;

    private BackKeyCompont( Activity parentactivity)
    {
        parentActivity = parentactivity;
        isExit = new AlertDialog.Builder(parentActivity);
        isExit.setMessage("确定要退出吗");


        // 添加选择按钮并注册监听
        isExit.setPositiveButton("确定", listener);
        isExit.setNegativeButton("取消", listener);

    }

    public static BackKeyCompont Create(Activity parentactivity)
    {
        if(null != backCompont)
        {
            backCompont = null;
        }
        backCompont = new BackKeyCompont(parentactivity);
        return  backCompont;
    }

    public static void Show()
    {
        if(isExit!=null)
        {
            isExit.show();
        }
    }

    DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener()
    {
        public void onClick(DialogInterface dialog, int which)
        {
            switch (which)
            {
                case AlertDialog.BUTTON_POSITIVE:// "确认"按钮退出程序
                    parentActivity.finish();
                    break;
                case AlertDialog.BUTTON_NEGATIVE:// "取消"第二个按钮取消对话框
                    break;
                default:
                    break;
            }
        }
    };
}
