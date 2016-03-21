package com.tabjin.dinner.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.tabjin.dinner.AllfoodActivity;
import com.tabjin.dinner.R;
import com.tabjin.dinner.api.Contant;
import com.tabjin.dinner.bean.FoodItem;
import com.tabjin.dinner.utils.MyCallback;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;

/**
 * Created by Tabjin on 2016/3/8.
 */
public class FoodItemAdapter extends BaseAdapter {


    private List<FoodItem> foodItems;
    private Context context;
    private View.OnClickListener subOnClickListener,addOnClickListener;

    public FoodItemAdapter(List<FoodItem> foodItems, Context context) {
        this.foodItems = foodItems;
        this.context = context;
    }

    public void setList(List<FoodItem> foodItems) {
        this.foodItems = foodItems;
        notifyDataSetChanged();
    }

    public List<FoodItem> getList() {
        return foodItems;
    }


    @Override

    public int getCount() {
        return foodItems.size();
    }

    @Override
    public Object getItem(int position) {
        return foodItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.allfood_item, null);
            vh = new ViewHolder();
            vh.image = (ImageView) convertView.findViewById(R.id.allfood_item_image);
            vh.name = (TextView) convertView.findViewById(R.id.allfood_item_name);
            vh.type = (TextView) convertView.findViewById(R.id.allfood_item_type);
            vh.sub = (TextView) convertView.findViewById(R.id.sub_bt);
            vh.add = (TextView) convertView.findViewById(R.id.add_bt);
            vh.num = (TextView) convertView.findViewById(R.id.num_tv);
            vh.rating = (RatingBar) convertView.findViewById(R.id.allfood_item_rating);
            convertView.setTag(vh);

//               减少按钮输入框监听
            if (subOnClickListener == null) {
                subOnClickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position  = (int) v.getTag();
                        FoodItem foodItem = foodItems.get(position);
                        TextView tv = (TextView)v.getTag(R.id.sub_bt);
                        if(foodItem.getSelectNum()>0) {
                            foodItem.setSelectNum(foodItem.getSelectNum()-1);
                            tv.setText(String.valueOf(foodItem.getSelectNum()));
                        }
                        if(foodItem.getSelectNum()==0){
                            Contant.cartList.remove(foodItem);
                            notifyDataSetChanged();
                            tv.setText("0");
                        }
                        if(!Contant.cartList.isEmpty()){
                            float count = 0;
                            int num = 0;
                            for(FoodItem item:Contant.cartList){
                                count += item.getPrice()*item.getSelectNum();
                                num += item.getSelectNum();
                            }
                            String countStr = num+"个 ￥"+String.format("%.2f",count);
                            ((AllfoodActivity)context).setCount(countStr);
                        }else{
                            ((AllfoodActivity)context).setCount("0");
                        }
                    }
                };
            }

//            添加按钮输入框监听
            if (addOnClickListener == null) {
                addOnClickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position  = (int) v.getTag();
                        FoodItem foodItem = foodItems.get(position);
                        foodItem.setSelectNum(foodItem.getSelectNum() + 1);
                        if( !Contant.cartList.contains(foodItem)){
                            Contant.cartList.add(foodItem);
                        }
                        TextView tv = (TextView)v.getTag(R.id.add_bt);
                        tv.setText(String.valueOf(foodItem.getSelectNum()));
                        if(!Contant.cartList.isEmpty()){
                            float count = 0;
                            int num = 0;
                            for(FoodItem item:Contant.cartList){
                                count += item.getPrice()*item.getSelectNum();
                                num += item.getSelectNum();
                            }
                            String countStr = num+"个 ￥"+String.format("%.2f",count);
                            ((AllfoodActivity)context).setCount(countStr);
                        }else{
                            ((AllfoodActivity)context).setCount("");
                        }
                    }
                };
            }
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        FoodItem foodItem = foodItems.get(position);
        vh.image.setImageResource(R.drawable.img_big_193);
        x.image().loadDrawable(foodItem.getImage(), ImageOptions.DEFAULT, new MyCallback<Drawable>(vh.image));
        vh.rating.setRating(foodItem.getRating());
        vh.name.setText(foodItem.getName());
        vh.type.setText(foodItem.getType().getName() + "  ￥"+foodItem.getPrice());

//        设置已选中的数量
        vh.num.setText(String.valueOf(foodItem.getSelectNum()));
        vh.sub.setTag(position);
        vh.sub.setTag(R.id.sub_bt,vh.num);
        vh.sub.setOnClickListener(subOnClickListener);
        vh.add.setTag(position);
        vh.add.setTag(R.id.add_bt,vh.num);
        vh.add.setOnClickListener(addOnClickListener);
        return convertView;
    }

    class ViewHolder {
        ImageView image;
        TextView name, type ,sub,add,num;
        RatingBar rating;
    }
}
