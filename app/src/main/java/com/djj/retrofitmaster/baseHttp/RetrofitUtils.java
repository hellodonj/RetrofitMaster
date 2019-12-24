package com.djj.retrofitmaster.baseHttp;

import androidx.annotation.NonNull;

import com.djj.retrofitmaster.ApiService;
import com.djj.retrofitmaster.Constants;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * desc:Retrofit对象获取的封装
 * author:djj
 * date:2019/12/8 15:37
 */
public class RetrofitUtils {

    private static final String TAG = "RetrofitUtils";
    private static ApiService mApiService;

    /**
     * 单例模式
     */
    public static ApiService getApiServer() {
        if (mApiService == null) {
            synchronized (RetrofitUtils.class) {
                if (mApiService == null) {
                    mApiService = new RetrofitUtils().getRetrofit();
                }
            }
        }
        return mApiService;
    }

    private RetrofitUtils() {
    }

    public ApiService getRetrofit() {
        // 初始化Retrofit
        ApiService apiService = initRetrofit(initOkHttp()).create(ApiService.class);
        return apiService;
    }

    /**
     * 初始化Retrofit
     */
    @NonNull
    private Retrofit initRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                .client(client)
                .baseUrl(Constants.BaseUrl)
                //添加Gson解析，返回数据转化成GSON类型
                .addConverterFactory(GsonConverterFactory.create())
                //添加Rxjava的支持
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    /**
     * 初始化okhttp
     */
    @NonNull
    private OkHttpClient initOkHttp() {
        return new OkHttpClient().newBuilder()
                .readTimeout(Constants.DEFAULT_TIME, TimeUnit.SECONDS)//设置读取超时时间
                .connectTimeout(Constants.DEFAULT_TIME, TimeUnit.SECONDS)//设置请求超时时间
                .writeTimeout(Constants.DEFAULT_TIME, TimeUnit.SECONDS)//设置写入超时时间
                .addInterceptor(new LogInterceptor())//添加打印拦截器
                .retryOnConnectionFailure(true)//设置出现错误进行重新连接。
                .build();
    }
}
