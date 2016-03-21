package com.tabjin.dinner.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.tabjin.dinner.R;
import com.tabjin.dinner.adapter.OrderAdapter;
import com.tabjin.dinner.api.Contant;
import com.tabjin.dinner.bean.Order;
import com.tabjin.dinner.bean.UserInfo;
import com.tabjin.dinner.db.SQLOrderService;
import com.tabjin.dinner.utils.FileLocalCache;

import java.util.List;


/**
 * Created by Tabjin on 2016/3/6.
 */
public class OrderFragment extends Fragment {

    private Context context;
    private View view ;
    private View toast;
    private ListView listView;
    private UserInfo userInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_order, null);
            init(view);
        }
        loading();
        return view;
    }

    private void init(View view) {
        context = getActivity();
        listView = (ListView) view.findViewById(R.id.list);
        toast = view.findViewById(R.id.toast);

    }

    /**
     * 耗时操作在这里执行
     */
    private void loading(){
        new AsyncTask<Void,Void,List<Order> >(){


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(List<Order>  result) {
                super.onPostExecute(result);
                if(result == null){
                    toast.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.GONE);
                }else{
                    toast.setVisibility(View.GONE);
                    listView.setAdapter(new OrderAdapter(getActivity(), result));
                    listView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            protected List<Order> doInBackground(Void... params) {
                userInfo = (UserInfo) FileLocalCache.getSerializableData(getActivity(), Contant.USER_INFOR);
                List<Order> orders = null;
                if(userInfo != null){
                    SQLOrderService sqlOrderService = new SQLOrderService(getActivity());
                    orders = sqlOrderService.query(userInfo.getPhone());
                    Log.e("--main--" ,""+orders.size());
                }
                return orders;
            }
        }.execute();
    }
}
