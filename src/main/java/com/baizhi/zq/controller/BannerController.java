package com.baizhi.zq.controller;

import com.baizhi.zq.entity.Banner;
import com.baizhi.zq.service.BannerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/banner")
public class BannerController {
    @Resource
    BannerService bannerService;

    @RequestMapping("/queryAllBanners")
    @ResponseBody
    public Map<String,Object> queryAllBanners(Integer rows, Integer page){
        Map<String, Object> map = bannerService.queryAllBanners(rows,page);
        return map;
    }

    @RequestMapping("/edits")
    @ResponseBody
    public String edits(Banner banner, String oper){
        String id=null;
        if ("edit".equals(oper)){
            //修改
            bannerService.modifyOneBanner(banner);
        }else if("add".equals(oper)){
            //添加
            id = bannerService.addOneBanner(banner);
        }else {
            //删除
            banner.setStatus("0");
            bannerService.modifyOneBanner(banner);
        }
        return id;
    }

    @RequestMapping("/uploadBanner")
    @ResponseBody
    public void uploadBanner(MultipartFile src_img , String id, HttpServletRequest request){
        bannerService.uploadBanner(src_img,id,request);
    }
}
