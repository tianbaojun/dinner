package com.tabjin.dinner.utils;

import android.content.Context;

import com.tabjin.dinner.api.Contant;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tabjin on 2016/3/8.
 */
public class ImageRes {

    public static List<String > getImages(Context context){
        BufferedReader is = null;
        List<String> list = new ArrayList<>();
        try {
            is = new BufferedReader(new InputStreamReader(context.getAssets().open(Contant.imagesRes)));
            String str ;
            while ((str = is.readLine())!=null){
                list.add(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(is!=null)
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}
