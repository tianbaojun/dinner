package com.tabjin.dinner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.tabjin.dinner.activity.BaseActivity;
import com.tabjin.dinner.api.Contant;
import com.tabjin.dinner.bean.Address;
import com.tabjin.dinner.bean.UserInfo;
import com.tabjin.dinner.db.SQLAddressService;
import com.tabjin.dinner.utils.CoreUtil;
import com.tabjin.dinner.utils.FileLocalCache;
import com.tabjin.dinner.utils.MyUtil;

/**
 * Created by Tabjin on 2016/3/16.
 */
public class AddressAddActivity extends BaseActivity {

    private EditText name,call,street,num;
    private Address address;
    private Address bundAddress;
    @Override
    protected int setView() {
        return R.layout.activity_address_add;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void getAddress(){
        String callStr = call.getText().toString();
        String addressStr = street.getText().toString() + num.getText().toString();
        String nameStr = name.getText().toString();
        if (CoreUtil.isEmpty(callStr)||CoreUtil.isEmpty(addressStr)||CoreUtil.isEmpty(nameStr)) {
            return;
        }
        address = new Address();
        address.setCall(callStr);
        address.setAddress(addressStr);
        address.setName(nameStr);
        UserInfo userInfo = (UserInfo) FileLocalCache.getSerializableData(AddressAddActivity.this, Contant.USER_INFOR);
        address.setPhone(userInfo.getPhone());
        if (bundAddress != null) {
            address.setId(bundAddress.getId());
        }
    }

    private void init() {
        name = (EditText) findViewById(R.id.name);
        num = (EditText) findViewById(R.id.num);
        street = (EditText) findViewById(R.id.street);
        call = (EditText) findViewById(R.id.phone);
        Intent intent = getIntent();
        bundAddress = (Address) intent.getSerializableExtra(Contant.address);
        if(bundAddress != null){
            name.setText(bundAddress.getName());
            call.setText(bundAddress.getCall());
            setTitleText("修改地址");
        }else{
            setTitleText("添加地址");
        }
        showRightText("确定");
        findViewById(R.id.right_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAddress();
                SQLAddressService sqlAddressService = new SQLAddressService(AddressAddActivity.this);
                if (address != null) {
                    if(bundAddress == null) {
                        sqlAddressService.insert(address);
                        AddressAddActivity.this.finish();
                    }else{
                        sqlAddressService.update(address);
                        AddressAddActivity.this.finish();
                    }
                }else{
                    MyUtil.showToast(AddressAddActivity.this,"请输入正确内容");
                }
            }
        });
    }
}
