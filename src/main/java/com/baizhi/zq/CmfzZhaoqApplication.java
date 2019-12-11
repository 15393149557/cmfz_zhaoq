package com.baizhi.zq;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@tk.mybatis.spring.annotation.MapperScan("com.baizhi.zq.dao")
@org.mybatis.spring.annotation.MapperScan("com.baizhi.zq.dao")   //注意在这要将org的注解放在tk的下面  解决自定义mapper和自动生成mapper之间的错误
public class CmfzZhaoqApplication {

    public static void main(String[] args) {
        SpringApplication.run(CmfzZhaoqApplication.class, args);
    }

}
