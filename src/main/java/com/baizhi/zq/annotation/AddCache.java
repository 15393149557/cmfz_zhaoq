package com.baizhi.zq.annotation;

import java.lang.annotation.*;
//自定义注解，用于添加缓存的方法上
@Documented
@Target(ElementType.METHOD)   //添加在方法上
@Retention(RetentionPolicy.RUNTIME) //生命周期
public @interface AddCache {

}
