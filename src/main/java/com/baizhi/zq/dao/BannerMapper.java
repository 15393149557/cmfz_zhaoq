package com.baizhi.zq.dao;

import com.baizhi.zq.entity.Banner;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


public interface BannerMapper extends Mapper<Banner> {
    public List<Banner> selectBannersByPage(@Param("rows") Integer rows, @Param("page") Integer page);

    public void updateOneBanner(Banner banner);
}
