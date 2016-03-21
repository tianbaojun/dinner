package com.tabjin.dinner;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.tabjin.dinner.activity.BaseActivity;
import com.tabjin.dinner.adapter.FoodItemAdapter;
import com.tabjin.dinner.adapter.TopGVAdapter;
import com.tabjin.dinner.api.Contant;
import com.tabjin.dinner.bean.FoodItem;
import com.tabjin.dinner.bean.FoodType;
import com.tabjin.dinner.bean.UserInfo;
import com.tabjin.dinner.utils.FileLocalCache;
import com.tabjin.dinner.utils.MyCallback;
import com.tabjin.dinner.utils.MyUtil;
import com.tabjin.dinner.widget.MyGridView;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.Collections;
import java.util.List;

import static com.tabjin.dinner.api.Contant.foodItemList;

/**
 * Created by Administrator on 2016/1/24.
 */
public class AllfoodActivity extends BaseActivity{
    private static final int WHAT_DID_LOAD_DATA = 0;
    private static final int WHAT_DID_REFRESH = 1;
    private static final int WHAT_DID_MORE = 3;
    //    推荐美食列表
    private ListView foodList;
    private MyGridView topGrid;
    private TopGVAdapter topGVAdapter;
    private FoodItemAdapter foodAdapter,cartAdapter;
    private RadioGroup foodListGroup;
    private List<FoodItem> foodItems;
    private TextView cartTv;
    private TextView submit;
    private ListView cartListView;
    private TextView clear;
    private ImageView first,two;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        set();
    }

    @Override
    protected int setView() {
        return R.layout.activity_allfood;
    }

    private void init() {


        clear = (TextView) findViewById(R.id.clear_bt);
        cartListView = (ListView) findViewById(R.id.cart_list);
        submit = (TextView) findViewById(R.id.submit_bt);
        cartTv = (TextView) findViewById(R.id.count_tv);
        foodList = (ListView) findViewById(R.id.allfood_list);
//        设置标题
        setTitleText(getString(R.string.allfood));

        cartAdapter = new FoodItemAdapter(Contant.cartList,this);

//        得到头视图
        View view = LayoutInflater.from(this).inflate(R.layout.allfood_list_top, null);
        first = (ImageView) view.findViewById(R.id.first);
        two = (ImageView) view.findViewById(R.id.two);
        foodList.addHeaderView(view, null, false);
//        得到表格视图   TODO  给表格视图设置监听
        topGrid = (MyGridView) view.findViewById(R.id.allfood_list_top_grid);
        topGVAdapter = new TopGVAdapter(FoodType.getGv(), this);

        foodItems = foodItemList;
        foodAdapter = new FoodItemAdapter(foodItems, this);
        foodListGroup = (RadioGroup) findViewById(R.id.allfood_list_top_rg);

    }

    private void set() {

        x.image().loadDrawable(Contant.ALLFOOD[0], ImageOptions.DEFAULT, new MyCallback<Drawable>(first));
        x.image().loadDrawable(Contant.ALLFOOD[1], ImageOptions.DEFAULT, new MyCallback<Drawable>(two));
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInfo userInfo = (UserInfo) FileLocalCache.getSerializableData(AllfoodActivity.this, Contant.USER_INFOR);
                if (userInfo == null) {
                    MyUtil.showToast(AllfoodActivity.this, "您还没有登录，请先登录");
                } else {
                    if (Contant.cartList.size() == 0) {
                        MyUtil.showToast(AllfoodActivity.this, "您还没有挑选商品");
                    } else {
                        startActivity(new Intent(AllfoodActivity.this, SubmitActivity.class));
                    }
                }
            }
        });
//        为顶部的网格设置adapter
        topGrid.setAdapter(topGVAdapter);
//        食品列表设置adapter
//        foodList.initBottomView();
        foodList.setAdapter(foodAdapter);
//        下拉刷新
       /* foodList.setMyPullUpListViewCallBack(new MyPullUpListView.MyPullUpListViewCallBack() {
            @Override
            public void scrollBottomState() {
                new AsyncTask<Void, Void, Integer>() {
                    @Override
                    protected Integer doInBackground(Void... params) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        int count = foodAdapter.getCount() + 10;
                        if (count > Contant.foodNum) {
                            count = count - 10;
                        }
//                TODO  OOM解决
                        return count;
                    }

                    @Override
                    protected void onPostExecute(Integer aVoid) {
                        foodAdapter.setList(foodItems.subList(0, aVoid));
                    }
                }.execute();
            }
        });*/

//        给购物车设置adapter
        cartListView.setAdapter(cartAdapter);
//        设置默认选中的排序方式
        setCheck(R.id.most_num);
//        食品排序设置监听
        foodListGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                setCheck(checkedId);
            }
        });

        cartTv.setText(String.valueOf(Contant.cartList.size()));
        int num = 0;
        float money = 0.0f;
        for (FoodItem item : Contant.cartList) {
            num += item.getSelectNum();
            money += item.getPrice() * item.getSelectNum();
        }
        String count = num + "个  ￥" + String.format("%.2f", money);
        setCount(count);

        cartTv.setOnClickListener(new View.OnClickListener() {

            boolean isUp = false;

            @Override
            public void onClick(View v) {
                if (isUp) {
                    cartListView.setVisibility(View.GONE);
                    clear.setVisibility(View.GONE);
                } else {
                    cartListView.setVisibility(View.VISIBLE);
                    cartAdapter.setList(Contant.cartList);
                    clear.setVisibility(View.VISIBLE);
                }
                isUp = !isUp;
            }
        });

//        清空购物车列表
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (FoodItem item : Contant.cartList) {
                    item.setSelectNum(0);
                }
                Contant.cartList.clear();
                cartAdapter.notifyDataSetChanged();
                foodAdapter.notifyDataSetChanged();
                cartTv.setText("0");
            }
        });
    }

    /**
     * 设置购物车模块的金额
     * @param count
     */
    public void setCount(String count){
        cartTv.setText(count);
    }

    /**
     * 排序选择器
     * @param checkedId 选中的id
     */
    private void setCheck(int checkedId){
        List<FoodItem> list;
        switch (checkedId){
            case R.id.good_comment:
                Collections.sort(foodItems, FoodItem.ratingCompartor);
                list = foodItems.subList(0, 30);
                break;
            case R.id.most_num:
                Collections.sort(foodItems, FoodItem.soldCompartor);
                list = foodItems.subList(0,30);
                break;
            case R.id.best_price:
                Collections.sort(foodItems, FoodItem.priceCompartor);
                list = foodItems.subList(0,30);
                break;
            case R.id.good_price:
                Collections.sort(foodItems, Collections.reverseOrder(FoodItem.priceCompartor));
                list = foodItems.subList(0,30);
                break;
            default:
                Collections.sort(foodItems, FoodItem.soldCompartor);
                list = foodItems.subList(0,30);
                break;
        }
        foodAdapter.setList(list);
    }

}
