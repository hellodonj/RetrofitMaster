package com.djj.retrofitmaster.baseHttp;

/**
 * desc: 基础数据的抽象
 * author:djj
 * date:2019/12/8 15:18
 */
public class BaseResponse<T> {

    private int res_code;
    private String err_msg;
    private T demo;


    public int getRes_code() {
        return res_code;
    }

    public void setRes_code(int res_code) {
        this.res_code = res_code;
    }

    public String getErr_msg() {
        return err_msg;
    }

    public void setErr_msg(String err_msg) {
        this.err_msg = err_msg;
    }

    public T getDemo() {
        return demo;
    }

    public void setDemo(T demo) {
        this.demo = demo;
    }
}