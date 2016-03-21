package com.tabjin.dinner.utils;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import org.xutils.common.Callback.CommonCallback;

public class MyCallback<T> implements CommonCallback<T>{
	
	private ImageView imageView ;
	
	public MyCallback(ImageView imageView) {
		this.imageView = imageView;
	}
	@Override
	public void onCancelled(CancelledException arg0) {
	}
	@Override
	public void onError(Throwable arg0, boolean arg1) {
	}
	@Override
	public void onFinished() {
	}
	@Override
	public void onSuccess(T arg0) {
		imageView.setImageDrawable((Drawable)arg0);
	}
	
}