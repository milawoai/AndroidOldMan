package com.example.learn1.ThirdpartyInsist;

import android.util.Log;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import org.json.JSONObject;

/**
 * Created by a0153-00401 on 15/9/9.
 */
public class TecentGetingListener implements IUiListener {
        @Override
        public void onComplete(Object response) {
            doComplete((JSONObject)response);
        }

        protected void doComplete(JSONObject values) {
                Log.e("TecentGetingListener", values.toString());
        }
        @Override
        public void onError(UiError e) {
        }
        @Override
        public void onCancel() {
        }
}
