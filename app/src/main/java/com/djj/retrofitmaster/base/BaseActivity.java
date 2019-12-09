package com.djj.retrofitmaster.base;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

public class BaseActivity extends Activity {

    private Activity activity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initData();
    }

    private void initData() {
        activity = this;
        //判断当前子类是否绑定了BindEventBus注解
        if (this.getClass().isAnnotationPresent(BindEventBus.class)) {
            //注册
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (this.getClass().isAnnotationPresent(BindEventBus.class)) {
            //反注册

        }
    }
}
