package com.baizhi.zq.controller;

import com.baizhi.zq.entity.Album;
import com.baizhi.zq.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/album")
public class AlbumController {

    @Autowired
    AlbumService albumService;

    @RequestMapping("/queryAll")
    @ResponseBody
    public Map<String,Object> queryAllBanners(Integer rows, Integer page){
        Map<String, Object> map = albumService.queryAllAlbums(rows,page);
        return map;
    }

    @RequestMapping("/edit")
    @ResponseBody
    public String edit(Album album, String oper){
        String id=null;
        if ("edit".equals(oper)){
            //修改
            albumService.modifyOneAlbum(album);
        }else if("add".equals(oper)){
            //添加
            id = albumService.addOneAlbum(album);
        }else {
            //删除  需要先判断底层存在chapter否;

            albumService.deleteOneAlbum(album);
        }
        return id;
    }

    @RequestMapping("/uploadAlbum")
    @ResponseBody
    public void uploadAlbum(MultipartFile cover_img , String id, HttpServletRequest request){
        albumService.uploadAlbum(cover_img,id,request);
    }
}
