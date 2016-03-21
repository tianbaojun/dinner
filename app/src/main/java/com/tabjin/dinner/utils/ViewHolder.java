package com.tabjin.dinner.utils;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.tabjin.dinner.widget.RoundImageView;

public class ViewHolder{
	
	/**
	 * 与SuperBaseAdapter一起使用，
	 * 用以缓存，优化BaseAdapter;
	 * set方法待完工；
	 */
	private SparseArray<View> mViews; //特殊的集合类型 key 是int型
	private int mPosition;
	private View mConvertView;
	private Context mContext;
	
	public ViewHolder(Context context,
			ViewGroup parent,int layouID,int position){
		this.mPosition=position;
		this.mContext=context;
		this.mConvertView=LayoutInflater.from(context).inflate(layouID, parent);
		this.mViews=new SparseArray<View>();
		mConvertView.setTag(this);
	}
	
	public static ViewHolder get(Context context,View convertView,ViewGroup parent,int layouID,int position){
		if(convertView==null){
			return new ViewHolder(context, parent, layouID, position);
		}else{
			ViewHolder holder=(ViewHolder) convertView.getTag();
			holder.mPosition=position;
			return holder;
		}
	}
	
	public Context getContext(){
		return mContext;
	}
	/**
	 * 通过resID获取控件
	 * @param resID
	 * @return 对应类型的组件
	 */
	@SuppressWarnings("unchecked")
	public <T extends View > T getView(int resID){
		View view =mViews.get(resID);
		if(view==null){
			view=mConvertView.findViewById(resID);
			mViews.put(resID, view);
		}
		return (T)view;
	}
	
	public View getConvertView(){
		return mConvertView;
	}
	public int getPosition(){
		return mPosition;
	}
	
	/**
	 * 设置TextView 的值  
	 * @param ViewID 指定组件ID
	 * @param text 设置内容
	 * @return
	 */
	public ViewHolder setText(int ViewID,String text){
		TextView tv=getView(ViewID);
		tv.setText(text);
		return this;
	}
	public ViewHolder setText(int ViewID,int text){
		TextView tv=getView(ViewID);
		if(text>0){
			tv.setText(String.valueOf(text));
		}else{
			tv.setVisibility(View.INVISIBLE);
		}
		return this;
	}
	/**
	 * 缓存出问题 待完善方法
	 * 方法一新建list跟踪 方法二 bean中添加boolean属性记录
	 * @param ViewID
	 * @param flag
	 * @return
	 */
	public ViewHolder setChecked(int ViewID,Boolean flag){
		CheckBox cb=getView(ViewID);
		cb.setChecked(flag);
		return this;
	}
	
	public ViewHolder setAdapter(int ViewID,ListAdapter adapter){
		GridView gv=getView(ViewID);
		gv.setAdapter(adapter);
		return this;
	}
	/**
	 * 设置ImageView 的值  
	 * @param ViewID 指定组件ID
	 * @param drawable 设置内容ID
	 * @return
	 */
	
	public ViewHolder setRoundImageViewDrawable(int ViewID,Drawable drawable){
		RoundImageView tv=getView(ViewID);
		tv.setImageDrawable(drawable);
		return this;
	}
	
	public ViewHolder setImageURI(int ViewID,Uri uri){
		ImageView tv=getView(ViewID);
		tv.setImageURI(uri);
		return this;
	}
	public ViewHolder setImageDrawable(int ViewID,Drawable drawable){
		ImageView tv=getView(ViewID);
		tv.setImageDrawable(drawable);
		return this;
	}
	
	@SuppressWarnings("deprecation")
	public ViewHolder setImageDrawable(int ViewID,int resID){
		ImageView tv=getView(ViewID);
		tv.setImageDrawable(mContext.getResources().getDrawable(resID));
		return this;
	}
	public ViewHolder setImageResource(int ViewID,int resID){
		ImageView tv=getView(ViewID);
		tv.setImageResource(resID);
		return this;
	}
	public ViewHolder setImageBitmap(int ViewID,Bitmap bm){
		ImageView tv=getView(ViewID);
		tv.setImageBitmap(bm);
		return this;
	}
	public ViewHolder setAlpha(int ViewID,int alpha){
		ImageView tv=getView(ViewID);
		tv.setAlpha(alpha);;
		return this;
	}
	public ViewHolder setAlpha(int ViewID,Float alpha){
		ImageView tv=getView(ViewID);
		tv.setAlpha(alpha);;
		return this;
	}
	
}

