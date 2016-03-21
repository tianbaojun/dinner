package com.tabjin.dinner.bean;

import com.tabjin.dinner.R;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Tabjin on 2016/3/7.
 */
public class FoodType {

    private int label;
    private String name;
    private int _id;

    public int getLabel() {
        return label;
    }

    public void setLabel(int label) {
        this.label = label;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return _id;
    }

    public void setId(int id) {
        this._id = id;
    }

    public static List<FoodType> getGv(){
        String[] names = {"川菜","粤菜","北京菜","上海菜","面食","饮料","西餐","小吃"};
        int[] labels = {R.drawable.img10,R.drawable.img130,R.drawable.img132,
                R.drawable.img136,R.drawable.img153,R.drawable.img162,
                R.drawable.img3,R.drawable.img18,};
        List<FoodType> list = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            FoodType foodType = new FoodType();
            foodType.setName(names[i]);
            foodType.setLabel(labels[i]);
            foodType.setId(i);
            list.add(foodType);
        }
        return list;
    }

    public static class IdCompartor implements Comparator<FoodType>{

        @Override
        public int compare(FoodType lhs, FoodType rhs) {
            int lId = lhs.getId();
            int rId = rhs.getId();
            if(lId>rId){
                return -1;
            }else if(lId==rId){
                return 0;
            }
            return 1;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FoodType foodType = (FoodType) o;

        if (label != foodType.label) return false;
        if (_id != foodType._id) return false;
        return !(name != null ? !name.equals(foodType.name) : foodType.name != null);

    }

    @Override
    public int hashCode() {
        int result = label;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + _id;
        return result;
    }
}
