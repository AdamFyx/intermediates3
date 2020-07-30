package com.example.intermediates.bean;

import android.content.Context;
import android.util.Log;
import android.webkit.JavascriptInterface;

import com.example.intermediates.DB.SQLFunction;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MyObject {
    public static final String TAG = MyObject.class.getSimpleName() ;
    private Context mContext;
    private String data;
    SQLFunction sqlFunction=new SQLFunction();
    public MyObject(Context c,String data){
        this.data = data;
        mContext = c;
    }

    /**
     * 获取info字符串传Html
     * @return
     */
    @JavascriptInterface
    public String getData(){
        List<Info> mlist = new ArrayList<>();
        mlist=sqlFunction.select(mContext);
        Gson gson = new Gson();
        String d  = gson.toJson(mlist);
        Log.d(TAG, "getData: dddd"+d);
        return d;
    }
}
