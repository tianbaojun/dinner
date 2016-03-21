package com.tabjin.dinner;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tabjin.dinner.activity.BaseActivity;
import com.tabjin.dinner.adapter.CartAdapter;
import com.tabjin.dinner.api.Contant;
import com.tabjin.dinner.bean.Address;
import com.tabjin.dinner.bean.FoodItem;
import com.tabjin.dinner.bean.Order;
import com.tabjin.dinner.bean.UserInfo;
import com.tabjin.dinner.db.SQLAddressService;
import com.tabjin.dinner.db.SQLOrderService;
import com.tabjin.dinner.utils.CoreUtil;
import com.tabjin.dinner.utils.FileLocalCache;
import com.tabjin.dinner.utils.MyUtil;
import com.tabjin.dinner.widget.MyGridView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Tabjin on 2016/3/15.
 */
public class SubmitActivity extends BaseActivity {

    private MyGridView listView;
    private CartAdapter adapter;
    private TextView count,name,call,addressTv,please;
    private Button submit;
    private EditText note;
    private Address address;
    private List<Address> addresses;
    private float sumary;
    private UserInfo userInfo;
    private final int REQUESTCODE = 1;

    @Override
    protected int setView() {
        return R.layout.activity_submit;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        set();
    }

    private void init(){
        please = (TextView) findViewById(R.id.please);
        name = (TextView) findViewById(R.id.user_name);
        addressTv = (TextView) findViewById(R.id.user_address);
        call = (TextView) findViewById(R.id.user_phone);
        setTitleText("提交订单");
        listView  = (MyGridView) findViewById(R.id.submit_list);
        adapter = new CartAdapter(Contant.cartList,this);
        count = (TextView) findViewById(R.id.count);
        submit = (Button) findViewById(R.id.submit_bt);
        note = (EditText) findViewById(R.id.note);

        findViewById(R.id.order_address).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubmitActivity.this,AddressActivity.class);
                intent.putExtra(Contant.address, Contant.address);
                startActivityForResult(intent, REQUESTCODE);
            }
        });
    }

    private void set(){
        listView.setAdapter(adapter);
        sumary = 0f;
        for(FoodItem item:Contant.cartList){
            sumary += item.getSelectNum()*item.getPrice();
        }
        count.setText("￥"+String.format("%.2f",sumary));
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (address != null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SubmitActivity.this);
                    builder.setTitle("确认提交订单？").setMessage("￥"+String.format("%.2f",sumary))
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Order order = new Order();
                            order.setTime(new Date());
                            order.setPhone(userInfo.getPhone());
                            order.setAddressId(address.getId());
                            order.setFoodItems(Contant.cartList);
                            List<Integer> ids = new ArrayList<>();
                            for(FoodItem item:Contant.cartList){
                                ids.add(item.get_id());
                            }
                            order.setFooditemIds(ids);
//                            SQLcartService sqLcartService = new SQLcartService(SubmitActivity.this);
//                            sqLcartService.insert(order);
                            SQLOrderService sqlOrderService = new SQLOrderService(SubmitActivity.this);
                            sqlOrderService.insert(order);
                            CoreUtil.removeActivityList(SubmitActivity.this);
                            CoreUtil.finishActivityList();
                            CoreUtil.startActivity(SubmitActivity.this, MainActivity.class);
                            for(FoodItem foodItem:Contant.cartList){
                                foodItem.setSelectNum(0);
                            }
                            Contant.cartList.clear();
                            finish();
                        }
                    }).setNegativeButton("取消", null);
                    builder.show();
                } else {
                    MyUtil.showToast(SubmitActivity.this,"请选择地址");
                }


            }
        });
        new MyTask().execute();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUESTCODE && data != null) {
            address = (Address) data.getSerializableExtra(Contant.address);
            name.setText(address.getName());
            please.setVisibility(View.GONE);
            addressTv.setText(address.getAddress());
            call.setText(address.getPhone());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    class MyTask extends AsyncTask<Void,Void,List<Address>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showDialog("数据加载中");
        }

        @Override
        protected void onPostExecute(List<Address> result) {
            super.onPostExecute(result);
            hideProgress();
            if(addresses.size()==0){
                please.setVisibility(View.VISIBLE);
            }else{
                address = addresses.get(0);
                name.setText(address.getName());
                call.setText(address.getCall());
                addressTv.setText(address.getAddress());
            }
        }

        @Override
        protected List<Address> doInBackground(Void... params) {
            SQLAddressService sqlAddressService = new SQLAddressService(SubmitActivity.this);
            userInfo = (UserInfo) FileLocalCache.getSerializableData(SubmitActivity.this, Contant.USER_INFOR);
            addresses = sqlAddressService.query(userInfo.getPhone());
            return addresses;
        }
    }
}
