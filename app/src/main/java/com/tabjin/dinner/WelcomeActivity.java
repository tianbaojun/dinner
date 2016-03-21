package com.tabjin.dinner;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.tabjin.dinner.api.Contant;
import com.tabjin.dinner.bean.FoodItem;
import com.tabjin.dinner.bean.FoodType;
import com.tabjin.dinner.db.SQLFoodItemService;
import com.tabjin.dinner.utils.CoreUtil;
import com.tabjin.dinner.utils.ImageRes;
import com.tabjin.dinner.utils.MyCallback;

import org.xutils.image.ImageOptions;
import org.xutils.x;

/**
 * Created by Tabjin on 2016/3/9.
 */
public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        new Thread(){
            @Override
            public void run() {
                init();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                CoreUtil.startActivity(WelcomeActivity.this, MainActivity.class);
                WelcomeActivity.this.finish();
            }
        }.start();
    }

    private void init(){
        SharedPreferences sharedPreferences = getSharedPreferences("dinner",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        SQLFoodItemService sqlFoodItemService = new SQLFoodItemService(this);
        x.image().loadDrawable(Contant.WELCOME, ImageOptions.DEFAULT,new MyCallback<Drawable>((ImageView)findViewById(R.id.online)));
        if(sharedPreferences.getInt("num",0)==0){
            Contant.images = ImageRes.getImages(WelcomeActivity.this);
            Contant.foodTypeList = FoodType.getGv();
            Contant.foodItemList = FoodItem.getFoodItems(Contant.foodNum);
            sqlFoodItemService.init();
            Contant.foodItemList = sqlFoodItemService.getAllFoodItem();
            editor.putInt("num",1);
            editor.commit();
        }else{
            Contant.images = ImageRes.getImages(WelcomeActivity.this);
            Contant.foodTypeList = FoodType.getGv();
            Contant.foodItemList = sqlFoodItemService.getAllFoodItem();
        }


    }
}
