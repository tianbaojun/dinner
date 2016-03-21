package com.tabjin.dinner.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Tabjin on 2016/3/16.
 */
public class Order implements Serializable {

    private String id;
    private String phone;
    private int num;
    private List<Integer> fooditemIds;
    private List<FoodItem> foodItems;
    private Date time;
    private int addressId;

    public Order() {
        id = String.valueOf(new Date().getTime() + 1000 * ((int) Math.random()));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public List<Integer> getFooditemIds() {
        return fooditemIds;
    }

    public void setFooditemIds(List<Integer> fooditemIds) {
        this.fooditemIds = fooditemIds;
    }

    public List<FoodItem> getFoodItems() {
        return foodItems;
    }

    public void setFoodItems(List<FoodItem> foodItems) {
        this.foodItems = foodItems;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }
}
