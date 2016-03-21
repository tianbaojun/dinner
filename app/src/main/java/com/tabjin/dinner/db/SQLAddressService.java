package com.tabjin.dinner.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tabjin.dinner.bean.Address;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tabjin on 2016/3/16.
 */
public class SQLAddressService {

    private Context context;
    private MyDBHelper helper;

    public SQLAddressService(Context context) {
        helper = MyDBHelper.getHelper(context);
        this.context = context;
    }

    /**
     * 插入数据到数据库
     *
     * @param address
     * @return false 注册失败 true 注册成功
     */
    public boolean insert(Address address) {
        SQLiteDatabase db = helper.getWritableDatabase();
        String phone = address.getPhone();
        String call = address.getCall();
        String name = address.getName();
        String addressStr = address.getAddress();
        db.execSQL("insert into t_address(address,phone,name,call) values(?,?,?,?)",
                new Object[]{addressStr, phone,name,call});
    /*    ContentValues values = new ContentValues();
        values.put("phone", userInfo.getPhone());
        values.put("password", userInfo.getPassword());
        db.insert("t_userInfo", null, values);*/
        db.close();
        return true;
    }

    public List<Address> query(String phone) {

        SQLiteDatabase db = helper.getWritableDatabase();
        List<Address> address = new ArrayList<>();
        Cursor cursor = db.rawQuery("select address,name,call,id from t_address where phone = ?", new String[]{phone});
        while(cursor.moveToNext()) {
            Address address1 = new Address();
            address1.setAddress(cursor.getString(0));
            address1.setName(cursor.getString(1));
            address1.setCall(cursor.getString(2));
            address1.setId(cursor.getInt(3));
            address1.setPhone(phone);
            address.add(address1);
        }
        cursor.close();
        db.close();
        return address;
    }

    /**
     * 更新数据到数据库
     *
     * @param address
     * @return false 注册失败 true 注册成功
     */
    public boolean update(Address address) {
        SQLiteDatabase db = helper.getWritableDatabase();
        String phone = address.getPhone();
        String call = address.getCall();
        String name = address.getName();
        String addressStr = address.getAddress();
        int id = address.getId();
        db.execSQL("update t_address set address= ?, phone=?,name=?,call=? where id=?",
                new Object[]{addressStr, phone, name, call,id});
        db.close();
        return true;
    }

}
