package com.tabjin.dinner.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tabjin.dinner.R;
import com.tabjin.dinner.bean.Address;

import java.util.List;

/**
 * Created by Tabjin on 2016/3/16.
 */
public class AdderssAdapter extends BaseAdapter {

    private List<Address> addressList;
    private Context context;

    public AdderssAdapter(List<Address> addressList, Context context) {
        this.addressList = addressList;
        this.context = context;
    }

    public void setList(List<Address> addressList){
        this.addressList = addressList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return addressList.size();
    }

    @Override
    public Object getItem(int position) {
        return addressList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return addressList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Address address = addressList.get(position);
        ViewHolder vh ;
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_address, null);
            vh.name = (TextView) convertView.findViewById(R.id.user_name);
            vh.call = (TextView) convertView.findViewById(R.id.user_phone);
            vh.address = (TextView) convertView.findViewById(R.id.user_address);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        vh.name.setText(address.getName());
        vh.call.setText(address.getCall());
        vh.address.setText(address.getAddress());
        return convertView;
    }

    class ViewHolder {
        TextView name,call,address;
    }
}
