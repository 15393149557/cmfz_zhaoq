package com.baizhi.zq.annotation;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)   //添加在方法上
@Retention(RetentionPolicy.RUNTIME) //生命周期
public @interface AddCache {

}
