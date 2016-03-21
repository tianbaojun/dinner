package com.tabjin.dinner.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tabjin.dinner.bean.FoodItem;
import com.tabjin.dinner.bean.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tabjin on 2016/3/16.
 */
public class SQLcartService {

    private Context context;
    private MyDBHelper helper;

    public SQLcartService(Context context) {
        helper = MyDBHelper.getHelper(context);
        this.context = context;
    }

    /**
     * 将order中的食物集合插入数据库
     *
     * @param order 订单
     * @return false 插入失败 true 插入成功
     */
    public boolean insert(Order order) {
        SQLiteDatabase db = helper.getWritableDatabase();
        List<Integer> list = order.getFooditemIds();
        for(Integer id:list){
            List<FoodItem > foodList = order.getFoodItems();
            int num = 0;
            for(FoodItem foodItem:foodList){
                if(foodItem.get_id()==id){
                    num = foodItem.getSelectNum();
                }
            }
            db.execSQL("insert into t_cart (foodItem_id,order_id,num) values(?,?,?)",
                    new String[]{String.valueOf(id), order.getId(), String.valueOf(num)});
        }
        db.close();
        return true;
    }

    public List<Integer> query(Order order) {

        SQLiteDatabase db = helper.getWritableDatabase();
        List<Integer> list = new ArrayList<>();
//        得到的应该是id集合
        Cursor cursor = db.rawQuery("select foodItem_id from t_cart where order_id = ?", new String[]{order.getId()});
        while(cursor.moveToNext()) {
            int id =cursor.getInt(0);
            list.add(id);
        }
        cursor.close();
        db.close();
        return list;
    }

    public int queryNum(int foodItemId, String order_id) {

        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from t_cart where order_id =? and foodItem_id =?", new String[]{order_id, String.valueOf(foodItemId)});
        if (cursor.moveToNext()) {
            int num = cursor.getInt(3);
            cursor.close();
            db.close();
            return num;
        }
        cursor.close();
        db.close();
        return 0;
    }

}
