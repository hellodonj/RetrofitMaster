package com.djj.retrofitmaster.baseHttp;


import android.util.Log;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * desc:创建Base抽象类实现Observer
 * author:djj
 * date:2019/12/8 15:20
 */
public abstract class BaseObserver<T> implements Observer<BaseResponse<T>> {

    private static final String TAG = "BaseObserver";

    @Override
    public void onSubscribe(Disposable d) {
        Log.e(TAG, "onSubscribe: ");
    }

    @Override
    public void onNext(BaseResponse<T> response) {
        //d对基础数据进行处理
        if (response.getRes_code() == 200) {
            onSuccess(response.getDemo());
        } else {
            onFailure(null, response.getErr_msg());
        }
    }

    @Override
    public void onError(Throwable e) {
        onFailure(e,RxExceptionUtil.exceptionHandler(e));
        Log.e(TAG, "Throwable: " + e.getMessage());
    }

    @Override
    public void onComplete() {
        Log.e(TAG, "onComplete: ");
    }

    public abstract void onSuccess(T demo);

    public abstract void onFailure(Throwable e, String errorMsg);
}
