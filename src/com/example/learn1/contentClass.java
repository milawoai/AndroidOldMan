package com.example.learn1;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.provider.ContactsContract;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import java.util.*;

import com.example.learn1.PublicData.ContactInfo;
import com.example.learn1.PublicData.GlobalInfo;
import com.example.learn1.PublicData.Variables;

/**
 * Created by a0153-00401 on 15/8/1.
 */
public class contentClass extends Activity {

    private class ContactItem {
        public String ContactName;
        public String ContactPhone;
        public boolean isChoosed;
    }

    private List<ContactItem> contactList;

    public class ContactAdapt extends BaseAdapter {

        public ContactAdapt()
        {
            datalist = new ArrayList<ContactItem>();
        }

        private class ViewHolder {
            TextView contactNameView;
            TextView contactPhoneView;
            CheckBox clickBox;
        }

        private int Count;
        private List<ContactItem> datalist;

        public void setdata(List<ContactItem> data)
        {
            datalist.clear();
            datalist.addAll(data);
            notifyDataSetChanged();
        }
        @Override
        public int getCount()
        {
            return datalist.size();
        }
        @Override
        public Object getItem(int position) {
            return datalist.get(position);
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public View getView(final int position,View convertView,ViewGroup parent)
        {
            final ContactItem item = datalist.get(position);
            final ViewHolder holder;
            if(convertView == null)
            {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.contentitem,null);

                holder.contactNameView = (TextView)convertView.findViewById(R.id.name);
                holder.contactPhoneView = (TextView)convertView.findViewById(R.id.tel);
                holder.clickBox = (CheckBox)convertView.findViewById(R.id.contactcheckBox);
                convertView.setTag(holder);
            }
            else {
                holder = (ViewHolder) convertView.getTag();
                holder.clickBox.setOnCheckedChangeListener(null);
                convertView.setOnClickListener(null);
            }

            holder.contactNameView.setText(item.ContactName);
            holder.contactPhoneView.setText(item.ContactPhone);
            holder.clickBox.setChecked(item.isChoosed);


            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.clickBox.setChecked(!holder.clickBox.isChecked());
                }
            });

            holder.clickBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    ContactItem e = contactList.get(position);
                    e.isChoosed = b;
                    contactList.set(position, e);
                    e = datalist.get(position);
                    e.isChoosed = b;
                    datalist.set(position, e);
                }
            });

            return convertView;
        }
    }

    Thread getcontacts;
    Handler updateListHandler;
    private ContactAdapt mGridAdapter;

    private void updateList()
    {
        if(contactList!=null) {
            mGridAdapter.setdata(contactList);

          /*  contactlist.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int postion, long id) {
                    System.out.println(postion + "被单击了");
                    TextView contactName = (TextView) view.findViewById(R.id.name);
                    System.out.println(contactName.getText());

                }
            });*/
        }
    }

    private void setContactToGlobalContent()
    {
        GlobalInfo.RemoveAllElement();
        for(int i =0;i<contactList.size();i++)
        {
            ContactItem item = contactList.get(i);
            ContactInfo e;
            if(item.isChoosed) {
                e = new ContactInfo(item.ContactName,item.ContactPhone);
                GlobalInfo.ContactAdd(e);
            }
        }
        GlobalInfo.isChanged = true;
    }
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        DisplayMetrics dm =getResources().getDisplayMetrics();
        int w_screen = dm.widthPixels;
       // int h_screen = dm.heightPixels;
       // int dpi_screen = dm.densityDpi;

        setContentView(R.layout.contentfindbything);
        Button chooseContact = (Button)findViewById(R.id.chooseContactbutton);
        Button cancelContactPage= (Button)findViewById(R.id.cancelContactButton);

        mGridAdapter = new ContactAdapt();
        ListView contactView = (ListView)findViewById(R.id.contactView);
        contactView.setAdapter(mGridAdapter);

        chooseContact.setWidth(w_screen / 3);
        cancelContactPage.setWidth(w_screen / 3);

        chooseContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContactToGlobalContent();
                finish();
                //finishActivity(Variables.MainActivityWithRedAlert_REQUESTCODE);
            }
        });

        cancelContactPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        getcontacts = new Thread(new GetContacts());
        getcontacts.start();

        updateListHandler = new Handler()
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
        // SimpleAdapter simpleAdapter = new SimpleAdapter(this)
    }

    class GetContacts implements Runnable{
        @Override
        public void run()
        {
            contactList = new ArrayList<ContactItem>();
            Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,null,null,null,null);

            if(cursor.moveToFirst()) {
                int idColum = cursor.getColumnIndex(ContactsContract.Contacts._ID);
                int displayNameColumn = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                do {
                    ContactItem contact = new ContactItem();
                    String contactId = cursor.getString(idColum);
                    String disPlayName = cursor.getString(displayNameColumn);
                    int phoneCount = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                    StringBuilder phoneNumberSplit = new StringBuilder();
                    int phoneNumCount = 0;
                    if (phoneCount > 0) {
                        Cursor phones = getContentResolver().query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                        + " = " + contactId, null, null);
                        if(phones.moveToFirst())
                        {
                            do {
                                String phoneNumber = (phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))).replaceAll(" ","");
                                if(phoneNumber!=null)
                                {
                                    if(phoneNumCount>0)
                                    {
                                        phoneNumberSplit.append(";");
                                    }
                                    phoneNumberSplit.append(phoneNumber);
                                    phoneNumCount++;
                                }
                            }while(phones.moveToNext());
                        }
                        phones.close();
                        contact.ContactPhone = phoneNumberSplit.toString();
                        //contact.put("contactPhone", phoneNumberSplit.toString());
                    }
                    else
                    {
                        continue;
                    }
                    contact.ContactName = disPlayName;
                    if(GlobalInfo.globalContact.containsKey(contact.ContactName))
                    {
                        contact.isChoosed = true;
                    }
                    else {
                        contact.isChoosed = false;
                    }

                   // contact.put("contentName", disPlayName);
                    contactList.add(contact);
                }while(cursor.moveToNext());
            }
            cursor.close();


          /*  for(int i =0;i<telphoneRegister.size();i++)
            {
                Map<String,Object> map = telphoneRegister.get(i);
                Iterator it=map.keySet().iterator();
                while(it.hasNext()){
                    String key;
                    String value;
                    key=it.next().toString();
                    value=map.get(key).toString();
                    System.out.println(key+"--"+value);
                }
            }*/

            Message msg1=new Message();
            msg1.what=Variables.UPDATE_LIST;
            updateListHandler.sendMessage(msg1);
        }
    }


    @Override
    public void onDestroy()
    {
        System.out.println("Activty onDestroy");
       // unregisterReceiver(activityReceiver);
       /* unregisterReceiver(activityReceiver);

        if(!GlobalInfo.isEmpty()&&GlobalInfo.isChanged)
        {
            System.out.println(Method.saveInfoToString(GlobalInfo.globalContact));
            editor.putString("info", Method.saveInfoToString(GlobalInfo.globalContact));
            editor.commit();
        }*/
        super.onDestroy();
    }
}
