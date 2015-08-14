package com.example.learn1;



import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.*;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.*;
import android.widget.ImageButton;


import android.widget.Toast;
import com.example.learn1.CustomComponent.BackKeyCompont;
import com.example.learn1.PublicData.GlobalInfo;
import com.example.learn1.PublicData.Method;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

/**
 * Created by a0153-00401 on 15/7/27.
 */


public class MainActivtyWithRedAlert extends Activity {
    final static String CRAZYIT_ACTION =
            "org.crazyit.intent.action.CRAZYIT_ACTION";
    // 定义一个Category常量
    final static String CRAZYIT_CATEGORY =
            "org.crazyit.intent.category.CRAZYIT_CATEGORY";

    final static String StatusRecvier =
            "com.oldmanhelper.MainActivtyWithRedAlert.SetAction";

    Stack stClickRecord = new Stack();
    ImageButton redbutton;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    BroadcastReceiver activityReceiver;


    public class SetActivityRedAlertButtonStatus extends BroadcastReceiver
    {

        @Override
        public void onReceive(Context context, Intent intent) {
            int status = intent.getIntExtra("status",-1);
            System.out.println("Receiver"+status);
            System.out.println(redbutton.isActivated());
            if(status == 0x11)
            {
                if(redbutton!=null)
                {
                    redbutton.setActivated(true);
                }
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        activityReceiver = new SetActivityRedAlertButtonStatus();
        preferences = getSharedPreferences("Record", MODE_PRIVATE);
        editor = preferences.edit();

        System.out.println("Read");

        String info = preferences.getString("info","");
        GlobalInfo.setGlobalContact(Method.getInfo(info));
        GlobalInfo.DisplayElement();


        setContentView(R.layout.mainwithredalert);

        IntentFilter filter = new IntentFilter();
        // 指定BroadcastReceiver监听的Action
        filter.addAction(StatusRecvier);
        // 注册BroadcastReceiver
        registerReceiver(activityReceiver, filter);

        ImageButton mainButton = (ImageButton)findViewById(R.id.imageButtonTheRedAlert);
        redbutton = mainButton;
        mainButton.setImageResource(R.drawable.redalert);
        mainButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast toast = Toast.makeText(MainActivtyWithRedAlert.this,"开始发送",Toast.LENGTH_SHORT);
                toast.show();
                Intent intent = new Intent(MainActivtyWithRedAlert.this,MessageSendService.class);
                startService(intent);
                redbutton.setActivated(false);
                return false;
            }
        });
        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // MainActivtyWithRedAlert.this.finish();
               // System.out.println("startService  " + stClickRecord.size());

                if(stClickRecord.size()>0) {
                    long timeLastClick = (long) stClickRecord.peek();
                    System.out.println(System.currentTimeMillis() - timeLastClick);
                    if(System.currentTimeMillis() - timeLastClick > 1000)
                    {
                        stClickRecord.clear();
                    }
                }

                stClickRecord.push(System.currentTimeMillis());

                if(stClickRecord.size()>5)
                {
                    stClickRecord.clear();
                    Intent intent = new Intent(MainActivtyWithRedAlert.this,MessageSendService.class);
                    startService(intent);
                    Toast toast = Toast.makeText(MainActivtyWithRedAlert.this,"开始发送",Toast.LENGTH_SHORT);
                    toast.show();
                    redbutton.setActivated(false);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //GlobalInfo.DisplayElement();
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflator = new MenuInflater(this);
        inflator.inflate(R.menu.system_mean, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem mi)
    {
        if(mi.isCheckable())
        {
            mi.setChecked(true);
        }
        switch(mi.getItemId()) {
            case R.id.chooseContant:
                startActivity(new Intent(this, contentClass.class));
            case R.id.testActivity:

               /* startActivity(new Intent(this,TestDoubleActivity.class));*/
               /* Intent intent = new Intent();
                // 设置Action属性
                intent.setAction(MainActivtyWithRedAlert.CRAZYIT_ACTION);
                // 添加Category属性
                intent.addCategory(MainActivtyWithRedAlert.CRAZYIT_CATEGORY);
                startActivity(intent);*/
        }
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode,KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            BackKeyCompont.Create(this).Show();
        }
        return false;
    }

    @Override
    public void onDestroy()
    {
        unregisterReceiver(activityReceiver);
        System.out.println("MainActivtyWithRedAlert Destroy");
        if(!GlobalInfo.isEmpty()&&GlobalInfo.isChanged)
        {
            System.out.println(Method.saveInfoToString(GlobalInfo.globalContact));
            editor.putString("info", Method.saveInfoToString(GlobalInfo.globalContact));
            editor.commit();
        }
        super.onDestroy();
    }
}
