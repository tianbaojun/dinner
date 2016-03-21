package com.tabjin.dinner.utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public abstract class SuperBaseAdapter<T> extends BaseAdapter{
	
	/**
	 * BaseAdapter的工具类 
	 * 可适应任何不同的自定义对象T构成的BaseAdapter;
	 */
	protected  List<T>  mlist;
	protected Context  mContext;
	private int layoutID;
	public SuperBaseAdapter(List<T> list,Context context,int layoutID){
		mlist=list;
		mContext=context;
		this.layoutID=layoutID;
	}
	
	public  void setList(List<T> mlist) {
		this.mlist = mlist;
		notifyDataSetChanged();
	}
	
	public  List<T>  getList() {
		return mlist;
	}
	
	@Override
	public int getCount() {
		return mlist.size();
	}

	@Override
	public T getItem(int position) {
		return mlist.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder holder=ViewHolder.get(mContext, convertView, null, layoutID, position);
		convert(holder, getItem(position));
		
		return holder.getConvertView();
	}

	/**
	 * 子类需要重写的方法
	 * @param holder 缓存的View
	 * @param t 具体对象
	 */
	public abstract void convert(ViewHolder holder,T t);
}
