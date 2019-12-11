package com.baizhi.zq.annotation;

import java.lang.annotation.*;
//自定义注解用来标识删除缓存的方法
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DelCache {

}
