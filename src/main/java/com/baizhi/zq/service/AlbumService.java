package com.baizhi.zq.service;

import com.baizhi.zq.entity.Album;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface AlbumService {
    //查询所有album专辑数据
    public Map<String,Object> queryAllAlbums(Integer rows, Integer page);

    //添加
    public String addOneAlbum(Album album);


    public void modifyOneAlbum(Album album);

    public void deleteOneAlbum(Album album);

    public void uploadAlbum(MultipartFile cover_img, String id, HttpServletRequest request);

    //用于接口的方法
    public List<Album> queryAllAlbum();
    public Album queryOneAlbum(String id);
}
