package com.djj.retrofitmaster.base;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * desc:定义一个空的注解，然后在需要使用EventBus的Activity或者Fragment中绑定该注解
 * author:djj
 * date:2019/12/4 20:49
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface BindEventBus {
}
