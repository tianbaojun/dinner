package com.tabjin.dinner.utils;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyUtil {

	/**
	 * ��������activity
	 * @param context �����Ķ���
	 * @param class1 Ŀ��activity
	 */
	public static void startActivity(Context context,Class<?> class1){
		context.startActivity(new Intent(context,class1));
	}
	
	/**
	 * ��ʾToast��ʱ��Ϊshort
	 * @param context ������
	 * @param message ��ʾ������
	 */
	public static void showToast(Context context,String message){
		Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
	}
	
	/**
     * ��spֵת��Ϊpxֵ����֤���ִ�С����
     * @param context
     * @param spValue
     * @return
     */
    public static int spToPixel(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
