package com.example.learn1;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.telephony.SmsManager;
import com.example.learn1.PublicData.GlobalInfo;

import java.util.Iterator;
import java.util.List;

/**
 * Created by a0153-00401 on 15/8/13.
 */
public class MessageSendService extends IntentService {

    SmsManager smsManager;
    public MessageSendService()
    {
        super("MessageSendService");
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        System.out.println("onHandleIntent");
        smsManager = SmsManager.getDefault();
        final String testMsg = "，这是测试短信";

        if(GlobalInfo.globalContact.size()<=0)
        {
            return;
        }
        Iterator it=GlobalInfo.globalContact.keySet().iterator();
        while(it.hasNext()){
            String key;
            List<String> value;
            key=it.next().toString();
            value=GlobalInfo.globalContact.get(key);
            if(value!=null&&value.size()>0)
            {
                for(String number:value) {
                  /*  PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(), 0);
                    smsManager.sendTextMessage(number,null,key+testMsg,pi,null);*/
                }
            }
        }
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Intent sendIntent = new Intent();
        sendIntent.setAction(MainActivtyWithRedAlert.StatusRecvier);
        sendIntent.putExtra("status",0x11);
        sendBroadcast(sendIntent);
    }
}
