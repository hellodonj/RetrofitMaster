package com.djj.retrofitmaster;

import android.os.Bundle;
import android.util.Log;

import com.djj.retrofitmaster.base.BindEventBus;
import com.djj.retrofitmaster.baseHttp.BaseResponse;
import com.djj.retrofitmaster.baseHttp.LogInterceptor;
import com.djj.retrofitmaster.baseHttp.MyObserver;
import com.djj.retrofitmaster.baseHttp.RetrofitUtils;
import com.djj.retrofitmaster.baseHttp.RxHelper;
import com.djj.retrofitmaster.utils.NumberProgressBar1;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * desc:
 * 参考：https://www.jianshu.com/p/81149c0babd1
 * author:djj
 * date:2019/12/24 10:56
 */
@BindEventBus
public class MainActivity extends RxAppCompatActivity {

    private static final String TAG = "MainActivity";
    private NumberProgressBar1 progressBar;
    private int mProgress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progress_bar);

        //模拟进度
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mProgress < 100) {
                    mProgress += 1;
                    progressBar.setProgress(mProgress);
                    try {
                        Thread.sleep(200);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        //简单封装效果
        RetrofitUtils.getApiServer()
                .getTimetable()
                .compose(RxHelper.observableIO2Main(this))
                //加载框
                .subscribe(new MyObserver<DemoBeanVo>(this) {
                    @Override
                    public void onSuccess(DemoBeanVo demo) {

                    }

                    @Override
                    public void onFailure(Throwable e, String errorMsg) {

                    }
                });

        //最终封装
        RequestUtils.getTimetable(this, new MyObserver<DemoBeanVo>(this) {
            @Override
            public void onSuccess(DemoBeanVo demo) {

            }

            @Override
            public void onFailure(Throwable e, String errorMsg) {

            }
        });

        //自定义OkHttpClient
        OkHttpClient client = new OkHttpClient().newBuilder()
                .readTimeout(Constants.DEFAULT_TIME, TimeUnit.SECONDS) //设置读取超时时间
                .connectTimeout(Constants.DEFAULT_TIME, TimeUnit.SECONDS) //设置请求超时时间
                .writeTimeout(Constants.DEFAULT_TIME, TimeUnit.SECONDS) //设置写入超时时间
                .addInterceptor(new LogInterceptor()) //设置打印拦截器
                .retryOnConnectionFailure(true) //设置出现错误进行重新连接
                .build();

        //1、创建retrofit对象
        Retrofit retrofit1 = new Retrofit.Builder()
                .baseUrl(Constants.BaseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //2、获取接口对象
        ApiService apiService1 = retrofit1.create(ApiService.class);
        //构建要上传的文件
        String fileName = null;
        File file = new File(fileName);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/otcet-stream"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("aFile", fileName, requestBody);

        String descriptionString = "This is a description";
        RequestBody description = RequestBody.create(MediaType.parse("multipart/form-data"), descriptionString);

        //3、调用接口上传方法
        Call<DemoBeanVo> upload = (Call<DemoBeanVo>) apiService1.upload(description, body);
        //4、异步请求
        upload.enqueue(new Callback<DemoBeanVo>() {
            @Override
            public void onResponse(Call<DemoBeanVo> call, Response<DemoBeanVo> response) {
                Log.e(TAG, "onResponse: 上传成功！" + response.message());
            }

            @Override
            public void onFailure(Call<DemoBeanVo> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });


        //1、获取retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                //设置自定义的OKhttp
                .client(client)
                .baseUrl(Constants.BaseUrl)
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
                //对执行线程和绑定生命周期几个方法进行封装
                .compose(RxHelper.observableIO2Main(this))
//                .subscribe(new BaseObserver<DemoBeanVo>() {
//                    @Override
//                    public void onSuccess(DemoBeanVo demo) {
//                        Log.e(TAG, "onSuccess: " + demo);
//                    }
//
//                    @Override
//                    public void onFailure(Throwable e, String errorMsg) {
//                        Log.e(TAG, "onFailure: " + errorMsg);
//                    }
//                });
               .subscribe(new Observer<BaseResponse<DemoBeanVo>>() {
                   @Override
                   public void onSubscribe(Disposable d) {

                   }

                   @Override
                   public void onNext(BaseResponse<DemoBeanVo> demoBeanVoBaseResponse) {

                   }

                   @Override
                   public void onError(Throwable e) {

                   }

                   @Override
                   public void onComplete() {

                   }
               });


        //2、获取接口对象
        ApiService apiService = retrofit.create(ApiService.class);

        //3、调用请求方法
        final Call<TimetableVo> timetable = (Call<TimetableVo>) apiService.getTimetable();

        //----同步请求-----
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response<TimetableVo> response = timetable.execute();
                    int code = response.body().getRes_code();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        //-----去异步请求操作-----
        timetable.enqueue(new Callback<TimetableVo>() {
            @Override
            public void onResponse(Call<TimetableVo> call, Response<TimetableVo> response) {
                TimetableVo body = response.body();
                Log.e(TAG, "请求成功的信息：" + response.body().toString());
            }

            @Override
            public void onFailure(Call<TimetableVo> call, Throwable t) {
                Log.e(TAG, "请求失败的信息：" + t.getMessage());
            }
        });
    }
}
