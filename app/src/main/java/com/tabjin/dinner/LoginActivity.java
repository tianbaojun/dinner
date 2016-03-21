package com.tabjin.dinner;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tabjin.dinner.activity.BaseActivity;
import com.tabjin.dinner.api.Contant;
import com.tabjin.dinner.bean.UserInfo;
import com.tabjin.dinner.db.SQLUserInfoService;
import com.tabjin.dinner.utils.CoreUtil;
import com.tabjin.dinner.utils.FileLocalCache;
import com.tabjin.dinner.utils.MyUtil;

public class LoginActivity extends BaseActivity implements OnClickListener{
	
	private Button register,login;
	private String phone,password;
	private EditText phoneEt,passwordEt;
	private TextView problem;
	private AsyncTask<String, Void, UserInfo> task;
	
	@Override
	protected int setView() {
		return R.layout.activity_login;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
		
	}
	
	/**
	 * 得到用户的密码和用户名
	 */
	private void getUserInfo(){
		phone = phoneEt.getText().toString();
		password = passwordEt.getText().toString();
	}
	
	private void init(){
		register = (Button) findViewById(R.id.register_bt);
		register.setOnClickListener(this);
		login = (Button) findViewById(R.id.login_bt);
		login.setOnClickListener(this);
		phoneEt = (EditText) findViewById(R.id.phone_input_et);
		passwordEt = (EditText) findViewById(R.id.password_et);
		problem = (TextView) findViewById(R.id.problem_tv);
	}
	
	/**
	 * 执行登录操作
	 */
	public void doLogin(){

		getUserInfo();
		UserInfo userInfo = new UserInfo();
		userInfo.setPhone(phone);
		userInfo.setPassword(password);
		new AsyncTask<UserInfo, Void, Boolean>() {

			@Override
			protected void onPostExecute(Boolean aBoolean) {
				super.onPostExecute(aBoolean);
				hideProgress();
				phoneEt.setText("");
				passwordEt.setText("");
				if(aBoolean){
					MyUtil.showToast(LoginActivity.this,"登录成功");
					CoreUtil.startActivity(LoginActivity.this, MainActivity.class);
					LoginActivity.this.finish();
				}else{
					MyUtil.showToast(LoginActivity.this,"用户名或密码错误");
				}
				phoneEt.setText("");
				passwordEt.setText("");
			}

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				showDialog("登录中...");
			}

			@Override
			protected Boolean doInBackground(UserInfo... params) {
				SQLUserInfoService sqlUserInfoService = new SQLUserInfoService(LoginActivity.this);
				boolean flag = sqlUserInfoService.query(params[0]);
				if(flag){
					FileLocalCache.setSerializableData(LoginActivity.this,params[0], Contant.USER_INFOR);
				}
				return flag;
			}
		}.execute(userInfo);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.register_bt:
			MyUtil.startActivity(this, RegisterActivity.class);
			break;
		case R.id.login_bt:
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(passwordEt.getWindowToken(), 0);
			doLogin();
			break;
		case R.id.problem_tv:
			Intent intent = new Intent();
			intent.setAction(Intent.ACTION_VIEW);
			startActivity(intent);
			finish();
			break;

		default:
			break;
		}
	}

}
