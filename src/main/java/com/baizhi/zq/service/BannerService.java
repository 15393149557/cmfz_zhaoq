package com.baizhi.zq.service;

import com.baizhi.zq.entity.Banner;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface BannerService {
    public Map<String,Object> queryAllBanners(Integer rows, Integer page);

    public String addOneBanner(Banner banner);

    public void uploadBanner(MultipartFile src_img , String id, HttpServletRequest request);

    public void modifyOneBanner(Banner banner);

    //用于接口的方法
    public List<Banner> queryAllBanner();
}
