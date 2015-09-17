package com.example.learn1.ThirdpartyInsist;

import com.tencent.open.utils.HttpUtils;
import com.tencent.tauth.IRequestListener;
import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;

/**
 * Created by a0153-00401 on 15/9/9.
 */
public class TecentPostListener implements IRequestListener {


    @Override
    public void onComplete(JSONObject var1) {

    }

    @Override
    public void onIOException(IOException var1){

    }

    @Override
    public void onMalformedURLException(MalformedURLException var1){

    }

    @Override
    public  void onJSONException(JSONException var1){

    }

    @Override
    public void onConnectTimeoutException(ConnectTimeoutException var1)
    {

    }

    @Override
    public void onSocketTimeoutException(SocketTimeoutException var1){

    }

    @Override
    public void onNetworkUnavailableException(HttpUtils.NetworkUnavailableException var1){

    }

    @Override
    public void onHttpStatusException(HttpUtils.HttpStatusException var1){

    }

    @Override
    public void onUnknowException(Exception var1){

    }

    public void onComplete(final JSONObject response, Object state) {
       // showResult("IRequestListener.onComplete:", response.toString());
        doComplete(response, state);
    }
    protected void doComplete(JSONObject response, Object state) {
    }

    public void onIOException(final IOException e, Object state) {
       /// showResult("IRequestListener.onIOException:", e.getMessage());
    }

    public void onMalformedURLException(final MalformedURLException e,
                                        Object state) {
       // showResult("IRequestListener.onMalformedURLException", e.toString());
    }

    public void onJSONException(final JSONException e, Object state) {
        //showResult("IRequestListener.onJSONException:", e.getMessage());
    }
}
