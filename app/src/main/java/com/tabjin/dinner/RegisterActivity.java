package com.tabjin.dinner;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.tabjin.dinner.activity.BaseActivity;
import com.tabjin.dinner.api.Contant;
import com.tabjin.dinner.bean.UserInfo;
import com.tabjin.dinner.db.SQLUserInfoService;
import com.tabjin.dinner.utils.CoreUtil;
import com.tabjin.dinner.utils.FileLocalCache;
import com.tabjin.dinner.utils.MyUtil;

public class RegisterActivity extends BaseActivity implements OnClickListener{
	
	private Button register,sendCode;
	private String phone,password,code;
	private EditText phoneEt,passwordEt,codeEt;
	private ProgressBar progressBar;
	private AsyncTask<String, Integer, Boolean> task;

	@Override
	protected int setView() {
		return R.layout.activity_register;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("注册");
		 init();
		 setListener();
	}
/**
 * 初始化
 */
	private void init(){
		register = (Button) findViewById(R.id.register_bt);
		sendCode = (Button) findViewById(R.id.code_bt);
		phoneEt = (EditText) findViewById(R.id.phone_input_et);
		passwordEt = (EditText) findViewById(R.id.password_et);
		codeEt = (EditText) findViewById(R.id.code_et);
	}
	
	/**
	 * 设置监听器
	 */
	private void setListener(){
		register.setOnClickListener(this);
		sendCode.setOnClickListener(this);
		
	}
	
	/**
	 * 得到输入的用户信息
	 */
	private void getUserInfo(){
		phone = phoneEt.getText().toString();
		password = passwordEt.getText().toString();
		code = codeEt.getText().toString();
	}


	
	/**
	 * 执行注册操作
	 */
	private void doRegister(){
//		progressBar.setVisibility(View.VISIBLE);
//		将用户的注册信息保存到数据库

		if(CoreUtil.isEmpty(phone)){
			MyUtil.showToast(this,"请输入手机号");
			return;
		}
		if(CoreUtil.isEmpty(password)){
			MyUtil.showToast(this,"密码不能为空");
			return;
		}

		task = new AsyncTask<String, Integer, Boolean>() {

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				showDialog("正在注册...");
			}

			@Override
			protected void onPostExecute(Boolean s) {
				super.onPostExecute(s);
				hideProgress();
				if(s){
					MyUtil.showToast(RegisterActivity.this,"注册成功");
					CoreUtil.startActivity(RegisterActivity.this, MainActivity.class);
					RegisterActivity.this.finish();
				}else{
					MyUtil.showToast(RegisterActivity.this,"号码已被占用");
				}
			}

			@Override
			protected Boolean doInBackground(String... params) {
				UserInfo userInfo = new UserInfo();
				userInfo.setPhone(params[0]);
				userInfo.setPassword(params[1]);
				SQLUserInfoService userInfoService = new SQLUserInfoService(RegisterActivity.this);
				boolean flag = userInfoService.insert(userInfo);
				if(flag){
					FileLocalCache.setSerializableData(RegisterActivity.this, userInfo, Contant.USER_INFOR);
				}
				return flag;
			}
		};
		task.execute(phone,password,code);
		
	}

	@Override
	public void onClick(View v) {
		getUserInfo();
		switch (v.getId()) {
		case R.id.register_bt://注册
			doRegister();
			break;
		case R.id.code_bt://验证码
			break;
		default:
			break;
		}
	}
	
}
