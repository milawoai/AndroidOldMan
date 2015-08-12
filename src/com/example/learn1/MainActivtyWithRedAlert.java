package com.example.learn1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.ImageButton;
import com.example.learn1.CustomComponent.BackKeyCompont;

/**
 * Created by a0153-00401 on 15/7/27.
 */
public class MainActivtyWithRedAlert extends Activity {
    final static String CRAZYIT_ACTION =
            "org.crazyit.intent.action.CRAZYIT_ACTION";
    // 定义一个Category常量
    final static String CRAZYIT_CATEGORY =
            "org.crazyit.intent.category.CRAZYIT_CATEGORY";

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainwithredalert);
        ImageButton mainButton = (ImageButton)findViewById(R.id.imageButtonTheRedAlert);
        mainButton.setImageResource(R.drawable.redalert);
        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // MainActivtyWithRedAlert.this.finish();
            }

        });
    }

    @Override
    public void OnResume()
    {

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
                Log.d("selected:", mi.toString());
                startActivity(new Intent(this,contentClass.class));
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


}
