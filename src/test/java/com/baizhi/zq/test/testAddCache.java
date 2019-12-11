package com.baizhi.zq.test;

import com.baizhi.zq.CmfzZhaoqApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CmfzZhaoqApplication.class)
public class testAddCache {

    @Resource
    RedisTemplate redisTemplate;

    @Test
    public void test1(){
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set("小黑","xiaohei");

        Object o = valueOperations.get("小黑");
        System.out.println(o);

    }
}
