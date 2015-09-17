package com.example.learn1.Activity;



import android.app.Activity;
import android.content.*;
import android.os.Bundle;
import android.view.*;
import android.widget.Button;
import android.widget.ImageButton;


import android.widget.Toast;
import com.example.learn1.CustomComponent.BackKeyCompont;
import com.example.learn1.PublicData.GlobalInfo;
import com.example.learn1.PublicData.Method;
import com.example.learn1.PublicData.Variables;
import com.example.learn1.R;
import com.example.learn1.ThirdpartyInsist.TecentGetingListener;
import com.tencent.connect.common.Constants;
import com.tencent.connect.share.QQShare;
import com.tencent.tauth.Tencent;

import java.util.*;

/**
 * Created by a0153-00401 on 15/7/27.
 */


public class MainActivtyWithRedAlert extends Activity {


    private static final String TAG = "MainActivtyWithRedAlert";
    public static final  String QQ_ID = "1104762927";
    final static String CRAZYIT_ACTION =
            "org.crazyit.intent.action.CRAZYIT_ACTION";
    // 定义一个Category常量
    final static String CRAZYIT_CATEGORY =
            "org.crazyit.intent.category.CRAZYIT_CATEGORY";

    final static public String StatusRecvier =
            "com.oldmanhelper.MainActivtyWithRedAlert.SetAction";

    Stack stClickRecord = new Stack();
    ImageButton redbutton;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    BroadcastReceiver activityReceiver;

    Tencent mTencent;


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

        Toast.makeText(MainActivtyWithRedAlert.this, this.toString(), Toast.LENGTH_SHORT).show();


        mTencent = Tencent.createInstance(QQ_ID, this.getApplicationContext());
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
                Toast toast = Toast.makeText(MainActivtyWithRedAlert.this, "开始发送", Toast.LENGTH_SHORT);
                toast.show();
              /*  Intent intent = new Intent(MainActivtyWithRedAlert.this, MessageSendService.class);
                startService(intent);
                redbutton.setActivated(false);*/
                return false;
            }
        });
        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // MainActivtyWithRedAlert.this.finish();
                // System.out.println("startService  " + stClickRecord.size());

                if (stClickRecord.size() > 0) {
                    long timeLastClick = (long) stClickRecord.peek();
                    //  System.out.println(System.currentTimeMillis() - timeLastClick);
                    if (System.currentTimeMillis() - timeLastClick > 1000) {
                        stClickRecord.clear();
                    }
                }

                stClickRecord.push(System.currentTimeMillis());

                if (stClickRecord.size() > 5) {
                    stClickRecord.clear();
                    Intent intent = new Intent(MainActivtyWithRedAlert.this, MessageSendService.class);
                    startService(intent);
                    Toast toast = Toast.makeText(MainActivtyWithRedAlert.this, "开始发送", Toast.LENGTH_SHORT);
                    toast.show();
                    redbutton.setActivated(false);
                }
            }
        });

        Button qqBtn = (Button)findViewById(R.id.buttonSendQQ);
        qqBtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast toast = Toast.makeText(MainActivtyWithRedAlert.this, "开始发送", Toast.LENGTH_SHORT);
                toast.show();
                shareWithQQ(view);
                return false;
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
                Intent intent_contentClass = new Intent(this, contentClass.class);
                intent_contentClass.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivityForResult(intent_contentClass,Variables.MainATOContact_REQUESTCODE);
                break;
            case R.id.powerManager:
              /*  PowerManager powerManager= (PowerManager) getSystemService(Context.POWER_SERVICE);
                powerManager.*/

                Intent intent_other = new Intent();
                intent_other.setAction("org.crazyit.intent.action.CRAZYIT_ACTION");
                intent_other.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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


    public void shareWithQQ(View view)
    {
        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, "过来帮忙啊");
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY,  "测试用摘要");
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,  "http://www.qq.com/news/1.html");
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,"http://imgcache.qq.com/qzone/space_item/pre/0/66768.gif");
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "oldmanhelp1104762927");
       // params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, "其他附加功能");
        mTencent.shareToQQ(MainActivtyWithRedAlert.this, params, new TecentGetingListener());
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
