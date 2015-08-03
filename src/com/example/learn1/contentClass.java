package com.example.learn1;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.provider.ContactsContract;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.example.learn1.PublicData.Variables;

/**
 * Created by a0153-00401 on 15/8/1.
 */
public class contentClass extends Activity {
    private ArrayList<HashMap<String,String>> telphoneRegister;
    Thread getcontacts;
    Handler updateListHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            switch (msg.what) {

                case Variables.UPDATE_LIST:
                    updateList();
            }
        }
    };

    private void updateList()
    {

    }
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contentfindbything);
       // SimpleAdapter simpleAdapter = new SimpleAdapter(this)
    }

    class GetContacts implements Runnable{
        @Override
        public void run()
        {
            telphoneRegister = new ArrayList<>();
            Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,null,null,null,null);

            if(cursor.moveToFirst()) {

                int idColum = cursor.getColumnIndex(ContactsContract.Contacts._ID);
                int displayNameColumn = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                do {
                    String contactId = cursor.getString(idColum);
                    String disPlayName = cursor.getString(displayNameColumn);

                    int phoneCount = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                    if (phoneCount > 0) {
                        Cursor phones = getContentResolver().query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                        + " = " + contactId, null, null);
                        if(phones.moveToFirst())
                        {
                            do {
                                String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                if(phoneNumber!=null)
                                {
                                    HashMap<String,String> contect = new HashMap<String,String>();
                                    contect.put(phoneNumber,disPlayName);
                                    telphoneRegister.add(contect);

                                }
                            }while(phones.moveToNext());
                        }
                        phones.close();
                    }
                    else
                    {
                        continue;
                    }
                }while(cursor.moveToNext());
            }
            cursor.close();

            Message msg1=new Message();
            msg1.what=Variables.UPDATE_LIST;
            updateListHandler.sendMessage(msg1);
        }
    }
    HashMap<String, String> getPhoneContacts()
    {
        HashMap<String, String> contectList = new HashMap<>();

        return contectList;
    }
}
