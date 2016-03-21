package com.tabjin.dinner;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.tabjin.dinner.activity.BaseActivity;
import com.tabjin.dinner.api.Contant;
import com.tabjin.dinner.fragment.HomepageFragment;
import com.tabjin.dinner.fragment.MeFragment;
import com.tabjin.dinner.fragment.OrderFragment;

public class MainActivity extends BaseActivity {

    private Fragment[] fragments;
    private int[] ids = {R.id.homepage,R.id.order,R.id.me};
    private ViewPager viewPager;
    private RadioGroup radioGroup;
    private  RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
        set();
    }

    @Override
    protected int setView() {
        return R.layout.activity_main;
    }

    private void init() {
        setTitleText(getString(R.string.app_name));
        fragments = new Fragment[3];
        fragments[0] = new HomepageFragment();
        fragments[1] = new OrderFragment();
        fragments[2] = new MeFragment();

        ids = new int[4];
        ids[0] = R.id.homepage;
        ids[1] = R.id.order;
        ids[2] = R.id.me;

        viewPager = (ViewPager) findViewById(R.id.vp_id);
        radioGroup = (RadioGroup) findViewById(R.id.nav_group_id);
    }

    /**
     * 为组件设置监听及适配器
     */
    private void set(){
        viewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            int which;
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                which = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if(state == 0)
                selector(ids[which]);
            }
        });

//        导航栏设置监听
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                selector(checkedId);
            }
        });
    }

    /**
     * viewpager的适配器
     */
    private class MyViewPagerAdapter extends FragmentPagerAdapter {



        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int arg0) {
            return fragments[arg0];
        }

        @Override
        public int getCount() {
            return fragments.length;
        }

    }


    /**
     * 底部导航栏的选择器
     * @param id
     */
    private void selector(int id){
        int position;

        switch (id) {
            case R.id.homepage:   //首页
                MainActivity.this.setTitleText("首页");
                radioButton = (RadioButton) findViewById(R.id.homepage);
                position = 0;
                break;
            case R.id.order:   //订单
                MainActivity.this.setTitleText("订单");
                radioButton = (RadioButton) findViewById(R.id.order);
                position = 1;
                break;
            case R.id.me:  //我
                MainActivity.this.setTitleText("我");
                radioButton = (RadioButton) findViewById(R.id.me);
                hideRight();
                position = 2;
                break;
            default:
                radioButton = (RadioButton) findViewById(R.id.homepage);
                position = 0;
                break;
        }
        radioButton.setChecked(true);
//		viewPager.setCurrentItem(position);  默认有切换的动画效果
        viewPager.setCurrentItem(position,false);//true 有动画效果   false 没有动画效果

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Contant.cartList.clear();
    }
}
