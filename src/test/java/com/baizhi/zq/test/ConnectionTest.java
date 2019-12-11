package com.baizhi.zq.test;

import com.baizhi.zq.CmfzZhaoqApplication;
import com.baizhi.zq.dao.BannerMapper;
import com.baizhi.zq.entity.Banner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CmfzZhaoqApplication.class)
public class ConnectionTest {

    @Resource
    BannerMapper bannerMapper;
    @Test
    public void test1(){
        List<Banner> banners = bannerMapper.selectBannersByPage(2, 1);
        System.out.println(banners);
    }
}
