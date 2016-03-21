package com.tabjin.dinner.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tabjin.dinner.R;
import com.tabjin.dinner.bean.FoodItem;

import java.util.List;

/**
 * Created by Tabjin on 2016/3/15.
 */
public class CartAdapter extends BaseAdapter {

    private List<FoodItem> foodItems;
    private Context context;

    public CartAdapter(List<FoodItem> foodItems, Context context) {
        this.foodItems = foodItems;
        this.context = context;
    }

    public void setList( List<FoodItem> foodItems){
        this.foodItems = foodItems;
        notifyDataSetChanged();
    }

    public List<FoodItem> getList(){
        return  foodItems;
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
        FoodItem item = foodItems.get(position);
        ViewHolder vh;
        if(convertView == null){
            vh = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_submit,null);
            vh.name = (TextView) convertView.findViewById(R.id.food_name);
            vh.num = (TextView) convertView.findViewById(R.id.num_price);
            vh.count = (TextView) convertView.findViewById(R.id.count);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        String count = String.format("%.2f", item.getPrice() * item.getSelectNum());
        vh.name.setText(item.getName());
        vh.count.setText(count);
        vh.num.setText("￥"+String.format("%.2f",item.getPrice())+"*"+item.getSelectNum());
        return convertView;
    }

    class ViewHolder{
        TextView name,num,count;
    }
}
