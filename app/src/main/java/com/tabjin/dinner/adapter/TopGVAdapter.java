package com.tabjin.dinner.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tabjin.dinner.R;
import com.tabjin.dinner.bean.FoodType;

import java.util.List;

/**
 * Created by Tabjin on 2016/3/7.
 */
public class TopGVAdapter extends BaseAdapter {

    private List<FoodType> types ;
    private Context context ;

    public TopGVAdapter(List<FoodType> types, Context context) {
        this.types = types;
        this.context = context;
    }

    public void setList(List<FoodType> types) {
        this.types = types;
        notifyDataSetChanged();
    }

    public List<FoodType> getList() {
        return  types;
    }

    @Override
    public int getCount() {
        return types.size();
    }

    @Override
    public FoodType getItem(int position) {
        return types.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.allfood_top_gv,null);
            vh = new ViewHolder();
            vh.image = (ImageView) convertView.findViewById(R.id.top_gv_im);
            vh.tv = (TextView) convertView.findViewById(R.id.top_gv_tv);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        vh.image.setImageResource(getItem(position).getLabel());
        vh.tv.setText(getItem(position).getName());
        return convertView;
    }

    class ViewHolder {
        ImageView image;
        TextView tv;
    }
}
