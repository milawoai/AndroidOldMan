package com.example.learn1;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by a0153-00401 on 15/7/27.
 */
public class MainActivtyWithRedAlert extends Activity {
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
                MainActivtyWithRedAlert.this.finish();
            }

        });
    }

    public boolean onCreateOptionMenu(Menu menu)
    {
        MenuInflater inflator = new MenuInflater(this);
        inflator.inflate(R.menu.system_mean,menu);
        return super.onCreateOptionsMenu(menu);
    }


}
