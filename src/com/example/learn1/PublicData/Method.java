package com.example.learn1.PublicData;

import android.content.Context;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

/**
 * Created by a0153-00401 on 15/8/6.
 */
public class Method {
    /**数组转换为ArrayList**/
    public static List array2List2(Object[] arr) {
        List list = new ArrayList();
        if (arr == null) return list;
        list = Arrays.asList(arr);
        return list;
    }

    /**密度转换为像素**/
    public static int dip2px(Context context, float dipValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dipValue * scale + 0.5f);
    }

    /****/
    public static int px2dip(Context context, float pxValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(pxValue / scale + 0.5f);
    }

    public static String saveInfoToString( Map<String, List<String>> datas) {
        JSONObject jsonMapObject = new JSONObject();
        Iterator it= datas.keySet().iterator();
        while(it.hasNext()){
            String savekey;
            List<String> saveValue;

            savekey=it.next().toString();
            saveValue=datas.get(savekey);

            JSONArray phoneArray = new JSONArray();
            for (String savePhone : saveValue)
            {
                phoneArray.put(savePhone);
            }
            try {
                jsonMapObject.put(savekey, phoneArray);
            } catch (JSONException e) {

            }
        }
        return jsonMapObject.toString();
    }


    public static Map<String, List<String>> getInfo(String info)
    {
        Map<String, List<String>> datas = new HashMap<String, List<String>>();
        if(info == "")
        {
            return  datas;
        }

        try
        {
            JSONObject mapInfo = new JSONObject(info);
            System.out.println(mapInfo.toString());
            JSONArray arrayName = mapInfo.names();
            for(int i = 0;i<arrayName.length();i++)
            {
                String Name = arrayName.getString(i);
                JSONArray arrayPhone = mapInfo.getJSONArray(Name);
                List<String> phone = new ArrayList<String>();
                for(int j = 0;j<arrayPhone.length();j++)
                {
                    phone.add(arrayPhone.getString(j));
                    System.out.println(arrayPhone.getString(j));
                }
                datas.put(Name,phone);
            }
        }
        catch (JSONException e)
        {

        }
        return  datas;
    }

    public static boolean isSame(String phoneName,List<String> phoneList)
    {
        List<String> phoneFromName = array2List2(phoneName.split(";"));

        return false;
    }
}
