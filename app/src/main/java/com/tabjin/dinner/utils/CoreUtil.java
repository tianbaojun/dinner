package com.tabjin.dinner.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

public class CoreUtil {

	private static float sDensity;
	static List<Activity> list = new ArrayList<Activity>();
	/**
	 *  启动activity
	 * @param context
	 * @param class1
	 */
	public static void startActivity(Context context,Class class1){
		context.startActivity(new Intent(context, class1));
	}
	
	/**
	 *  判断字符串是否为空
	 * @param string
	 * @return
	 */
	public static boolean isEmpty(String string){
		if(string == null || string.trim().length() == 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 将启动的activity  加入到list集合中
	 */
	public static void addToActivityList(Activity context) {
		if(!list.contains(context)){
			list.add(context);
		}
	}
	/**
	 *   将关闭的activity  移出list集合
	 */
	public static void removeActivityList(Activity context) {
		if(list.contains(context)){
			list.remove(context);
		}
	}
	
	/**
	 *  关闭所有已经启动的activity
	 */
	public static void finishActivityList() {
		for (Activity context : list) {
			context.finish();
		}
		list.clear();
	}
	
	/**
	 *  关闭所有已经启动的activity  然后杀死进程
	 */
	public static void closeActivityList() {
		for (Activity context : list) {
			context.finish();
		}
		list.clear();
        // 杀进程 
        android.os.Process.killProcess(android.os.Process.myPid());
	}
	
	
	/**
     * 将sp值转换为px值，保证文字大小不变
     * @param context
     * @param spValue
     * @return
     */
    public static int spToPixel(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * dp转换为像素
     * @param context
     * @param nDip
     * @return
     */
    public static int dipToPixel(Context context, int nDip) {
        if (sDensity == 0) {
            final WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics dm = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(dm);
            sDensity = dm.density;
        }
        return (int) (sDensity * nDip);
    }

    /**
     * 获取屏幕 宽 高
     * @param activity
     * @return 数组  0 为宽度 1 为高度
     */
    public static int[] getDisplay(Activity activity){
        int[] display = new int[2];
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        display[0] = dm.widthPixels;
        display[1] = dm.heightPixels;
        return display;
    }
	
	
	
	

}