package com.example.learn1.CustomComponent;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

/**
 * Created by a0153-00401 on 15/8/14.
 */
public class MessageSendSuccessCompont {
    public static Activity parentActivity;
    public static AlertDialog.Builder isExit;

    private static MessageSendSuccessCompont msCompont;

    private MessageSendSuccessCompont( Activity parentactivity,String sendName)
    {
        parentActivity = parentactivity;
        isExit = new AlertDialog.Builder(parentActivity);
        isExit.setMessage(sendName+"发送成功");
        isExit.setPositiveButton("确定", listener);

    }

    public static MessageSendSuccessCompont Create(Activity parentactivity,String sendName)
    {
        if(null != msCompont)
        {
            msCompont = null;
        }
        msCompont = new MessageSendSuccessCompont(parentactivity,sendName);
        return  msCompont;
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
                    break;
                default:
                    break;
            }
        }
    };
}
