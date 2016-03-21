package com.tabjin.dinner;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.tabjin.dinner.activity.BaseActivity;
import com.tabjin.dinner.adapter.AdderssAdapter;
import com.tabjin.dinner.api.Contant;
import com.tabjin.dinner.bean.Address;
import com.tabjin.dinner.bean.UserInfo;
import com.tabjin.dinner.db.SQLAddressService;
import com.tabjin.dinner.utils.CoreUtil;
import com.tabjin.dinner.utils.FileLocalCache;

import java.util.List;

/**
 * Created by Tabjin on 2016/3/16.
 */
public class AddressActivity extends BaseActivity {

    private List<Address> addresses;
    private ListView listView;
    private AdderssAdapter adapter;
    private final int RESULTCODE = 2;

    @Override
    protected int setView() {
        return R.layout.activity_address;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        listView = (ListView) findViewById(R.id.list);
        listView.setEnabled(true);
        findViewById(R.id.add_address).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CoreUtil.startActivity(AddressActivity.this, AddressAddActivity.class);
            }
        });
        Intent intent = getIntent();
        String str =  intent.getStringExtra(Contant.address);
        if (str != null) {
            setTitleText("选择地址");
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent();
                    intent.putExtra(Contant.address, addresses.get(position));
                    setResult(RESULTCODE, intent);
                    finish();
                }
            });
        } else {
            setTitleText("收货地址");
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent  = new Intent(AddressActivity.this,AddressAddActivity.class);
                    Address address = addresses.get(position);
                    intent.putExtra(Contant.address,address);
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        new MyTask().execute();
    }

    class MyTask extends AsyncTask<Void,Void,List<Address>>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showDialog("数据加载中");
        }

        @Override
        protected void onPostExecute(List<Address> result) {
            super.onPostExecute(result);
            hideProgress();
            adapter = new AdderssAdapter(result,AddressActivity.this);
            listView.setAdapter(adapter);
        }

        @Override
        protected List<Address> doInBackground(Void... params) {
            SQLAddressService sqlAddressService = new SQLAddressService(AddressActivity.this);
            UserInfo userInfo = (UserInfo) FileLocalCache.getSerializableData(AddressActivity.this, Contant.USER_INFOR);
            addresses = sqlAddressService.query(userInfo.getPhone());
            return addresses;
        }
    }
}
