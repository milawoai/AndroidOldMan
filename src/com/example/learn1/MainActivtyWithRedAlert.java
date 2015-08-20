package com.example.learn1;



import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.*;
import android.os.Bundle;
import android.provider.MediaStore;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.*;
import android.widget.ImageButton;
import android.os.PowerManager;


import android.widget.Toast;
import com.example.learn1.CustomComponent.BackKeyCompont;
import com.example.learn1.PublicData.GlobalInfo;
import com.example.learn1.PublicData.Method;
import com.example.learn1.PublicData.Variables;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

/**
 * Created by a0153-00401 on 15/7/27.
 */


public class MainActivtyWithRedAlert extends Activity {


    private static final String TAG = "MainActivtyWithRedAlert";
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

        System.out.println("Application Start");
        System.out.println("Task ID:" + this.getTaskId());
        System.out.println("Class Name:" + this.toString());
        Toast.makeText(MainActivtyWithRedAlert.this, this.toString(), Toast.LENGTH_SHORT).show();
        System.out.println("Activty onCreate");
        activityReceiver = new SetActivityRedAlertButtonStatus();
        preferences = getSharedPreferences("Record", MODE_PRIVATE);
        editor = preferences.edit();

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
                  //  System.out.println(System.currentTimeMillis() - timeLastClick);
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
        System.out.println("Activty onResume");
        super.onResume();
        //GlobalInfo.DisplayElement();
    }

    @Override
    protected void onRestart() {
        System.out.println("Activty onRestart");
        super.onRestart();
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
                System.out.println("start new Activity");
                startActivityForResult(new Intent(this, contentClass.class), Variables.MainATOContact_REQUESTCODE);
                break;
            case R.id.powerManager:
              /*  PowerManager powerManager= (PowerManager) getSystemService(Context.POWER_SERVICE);
                powerManager.*/

                Intent intent_other = new Intent();
                intent_other.setAction("org.crazyit.intent.action.CRAZYIT_ACTION");
                startActivity(intent_other);
                break;
            case R.id.testActivity:

               /* startActivity(new Intent(this,TestDoubleActivity.class));*/
                Intent intent = new Intent();
                // 设置Action属性
                //Intent intent = new Intent(;//调用android自带的照相机
                intent.setAction("android.intent.action.MAIN");
               // intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                //intent.setAction(Intent.ACTION_GET_CONTENT);
                //intent.setType("vnd.android.cursor.item/phone");
                // 添加Category属性
                //intent.addCategory(MainActivtyWithRedAlert.CRAZYIT_CATEGORY);
                startActivityForResult(intent,0);
                break;
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
        System.out.println("Activty onDestroy");
        unregisterReceiver(activityReceiver);
       /* unregisterReceiver(activityReceiver);

        if(!GlobalInfo.isEmpty()&&GlobalInfo.isChanged)
        {
            System.out.println(Method.saveInfoToString(GlobalInfo.globalContact));
            editor.putString("info", Method.saveInfoToString(GlobalInfo.globalContact));
            editor.commit();
        }*/
        super.onDestroy();
    }

    @Override
    public void onStop()
    {
        System.out.println("Activty onStop");
        super.onStop();
    }

    @Override
    public void onStart()
    {
        System.out.println("Activty onStart");
        super.onStart();
    }

    @Override
    public void onPause()
    {
        System.out.println("Activty onPause");
        super.onStart();
    }


    @Override
    protected void onNewIntent(Intent intent)
    {
        System.out.println("Task ID:" + this.getTaskId());
        System.out.println("Activty onNewIntent");
        System.out.println("Class Name:" + this.toString());
        Toast.makeText(MainActivtyWithRedAlert.this, this.toString(), Toast.LENGTH_SHORT).show();
        super.onNewIntent(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // Log.e(TAG, String.format("requestCode %d,resultCode %d", requestCode,resultCode));
        if(!GlobalInfo.isEmpty()&&GlobalInfo.isChanged)
        {
         //   System.out.println(Method.saveInfoToString(GlobalInfo.globalContact));
            editor.putString("info", Method.saveInfoToString(GlobalInfo.globalContact));
            editor.commit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        System.out.println("onSaveInstanceState");
        System.out.println(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){

        System.out.println("onRestoreInstanceState");
        System.out.println(savedInstanceState);
    }
}
