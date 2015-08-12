package com.example.learn1;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * Created by a0153-00401 on 15/7/28.
 */
public class TestDoubleActivity  extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testdoubleactivity);
        Button testButton = (Button)findViewById(R.id.button);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TestDoubleActivity.this.finish();
            }

        });
    }
    //<!---< android:theme="@style/CustomTheme"> -->
}
