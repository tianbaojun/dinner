package com.tabjin.dinner.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tabjin.dinner.bean.FoodItem;
import com.tabjin.dinner.bean.Order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Tabjin on 2016/3/16.
 */
public class SQLOrderService {

    private Context context;
    private MyDBHelper helper;

    public SQLOrderService(Context context) {
        helper = MyDBHelper.getHelper(context);
        this.context = context;
    }

    /**
     * 插入数据到数据库
     *
     * @param order
     * @return false 插入失败 true 插入成功
     */
    public boolean insert(Order order) {

        SQLcartService sqLcartService = new SQLcartService(context);
        sqLcartService.insert(order);
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("insert into t_order (phone,address_id,date,_id) values(?,?,?,?)",
                new Object[]{order.getPhone(), order.getAddressId(),order.getTime().getTime(),order.getId()});
        db.close();
        return true;
    }

    public List<Order> query(String phone) {

        SQLiteDatabase db = helper.getWritableDatabase();
        List<Order> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from t_order where phone = ?", new String[]{phone});
        while(cursor.moveToNext()) {
            // TODO: 2016/3/19
//            (id integer primary key autoincrement,phone,address_id,date,_id)
            String phoneStr = cursor.getString(1);
            int addressId = cursor.getInt(2);
            String date = cursor.getString(3);
            String _id = cursor.getString(4);
            Order order = new Order() ;
            order.setPhone(phoneStr);
            order.setAddressId(addressId);
            order.setId(_id);
            order.setTime(new Date(Long.valueOf(date)));
//            利用order id得到购物车内容
            SQLcartService sqLcartService = new SQLcartService(context);
            List<Integer> foodItemIds = sqLcartService.query(order);
            order.setFooditemIds(foodItemIds);
            SQLFoodItemService sqlFoodItemService = new SQLFoodItemService(context);
            List<FoodItem> foodItems = sqlFoodItemService.queryFooditems(foodItemIds);
            for(FoodItem foodItem:foodItems){
                foodItem.setSelectNum(sqLcartService.queryNum(foodItem.get_id(),order.getId()));
            }
            order.setFoodItems(foodItems);
            list.add(order);
        }
        cursor.close();
        db.close();
        return list;
    }

}
