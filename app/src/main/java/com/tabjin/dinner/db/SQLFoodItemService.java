package com.tabjin.dinner.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tabjin.dinner.api.Contant;
import com.tabjin.dinner.bean.FoodItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tabjin on 2016/3/16.
 */
public class SQLFoodItemService {

    private Context context;
    private MyDBHelper helper;

    public SQLFoodItemService(Context context) {
        helper = MyDBHelper.getHelper(context);
        this.context = context;
    }

    /**
     * 插入数据到数据库
     *
     * @param foodItem
     * @return false 添加失败 true 添加成功
     */
    public boolean insert(FoodItem foodItem) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("insert into t_foodItem (name,des,image,type_id,price,rating,soldNum) values(?,?,?,?,?,?,?)",
                new Object[]{foodItem.getName(), foodItem.getDes(),
                        foodItem.getImage(), foodItem.getType_id(),
                        foodItem.getPrice(), foodItem.getRating(),
                        foodItem.getSoldNum()});
        db.close();
        return true;
    }

    public FoodItem query(int  foodItemId) {
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from t_foodItem where id = ?", new String[]{String.valueOf(foodItemId)});
        while(cursor.moveToNext()){
            FoodItem foodItem = new FoodItem();
            foodItem.setName(cursor.getString(0));
            foodItem.setDes(cursor.getString(1));
            foodItem.setImage(cursor.getString(2));
            foodItem.setType_id(cursor.getInt(3));
            foodItem.setPrice(cursor.getFloat(4));
            foodItem.setRating(cursor.getFloat(5));
            foodItem.setSoldNum(cursor.getInt(6));
            cursor.close();
            db.close();
            return foodItem;
        }
        cursor.close();
        db.close();
        return null;
    }

    /**
     * 根据ids集合查询foodItem集合
     * @param foodItemIds
     * @return
     */
    public List<FoodItem> queryFooditems(List<Integer>  foodItemIds) {
        SQLiteDatabase db = helper.getWritableDatabase();
        List<FoodItem> list = new ArrayList<>();
        for(int id: foodItemIds) {
            Cursor cursor = db.rawQuery("select * from t_foodItem where id = ?", new String[]{String.valueOf(id)});
            while (cursor.moveToNext()) {
//                (id integer primary key autoincrement,name,des,image,type_id,price,rating,soldNum)
                FoodItem foodItem = new FoodItem();
                foodItem.set_id(cursor.getInt(0));
                foodItem.setName(cursor.getString(1));
                foodItem.setDes(cursor.getString(2));
                foodItem.setImage(cursor.getString(3));
                foodItem.setType_id(cursor.getInt(4));
                foodItem.setPrice(cursor.getFloat(5));
                foodItem.setRating(cursor.getFloat(6));
                foodItem.setSoldNum(cursor.getInt(7));
                foodItem.setType(Contant.foodTypeList.get(foodItem.getType_id()));
                list.add(foodItem);
            }
            cursor.close();
        }
        db.close();
        return list;
    }

    /**
     * 得到全部食物集合
     * @return
     */
    public List<FoodItem> getAllFoodItem(){
//        (id integer primary key autoincrement,name,des,image,type_id,price,rating,soldNum
        SQLiteDatabase db = helper.getWritableDatabase();
        List<FoodItem> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from t_foodItem",null);
        while(cursor.moveToNext()){
            FoodItem foodItem = new FoodItem();
            foodItem.set_id(cursor.getInt(0));
            foodItem.setName(cursor.getString(1));
            foodItem.setDes(cursor.getString(2));
            foodItem.setImage(cursor.getString(3));
            foodItem.setType_id(cursor.getInt(4));
            foodItem.setType(Contant.foodTypeList.get(foodItem.getType_id()));
            foodItem.setPrice(cursor.getFloat(5));
            foodItem.setRating(cursor.getFloat(6));
            foodItem.setSoldNum(cursor.getInt(7));
            list.add(foodItem);
        }
        cursor.close();
        db.close();
        return list;
    }

    /**
     * 初始化数据，将所有菜品添加至数据库
     */
    public void init(){
        SQLiteDatabase db = helper.getWritableDatabase();
        for(FoodItem foodItem: Contant.foodItemList){
            db.execSQL("insert into t_foodItem (name,des,image,type_id,price,rating,soldNum) values(?,?,?,?,?,?,?)",
                    new Object[]{foodItem.getName(), foodItem.getDes(),
                            foodItem.getImage(), foodItem.getType_id(),
                            foodItem.getPrice(), foodItem.getRating(),
                            foodItem.getSoldNum()});
        }
        db.close();
    }

}
