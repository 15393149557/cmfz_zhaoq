package com.baizhi.zq.dao;

import com.baizhi.zq.entity.Album;
import com.baizhi.zq.entity.AlbumExample;
import java.util.List;


import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface AlbumMapper extends Mapper<Album> {
    public List<Album> selectAllAlbums(@Param("rows") Integer rows, @Param("page") Integer page);

}