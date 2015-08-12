package com.example.learn1.PublicData;

import java.io.Serializable;
import java.util.List;

/**
 * Created by a0153-00401 on 15/8/6.
 */
public class ContactInfo implements Serializable {
    public String contactName;
    public List<String> contactPhone;

    public ContactInfo(String name,String phone)
    {
        contactName = name;
        contactPhone =  Method.array2List2(phone.split(";"));
    }
}
