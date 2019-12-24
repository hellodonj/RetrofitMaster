package com.djj.retrofitmaster.okhttp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.djj.retrofitmaster.R;
import com.djj.retrofitmaster.TimetableVo;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.util.HashMap;

/**
 * desc:OKhttp请求
 */
public class OkhttpActivity extends AppCompatActivity {

    private OkHttpHelper httpHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp);

        httpHelper = OkHttpHelper.getInstance();

        HashMap<String, String> params = new HashMap<>();
        params.put("teachid", "");
        params.put("page", "1");
        params.put("pageSize", "10");
        String url = "";
        httpHelper.post(url, params, new BaseCallback<TimetableVo>() {
            @Override
            public void onRequestBefore() {

            }

            @Override
            public void onFailure(Request request, Exception e) {

            }

            @Override
            public void onSuccess(Response response, TimetableVo timetableVo) {

            }


            @Override
            public void onError(Response response, int errorCode, Exception e) {

            }
        });

    }
}
