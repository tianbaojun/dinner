package com.tabjin.dinner.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.tabjin.dinner.AllfoodActivity;
import com.tabjin.dinner.R;
import com.tabjin.dinner.api.Contant;
import com.tabjin.dinner.utils.MyCallback;

import org.xutils.image.ImageOptions;
import org.xutils.x;

/**
 * Created by Tabjin on 2016/3/6.
 */
public class HomepageFragment extends Fragment implements View.OnClickListener{

    private Context context;
    private ViewFlipper viewFlipper;
    private Button allFood;

    private boolean isFirst = true;
    private View view ;
    private boolean isVisibleToUser;
    ImageView img1,img2,img3,first,two,three;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_homepage, null);
            init(view);
            set();
        }
        return view;
    }

    private void init(View view) {
        img1 = (ImageView) view.findViewById(R.id.img_1);
        img2 = (ImageView) view.findViewById(R.id.img_2);
        img3 = (ImageView) view.findViewById(R.id.img_3);
        first = (ImageView) view.findViewById(R.id.first);
        two = (ImageView) view.findViewById(R.id.two);
        three = (ImageView) view.findViewById(R.id.three);
        context = getActivity();
        viewFlipper = (ViewFlipper) view.findViewById(R.id.vf_homepages);
        auto();
        allFood = (Button) view.findViewById(R.id.bt_allfood);
    }


    private void set() {
        allFood.setOnClickListener(this);
    }

    /**
     * 图片自动滑动
     */
    public void auto(){
        viewFlipper.setInAnimation(context, R.anim.slide_in_left);
        viewFlipper.setOutAnimation(context, R.anim.slide_out_right);
        viewFlipper.startFlipping();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_allfood:
                allFood();
                break;

        }
    }

    /**
     * 按钮监听器所有美食的执行方法
     */
    public void allFood(){
        Intent intent = new Intent();
        intent.setClass(context, AllfoodActivity.class);
        startActivity(intent);
    }

    /**
     * 耗时操作在这里执行
     */
    private void loading(){
        new AsyncTask<Void,Void,Void>(){

            @Override
            protected Void doInBackground(Void... params) {
                x.image().loadDrawable(Contant.FLIPPER[0], ImageOptions.DEFAULT,new MyCallback<Drawable>(img1));
                x.image().loadDrawable(Contant.FLIPPER[1], ImageOptions.DEFAULT,new MyCallback<Drawable>(img2));
                x.image().loadDrawable(Contant.FLIPPER[2], ImageOptions.DEFAULT,new MyCallback<Drawable>(img3));
                x.image().loadDrawable(Contant.HOMEPAGE[0],ImageOptions.DEFAULT,new MyCallback<Drawable>(first));
                x.image().loadDrawable(Contant.HOMEPAGE[1],ImageOptions.DEFAULT,new MyCallback<Drawable>(two));
                x.image().loadDrawable(Contant.HOMEPAGE[2],ImageOptions.DEFAULT,new MyCallback<Drawable>(three));
                return null;
            }
        }.execute();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        if(isVisibleToUser && isFirst &&view!=null){
            loading();
            isFirst = false;
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if(isVisibleToUser && isFirst ){
            loading();
            isFirst = false;
        }

    }
}
