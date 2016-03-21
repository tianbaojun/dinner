package com.tabjin.dinner.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.tabjin.dinner.R;
import com.tabjin.dinner.utils.CoreUtil;

public abstract class BaseActivity extends FragmentActivity {
	
	private TextView title,rightTv;
	private ImageView leftIm,rightIm;
	private OnClickListener onClickListener;
	private ProgressDialog progress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(setView());
		CoreUtil.addToActivityList(this);
		init();
	}
	/**
	 * 初始化
	 */
	private void init(){
		title = (TextView) findViewById(R.id.title_tv);
		rightTv = (TextView) findViewById(R.id.right_tv);
		leftIm = (ImageView) findViewById(R.id.left_im);
		rightIm = (ImageView) findViewById(R.id.right_im);
		
		leftIm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(onClickListener==null){
					finish();
				}else{
					onClickListener.onClick(v);
				}
			}
		});
	}
	
	/**
	 * 设置监听
	 * @param onClickListener
	 */
	public void setListener(OnClickListener onClickListener) {
		this.onClickListener = onClickListener;
	}
	
	/**
	 * 设置布局
	 * @return 布局id
	 */
	abstract protected int setView();
	
	/**
	 * 展示右边的文字
	 * @param rightTextId 右边文字id
	 */
	public void showRightText(int rightTextId){
		rightTv.setText(rightTextId);
		rightTv.setVisibility(View.VISIBLE);
		rightIm.setVisibility(View.GONE);
	}
	
	/**
	 * 展示右边文字
	 * @param rightText 文字字符串
	 */
	public void showRightText(String rightText ){
		rightTv.setText(rightText);
		rightTv.setVisibility(View.VISIBLE);
		rightIm.setVisibility(View.GONE);
	}
	
	
	/**
	 * 展示右边图片
	 */
	public void showRightImage(int rightImgId){
		rightIm.setImageResource(rightImgId);
		rightIm.setVisibility(View.VISIBLE);
		rightTv.setVisibility(View.GONE);
	}
	
	public void hideRight(){
		rightIm.setVisibility(View.GONE);
		rightTv.setVisibility(View.GONE);
	}
	
	/**
	 * 展示左边图片
	 */
	public void showLeftImage(int leftImgId){
		leftIm.setImageResource(leftImgId);
		leftIm.setVisibility(View.VISIBLE);
	}
	
	/**
	 *隐藏左边图片
	 */
	public void hideLeftImage(){
		leftIm.setVisibility(View.GONE);
	}
	
	/**
	 * 设置标题
	 * @param title String标题
	 */
	public void setTitleText(String title){
		this.title.setText(title);
	}
	
	/**
	 * 设置标题
	 * @param titleId int 标题
	 */
	public void setTitleText(int titleId){
		this.title.setText(titleId);
	}
	
	/**
	 * 显示进度条对话框
	 */
	public void showDialog(String message){
		progress = new ProgressDialog(this); 
		progress.setTitle(message);
		progress.show();
	}
	
	@Override
	protected void onDestroy() {
		CoreUtil.removeActivityList(this);
		super.onDestroy();
	}
	
	public void hideProgress(){
		if(null!=progress){
			progress.dismiss();
		}
	}
	
}
