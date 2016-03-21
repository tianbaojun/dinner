package com.tabjin.dinner.bean;

import java.io.Serializable;

public class UserInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 422389239753661943L;
	private String password;
	private int addressId ;
	private String userId;
	private String userName;
	private String phone;
	private String head;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getAddressId() {
		return addressId;
	}

	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	/*	*//**
	 * 解析json得到用户信息
	 * @param message json字符串
	 * @return  用户信息
	 *//*
	public static UserInfo getUserInfo(String message){
		UserInfo userInfo = new UserInfo();
		try {
			JSONObject jsonObject = new JSONObject(message);
			if(0!=jsonObject.getInt("status")){
				return null;
			}
			JSONObject jsonUser = jsonObject.getJSONObject("userInfor");
			userInfo.setHead(jsonUser.getString("head"));
			userInfo.setUserId(jsonUser.getString("userId"));
			userInfo.setUserName(jsonUser.getString("userName"));
			userInfo.setUserPhoneNum(jsonUser.getLong("userPhoneNum"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return userInfo;
	}*/
	
}
