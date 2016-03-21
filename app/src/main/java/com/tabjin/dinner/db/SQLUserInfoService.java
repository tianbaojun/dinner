package com.tabjin.dinner.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tabjin.dinner.bean.UserInfo;

/**
 * Created by Tabjin on 2016/3/16.
 */
public class SQLUserInfoService {

    private Context context;
    private MyDBHelper helper;

    public SQLUserInfoService(Context context) {
        helper = MyDBHelper.getHelper(context);
        this.context = context;
    }

    /**
     * 插入数据到数据库
     *
     * @param userInfo
     * @return false 注册失败 true 注册成功
     */
    public boolean insert(UserInfo userInfo) {
        SQLiteDatabase db = helper.getWritableDatabase();
        String phone = userInfo.getPhone();
        Cursor cursor = db.rawQuery("select * from t_userInfo where phone = ?",new String[]{phone});
        if(cursor.getCount()>0){
            return false;
        }
        cursor.close();
        db.execSQL("insert into t_userInfo(phone,password) values(?,?)",
                new Object[]{userInfo.getPhone(), userInfo.getPassword()});
    /*    ContentValues values = new ContentValues();
        values.put("phone", userInfo.getPhone());
        values.put("password", userInfo.getPassword());
        db.insert("t_userInfo", null, values);*/
        db.close();
        return true;
    }

    public boolean query(UserInfo userInfo) {
        SQLiteDatabase db = helper.getWritableDatabase();
        String phone = userInfo.getPhone();
        String password = userInfo.getPassword();
        Cursor cursor = db.rawQuery("select * from t_userInfo where phone = ? and password = ?",new String[]{phone,password});
        if(cursor.getCount()>0){
            return true;
        }
        cursor.close();
        db.close();
        return false;
    }

}
