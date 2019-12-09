package com.djj.retrofitmaster;

import android.os.Bundle;
import android.util.Log;

import com.djj.retrofitmaster.base.BindEventBus;
import com.djj.retrofitmaster.baseHttp.BaseObserver;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


@BindEventBus
public class MainActivity extends RxAppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //自定义OkHttpClient
        OkHttpClient client = new OkHttpClient().newBuilder()
                .readTimeout(Constans.DEFAULT_TIME, TimeUnit.SECONDS) //设置读取超时时间
                .connectTimeout(Constans.DEFAULT_TIME, TimeUnit.SECONDS) //设置请求超时时间
                .writeTimeout(Constans.DEFAULT_TIME, TimeUnit.SECONDS) //设置写入超时时间
                .addInterceptor(new LogInterceptor()) //设置打印拦截器
                .retryOnConnectionFailure(true) //设置出现错误进行重新连接
                .build();

        //1、获取retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                //设置自定义的OKhttp
                .client(client)
                .baseUrl(Constans.BaseUrl)
                //添加Gson解析，返回数据转化成GSON类型
                .addConverterFactory(GsonConverterFactory.create())
                //添加Rxjava的支持
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        //Rxjava用法
        retrofit.create(ApiService.class)
                .getTimetable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                //处理内存泄漏 绑定生命周期
                .compose(bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new BaseObserver<DemoBeanVo>() {
                    @Override
                    public void onSuccess(DemoBeanVo demo) {
                        Log.e(TAG, "onSuccess: " + demo);
                    }

                    @Override
                    public void onFailure(Throwable e, String errorMsg) {
                        Log.e(TAG, "onFailure: " + errorMsg);
                    }
                });
//                .subscribe(new Observer<TimetableVo>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        Log.e(TAG, "onSubscribe: " );
//                    }
//
//                    @Override
//                    public void onNext(TimetableVo demo) {
//                        Log.e(TAG, "onNext: " +demo.toString());
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.e(TAG, "Throwable: " + e.getMessage());
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.e(TAG, "onComplete: " );
//                    }
//                });

        //2、获取接口对象
//        ApiService apiService = retrofit.create(ApiService.class);

        //3、调用请求方法
//        final Call<TimetableVo> timetable = apiService.getTimetable();

        //同步请求
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Response<TimetableVo> response = timetable.execute();
//                    int code = response.body().getRes_code();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
        //去异步请求操作
//        timetable.enqueue(new Callback<TimetableVo>() {
//            @Override
//            public void onResponse(Call<TimetableVo> call, Response<TimetableVo> response) {
//                TimetableVo body = response.body();
//                Log.e(TAG, "请求成功的信息：" + response.body().toString());
//            }
//
//            @Override
//            public void onFailure(Call<TimetableVo> call, Throwable t) {
//                Log.e(TAG, "请求失败的信息：" + t.getMessage());
//            }
//        });
    }
}
