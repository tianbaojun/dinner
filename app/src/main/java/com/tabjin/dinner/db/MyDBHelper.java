package com.tabjin.dinner.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Tabjin on 2016/3/16.
 */
public class MyDBHelper extends SQLiteOpenHelper {
    private MyDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    private static MyDBHelper helper = null;

    @Override
    public void onCreate(SQLiteDatabase db) {

        /**
         * phone 用户账号
         * password 用户密码
         * head 用户头像
         * name 用户名称
         */
        db.execSQL("create table t_userInfo (id integer primary key autoincrement,phone,password,head,name)");

        /**
         * address  收货地址
         * phone 账号
         * name 收货人姓名
         * call 收货人电话
         * id 地址id
         */
        db.execSQL("create table t_address (id integer primary key autoincrement,address,phone,name,call)");

        /**
         * phone 账号
         *  address_id 收货地址id
         *  date 订单生成时间
         *  _id 订单id
         */
        db.execSQL("create table t_order (id integer primary key autoincrement,phone,address_id,date,_id)");

        /**
         * foodItem_id 购物城食品id
         * order_id 订单id
         * num 该食品的购买数量
         */

        db.execSQL("create table t_cart (id integer primary key autoincrement,foodItem_id,order_id,num)");

        /**
         * name 食品名称
         * des 食品描述
         * image 食品图片网址
         * type_id 食品类型id
         * price 食品价格
         * rating 食品评价
         * soldNum 食品售出数量
         */

        db.execSQL("create table t_foodItem (id integer primary key autoincrement,name,des,image,type_id,price,rating,soldNum)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public synchronized static MyDBHelper getHelper(Context context){
        if(helper == null){
            helper = new MyDBHelper(context,"dinner_db",null,1);
        }
        return helper;
    }

}
