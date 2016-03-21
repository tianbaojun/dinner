package com.tabjin.dinner.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tabjin.dinner.R;
import com.tabjin.dinner.api.Contant;
import com.tabjin.dinner.bean.FoodItem;
import com.tabjin.dinner.bean.Order;
import com.tabjin.dinner.utils.MyCallback;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Tabjin on 2016/3/17.
 */
public class OrderAdapter extends BaseAdapter {

    private List<Order> orders;
    private Context context;

    public OrderAdapter(Context context,List<Order> orders ) {
        this.orders = orders;
        this.context = context;
    }

    public void setList( List<Order> orders){
        this.orders = orders;
    }

    @Override
    public int getCount() {
        return orders.size();
    }

    @Override
    public Object getItem(int position) {
        return orders.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Order order = orders.get(position);
        ViewHolder vh;
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_order, null);
            vh.name = (TextView) convertView.findViewById(R.id.foodItem);
            vh.summary = (TextView) convertView.findViewById(R.id.summary);
            vh.id = (TextView) convertView.findViewById(R.id.id);
            vh.time = (TextView) convertView.findViewById(R.id.time);
            vh.image = (ImageView) convertView.findViewById(R.id.image);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.id.append(order.getId());
        vh.time.setText(new SimpleDateFormat("MM-dd hh:mm").format(order.getTime()));
        List<FoodItem> foodItems = order.getFoodItems();
        StringBuilder name = new StringBuilder();
        String image = null;
        float summary = 0f;
//        // TODO: 2016/3/17 菜名及总价问题
        for(FoodItem item:foodItems){
            summary+=item.getPrice()*item.getSelectNum();
            name.append(item.getName()+"  ");
            image = Contant.foodItemList.get(item.get_id()).getImage();
        }

        vh.summary.append(String.format("%.2f", summary));
        vh.name.setText(name.toString());
        x.image().loadDrawable(image, ImageOptions.DEFAULT, new MyCallback< Drawable>(vh.image));
        return convertView;
    }

    class ViewHolder {
        TextView id,time,summary,name;
        ImageView image;
    }
}
