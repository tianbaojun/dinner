package com.tabjin.dinner.utils;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class GetJsonString {
	/**
	 * 閫氳繃JSON鏂囦欢鑾峰緱鏂囦欢鍐呯殑鏁版嵁锛岃繑鍥炰竴涓猄tring銆�
	 * 鐒跺悗 new JSONObject(String);鑾峰緱json瀵硅薄锛屽啀瑙ｆ瀽銆�
	 * @param jsonFileName 鏂囦欢鍚�
	 * @param context 涓婁笅鏂�
	 * @return
	 */
	public static String getJsonString(Context context,String jsonFileName){
		StringBuilder sb = new StringBuilder();
		AssetManager  manager = context.getResources().getAssets();
		BufferedReader bReader = null;
		try {
			 InputStream is=  manager.open(jsonFileName);
			 bReader = new BufferedReader(new InputStreamReader(is));
			 String sub;
			while (( sub=bReader.readLine()) != null) {
				sb.append(sub);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(bReader!=null){
				try {
					bReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
	}
	

}
