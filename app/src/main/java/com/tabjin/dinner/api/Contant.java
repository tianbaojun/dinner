package com.tabjin.dinner.api;

import com.tabjin.dinner.bean.FoodItem;
import com.tabjin.dinner.bean.FoodType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tabjin on 2016/3/8.
 */
public class Contant {

    public static String WELCOME = "http://img1.gtimg.com/ln/pics/hv1/204/135/1329/86452854.jpg";

    public static String[] FLIPPER = {"http://pic25.nipic.com/20121116/11223607_164950387319_2.jpg",
            "http://pic29.nipic.com/20130523/9412630_105614930000_2.jpg",
            "http://pic26.nipic.com/20130115/4678141_092028172100_2.jpg"};

    public static String[] HOMEPAGE = {
            "http://pic3.nipic.com/20090605/798308_210935099_2.jpg",
            "http://pic3.nipic.com/20090603/798308_225559027_2.jpg",
            "http://pic3.nipic.com/20090603/798308_223238038_2.jpg"};

    public static String[] ALLFOOD = {
            "http://i04.pic.sogou.com/b5f64181d85da70a",
            "http://i02.pic.sogou.com/f17b1291e12c2415"
    };

    public static List<FoodItem> cartList;

    static {
        cartList  = new ArrayList<>();
    }


    public static String USER_INFOR = "USER_INFOR";
    public static String REGISTER_INFOR = "REGISTER_INFOR";
    public static String address = "address";

    public static  List<FoodItem> foodItemList;

    public static List<FoodType> foodTypeList;




    public static final String imagesRes = "image.txt";

    public static List<String > images ;

    public static int foodNum = 200;

    public static List<FoodItem> getItemList(FoodType type){
        List<FoodItem> list = new ArrayList<>();
        for(FoodItem item:foodItemList){
            if(item.getType().equals(type)){
                list.add(item);
            }
        }
        return  list;
    }

}
