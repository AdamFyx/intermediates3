package com.example.intermediates.DB;

import android.content.Context;
import android.util.Log;

import com.example.intermediates.bean.Info;

import java.util.ArrayList;
import java.util.List;

public class SQLFunction {

    static DBHelper helper;

    public static void initTable(Context context) {
        helper = new DBHelper(context);
        helper.getReadableDatabase();
    }

    /*
        向infos表中插入数据
     */
    public static void insert(Context context, Info info) {
        DBManager sqlManager = new DBManager(context);
        helper = new DBHelper(context);
        helper.getWritableDatabase();
        String sql = "insert into infos (name,describe) values"+"("+"'"+info.getName()+"'"+","+"'"+info.getDescribe()+"'"+")";
        sqlManager.updateSQLiteBySql(sql);
    }
    /*
     向infos表中删除数据
  */
    public static void delete(Context context, Info info) {
        DBManager sqlManager = new DBManager(context);
        helper = new DBHelper(context);
        helper.getWritableDatabase();
        String sql = "delete from infos where name = "+"'"+info.getName()+"'";
        Log.d("sas",sql);
        sqlManager.updateSQLiteBySql(sql);
    }   /*
        向user表中修改数据
     */
    public static void update(Context context, Info info) {
        DBManager sqlManager = new DBManager(context);
        helper = new DBHelper(context);
        helper.getWritableDatabase();
        String sql = "update infos set describe="+"'"+info.getDescribe()+"'"+"where name="+"'"+info.getName()+"'";
        sqlManager.updateSQLiteBySql(sql);
    }   /*
        向user表中查询数据
     */
    public static ArrayList<Info> select(Context context) {
        ArrayList<Info> infoList=new ArrayList<>();
        DBManager sqlManager = new DBManager(context);
        helper = new DBHelper(context);
        helper.getWritableDatabase();
        String sql = "select * from infos";
        infoList=sqlManager.selectAllInfosData();
        return  infoList;
    }


}
