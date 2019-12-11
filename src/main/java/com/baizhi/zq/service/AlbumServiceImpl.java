package com.baizhi.zq.service;

import com.baizhi.zq.annotation.AddCache;
import com.baizhi.zq.dao.AlbumMapper;
import com.baizhi.zq.dao.ChapterMapper;
import com.baizhi.zq.entity.*;
import com.baizhi.zq.util.UUIDUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class AlbumServiceImpl implements AlbumService {

    @Resource
    AlbumMapper albumMapper;


    @Resource
    ChapterMapper chapterMapper;

    @AddCache
    @Override
    public Map<String, Object> queryAllAlbums(Integer rows, Integer page) {
        Map<String, Object> map = new HashMap<>();
        List<Album> albums = albumMapper.selectAllAlbums(rows, page);
        //设置rows属性
        map.put("rows",albums);

        //设置总页数total属性
        List<Album> albums1 = albumMapper.selectAll(); //查询出所有的album
        int totalPage = 0;
        if(albums1.size()%rows==0){
            totalPage=albums1.size()/rows;
        }else{
            totalPage=albums1.size()/rows+1;
        }
        map.put("total",totalPage);
        //设置当前页page属性
        map.put("page",page);
        //设置总条数records属性
        map.put("records",albums.size());
        return map;
    }

    @Override
    public String addOneAlbum(Album album) {
        //获取uuid
        String uuid = UUIDUtil.getUUID();
        album.setId(uuid);
        albumMapper.insert(album);
        return uuid;
    }

    @Override
    public void modifyOneAlbum(Album album) {
        AlbumExample albumExample = new AlbumExample();
        albumExample.createCriteria().andIdEqualTo(album.getId());
        if("".equals(album.getCoverImg())) album.setCoverImg(null);
        albumMapper.updateByExample(album,albumExample);
    }

    @Override
    public void deleteOneAlbum(Album album) {
        //设置条件查询出该album下边的chapter
        ChapterExample chapterExample = new ChapterExample();
        chapterExample.createCriteria().andAlbumIdEqualTo(album.getId());
        List<Chapter> chapters = chapterMapper.selectByExample(chapterExample);

        if(chapters==null||chapters.size()==0){
            //删除
            albumMapper.delete(album);
        }else{

        }
    }

    @Override
    public void uploadAlbum(MultipartFile cover_img, String id, HttpServletRequest request) {
        //先把图片上传到服务器中，然后再把该轮播图的路径改了
        //1.获取到图片要上传的最终路径
        String realPath = request.getRealPath("/upload/photo");

        File file = new File(realPath);
        if(!file.exists()){
            file.mkdir();
        }

        //2.获取到图片的真实名称
        String filename = cover_img.getOriginalFilename();
        //重新命名
        String newName = new Date().getTime()+"_"+filename;

        //3.将图片上传到指定路径
        try {
            cover_img.transferTo(new File(realPath,newName));

            //由于添加时上传的图片路径是错误的，在此处需要修改图片信息
            AlbumExample albumExample = new AlbumExample();
            albumExample.createCriteria().andIdEqualTo(id);
            Album album = albumMapper.selectOneByExample(albumExample);
            album.setCoverImg(newName);

            //修改album的图片路径
            AlbumExample example = new AlbumExample();
            example.createCriteria().andIdEqualTo(album.getId());
            albumMapper.updateByExample(album,example);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //接口调用方法
    @AddCache
    @Override
    public List<Album> queryAllAlbum() {
        List<Album> albums = albumMapper.selectAll();
        return albums;
    }

    @Override
    public Album queryOneAlbum(String id) {
        AlbumExample albumExample = new AlbumExample();
        albumExample.createCriteria().andIdEqualTo(id);
        Album album = albumMapper.selectOneByExample(albumExample);
        return album;
    }


}
