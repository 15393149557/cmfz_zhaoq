package com.baizhi.zq.test;

import com.baizhi.zq.CmfzZhaoqApplication;
import com.baizhi.zq.dao.AdminMapper;
import com.baizhi.zq.dao.AlbumMapper;
import com.baizhi.zq.dao.BannerMapper;
import com.baizhi.zq.entity.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sun.net.www.content.image.png;

import javax.annotation.Resource;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CmfzZhaoqApplication.class)
public class MBGTest {

    @Resource
    AdminMapper adminMapper;

    @Resource
    BannerMapper bannerMapper;

    @Resource
    AlbumMapper albumMapper;


    @Test
    public void test1(){
        adminMapper.insert(new Admin("3","administor","123456"));
    }

    @Test
    public void test2(){
        AdminExample adminExample = new AdminExample();
        adminExample.createCriteria().andUsernameEqualTo("admin");
        Admin admin = adminMapper.selectOneByExample(adminExample);
        System.out.println(admin);
    }

    @Test
    public void test3(){
        BannerExample bannerExample = new BannerExample();
        bannerExample.createCriteria().andIdEqualTo("15fb65c6608c4143b8d2745500501bee");
        Banner banner = bannerMapper.selectOneByExample(bannerExample);
        System.out.println(banner);
    }

    @Test
    public void test4(){
        AdminExample example = new AdminExample();
        example.createCriteria().andIdEqualTo("1");
        int i = albumMapper.updateByExample(new Album("1","甘露妙宝","1. png ",100,"柴守国","柴守国",23,"ayayao",new Date()), example);
        System.out.println(i);
    }
}
