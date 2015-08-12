package com.example.learn1.PublicData;

import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
}
