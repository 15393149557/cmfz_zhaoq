package com.baizhi.zq.cache;

import com.baizhi.zq.annotation.AddCache;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;
import java.lang.reflect.Method;

@Aspect
@Configuration
public class RedisCacheHashAndAnnotation {

    @Resource
    RedisTemplate redisTemplate;

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Around("@annotation(com.baizhi.zq.annotation.AddCache)")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) {

        System.out.println("===环绕通知===");

        /*序列化解决乱码*/
        StringRedisSerializer serializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(serializer);
        redisTemplate.setHashKeySerializer(serializer);


        //在判断缓存中是否有该数据   判断是否要查询数据库

        //用来拼接存入到redis当中的key
        StringBuilder sb = new StringBuilder();

        //获取该方法所在类的全限定名
        String name = proceedingJoinPoint.getTarget().getClass().getName();


        //获取该方法的名字
        String function = proceedingJoinPoint.getSignature().getName();
        sb.append(function);

        //获取实参
        Object[] args = proceedingJoinPoint.getArgs();

        for (Object arg : args) {
            sb.append(arg);
        }

        //拼接好的key  方法名+实参
        String key = sb.toString();

        //获取到key对应的数据
        Boolean aBoolean = redisTemplate.opsForHash().hasKey(name, key);


        //返回值 result
        Object result = null;

        if(!aBoolean){
            //不存在则 从数据库当中查询出数据  添加结果到缓存当中
            try {
                result = proceedingJoinPoint.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }

            //将其添加到缓存当中
            redisTemplate.opsForHash().put(name,key,result);
        }else{
            //存在则将得到的数据返回
            result = redisTemplate.opsForHash().get(name,key);
        }

        return result;


    }


    @After("@annotation(com.baizhi.zq.annotation.DelCache)")
    public void delCache(JoinPoint joinPoint){
        //获取到类 的全限定名
        String name = joinPoint.getTarget().getClass().getName();

        redisTemplate.delete(name);
    }

}
