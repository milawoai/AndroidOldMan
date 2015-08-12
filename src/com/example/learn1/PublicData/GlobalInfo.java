package com.example.learn1.PublicData;

import java.util.*;

/**
 * Created by a0153-00401 on 15/8/6.
 */
public class GlobalInfo {
    public static Map<String,List<String>> globalContact = new HashMap<String,List<String>>();

    public static void ContactAdd(ContactInfo e)
    {
        globalContact.put(e.contactName,e.contactPhone);
    }

    public static void ContactRemove(ContactInfo e)
    {
        if(globalContact!=null) {
          //  globalContact.remove(globalContact.indexOf(e));
        }
    }

    public static void RemoveAllElement()
    {
        if(globalContact!=null) {
            globalContact.clear();
        }
    }

    public static void DisplayElement()
    {
        if(globalContact!=null)
        {
            Iterator it=globalContact.keySet().iterator();
            while(it.hasNext()){
                String key;
                String value;
                key=it.next().toString();
                value=globalContact.get(key).toString();
                System.out.println(key+"--"+value);
            }
        }
    }
}
