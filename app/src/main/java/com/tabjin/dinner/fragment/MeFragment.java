package com.tabjin.dinner.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tabjin.dinner.AddressActivity;
import com.tabjin.dinner.LoginActivity;
import com.tabjin.dinner.R;
import com.tabjin.dinner.api.Contant;
import com.tabjin.dinner.bean.UserInfo;
import com.tabjin.dinner.utils.CoreUtil;
import com.tabjin.dinner.utils.FileLocalCache;
import com.tabjin.dinner.utils.MyUtil;

/**
 * Created by Tabjin on 2016/3/6.
 */
public class MeFragment extends Fragment implements View.OnClickListener {

    private Context context;
    private boolean isVisibleToUser;
    private boolean isFirst = true ;
    private View view;

    private TextView login;
    private ImageView imageLogin;
    private UserInfo userInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_me, null);
        }
        init(view);
        set();
        return view;
    }

    private void init(View view) {
        context = getActivity();
        login = (TextView) view.findViewById(R.id.login_tv_myinfo);
        imageLogin = (ImageView) view.findViewById(R.id.my_image);
        view.findViewById(R.id.address).setOnClickListener(this);
    }

    private void set(){
        login.setOnClickListener(this);
        imageLogin.setOnClickListener(this);

    }

    /**
     * 耗时操作在这里执行
     */
    private void loading(){
        userInfo = (UserInfo) FileLocalCache.getSerializableData(getActivity(),Contant.USER_INFOR);
        if(userInfo != null){
            login.setText(userInfo.getPhone());
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        if(isVisibleToUser && isFirst && view != null){
            loading();
            isFirst = false;
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if(isVisibleToUser && isFirst){
            loading();
            isFirst = false;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_image:
            case R.id.login_tv_myinfo:
                CoreUtil.startActivity(context, LoginActivity.class);
                break;
            case R.id.address:
                UserInfo userInfo = (UserInfo) FileLocalCache.getSerializableData(context, Contant.USER_INFOR);
                if(userInfo == null){
                    MyUtil.showToast(context,"您还没有登录，请登录");
                }else{
                    CoreUtil.startActivity(context,AddressActivity.class);
                }
                break;

        }
    }


}
