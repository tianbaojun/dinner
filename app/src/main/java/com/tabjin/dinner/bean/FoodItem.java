package com.tabjin.dinner.bean;

import com.tabjin.dinner.api.Contant;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Tabjin on 2016/3/8.
 */
public class FoodItem {



    private int _id;
    private int states;
    private String name ,des,image;
    private int type_id;

    private FoodType type;

    private float price;
    private float rating;
//   加入购物车数量
    private int selectNum;

    private int soldNum;

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public int getSelectNum() {
        return selectNum;
    }

    public void setSelectNum(int selectNum) {
        this.selectNum = selectNum;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getStates() {
        return states;
    }

    public void setStates(int states) {
        this.states = states;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public FoodType getType() {
        return type;
    }

    public void setType(FoodType type) {
        this.type = type;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getSoldNum() {
        return soldNum;
    }

    public void setSoldNum(int soldNum) {
        this.soldNum = soldNum;
    }

    /**
     *
     * @param num  最大为216 ，因为只有216个图片地址
     * @return
     */
    public static List<FoodItem> getFoodItems(int num){

        List<FoodItem> list = new ArrayList<>();
        for(int i = 0;i<num;i++){
            FoodItem foodItem = new FoodItem();
            foodItem.setName("美味" + i);
            foodItem.setSoldNum((int) (1000 * (Math.random())));
            float price = (float)Math.random()*30;
            String priceStr = String.format("%.2f",price);
            foodItem.setPrice(Float.valueOf(priceStr));
            foodItem.setRating((float) (2 + 3 * Math.random()));
            foodItem.setImage(Contant.images.get(i));
            FoodType foodType = Contant.foodTypeList.get(i%8);
            foodItem.setType(foodType);
            foodItem.setType_id(foodType.getId());
            list.add(foodItem);
        }
        return  list;
    }

    /**
     * 通过食品类型进行比较
     */
    public static Comparator<FoodItem> typeCompartor = new Comparator<FoodItem>(){

        @Override
        public int compare(FoodItem lhs, FoodItem rhs) {
            FoodType lType = lhs.getType();
            FoodType rType = rhs.getType();
            FoodType.IdCompartor idCompartor = new  FoodType.IdCompartor();
            return idCompartor.compare(lType,rType);
        }
    };

    /**
     * 通过卖出数量进行比较
     */
    public static Comparator<FoodItem> soldCompartor = new Comparator<FoodItem>(){

        @Override
        public int compare(FoodItem lhs, FoodItem rhs) {
            int lSold = lhs.getSoldNum();
            int rSold = rhs.getSoldNum();
            if(lSold>rSold){
                return -1;
            }else if(lSold==rSold){
                return 0;
            }
            return 1;
        }
    };

    /**
     * 通过价格进行比较
     */
    public static Comparator<FoodItem> priceCompartor = new Comparator<FoodItem>(){

        @Override
        public int compare(FoodItem lhs, FoodItem rhs) {
            float lPrice = lhs.getPrice();
            float rPrice = rhs.getPrice();
            if(lPrice>rPrice){
                return -1;
            }else if(lPrice==rPrice){
                return 0;
            }
            return 1;
        }
    };
    /**
     * 通过评价进行比较
     */
    public static Comparator<FoodItem> ratingCompartor = new Comparator<FoodItem>(){
        @Override
        public int compare(FoodItem lhs, FoodItem rhs) {
            float lRating = lhs.getRating();
            float rRating = rhs.getRating();
            if(lRating>rRating){
                return -1;
            }else if(lRating==rRating){
                return 0;
            }
            return 1;
        }
    };




}
