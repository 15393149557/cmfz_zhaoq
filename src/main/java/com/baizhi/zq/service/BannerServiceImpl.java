package com.baizhi.zq.service;

import com.baizhi.zq.annotation.AddCache;
import com.baizhi.zq.dao.BannerMapper;
import com.baizhi.zq.entity.Banner;
import com.baizhi.zq.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class BannerServiceImpl implements BannerService {
    @Resource
    BannerMapper bannerMapper;

    @AddCache
    @Override   //查询出表格展示的数据并用对应格式的json串返回
    public Map<String, Object> queryAllBanners(Integer rows, Integer page) {
        Map<String, Object> map = new HashMap<>();
        List<Banner> banners = bannerMapper.selectBannersByPage(rows, page);
        //设置rows属性
        map.put("rows",banners);

        //设置总页数total属性
        List<Banner> banners1 = bannerMapper.selectAll();
        int totalPage = 0;
        if(banners1.size()%rows==0){
            totalPage=banners1.size()/rows;
        }else{
            totalPage=banners1.size()/rows+1;
        }
        map.put("total",totalPage);
        //设置当前页page属性
        map.put("page",page);
        //设置总条数records属性
        map.put("records",banners.size());
        return map;
    }

    @Override
    public String addOneBanner(Banner banner) {
        //生成uuid
        String uuid = UUIDUtil.getUUID();
        banner.setId(uuid);
        bannerMapper.insert(banner);
        //返回uuid用于后续对错误的图片路径做修改
        return uuid;
    }

    @Override
    public void uploadBanner(MultipartFile src_img, String id, HttpServletRequest request) {
        //先把图片上传到服务器中，然后再把该轮播图的路径改了
        //1.获取到图片要上传的最终路径
        String realPath = request.getRealPath("/upload/photo");

        File file = new File(realPath);
        if(!file.exists()){
            file.mkdir();
        }

        //2.获取到图片的真实名称
        String filename = src_img.getOriginalFilename();
        //重新命名
        String newName = new Date().getTime()+"_"+filename;

        //3.将图片上传到指定路径
        try {
            src_img.transferTo(new File(realPath,newName));

            //由于添加时上传的图片路径是错误的，在此处需要修改图片信息
            Banner banner = new Banner();
            banner.setId(id);
            banner.setSrc_img(newName);
            banner.setStatus("0");
            bannerMapper.updateOneBanner(banner);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void modifyOneBanner(Banner banner) {
        System.out.println(banner);
        if("".equals(banner.getSrc_img())){
            banner.setSrc_img(null);
        }
        bannerMapper.updateOneBanner(banner);
    }

    @AddCache
    @Override
    public List<Banner> queryAllBanner() {
        List<Banner> banners = bannerMapper.selectAll();
        return banners;
    }


}
