package com.example.intermediates.DB;

import android.content.Context;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.intermediates.bean.Info;

import java.util.ArrayList;


public class DBManager {
    String TAG = "DBManger";
    DBHelper helper;
    SQLiteDatabase sqLiteDatabase;

    public DBManager(Context context) {
        helper = new DBHelper(context);
        sqLiteDatabase = helper.getReadableDatabase();
    }

    /**
     * execSql()方法可以执行 Insert,update,delete语句
     * 实现对数据库的增删改功能
     * sql为操作语句 bindArgs为操作传递参数
     */
    public boolean updateSQLite(String sql, Object[] bindArgs) {
        Log.d("Dbmanager", sql + ";" + bindArgs);
        boolean isSuccess = false;
        try {
            sqLiteDatabase.execSQL(sql, bindArgs);
            isSuccess = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if ((sqLiteDatabase != null)) {
                sqLiteDatabase.close();
            }
        }
        return isSuccess;
    }
    /**
     * execSql()方法可以执行 Insert,update,delete语句
     只用语句不用参数
     */
    public boolean updateSQLiteBySql(String sql) {
        boolean isSuccess = false;
        try {
            sqLiteDatabase.execSQL(sql);
            isSuccess = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if ((sqLiteDatabase != null)) {
                sqLiteDatabase.close();
            }
        }
        return isSuccess;
    }

    /**
     * 查询全部数据
     * SQL 语句中, asc是指定列按升序排列，desc则是指定列按降序排列。
     */
    public ArrayList<Info> selectAllInfosData() {
        //查询全部数据
        Cursor cursor = sqLiteDatabase.query("infos", null, null, null, null, null, null, null);
        ArrayList<Info> list = new ArrayList<Info>();
        if (cursor.getCount() > 0) {
            //移动到首位
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                 Info info=new Info();
                info.setName(cursor.getString(cursor.getColumnIndex("name")));
                info.setDescribe(cursor.getString(cursor.getColumnIndex("describe")));
                list.add(info);
                //移动到下一位
                cursor.moveToNext();
            }
        }
        cursor.close();
        return list;
    }
}
