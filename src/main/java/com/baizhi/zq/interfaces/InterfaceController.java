package com.baizhi.zq.interfaces;

import com.baizhi.zq.dao.ChapterMapper;
import com.baizhi.zq.entity.Album;
import com.baizhi.zq.entity.Article;
import com.baizhi.zq.entity.Banner;
import com.baizhi.zq.entity.Chapter;
import com.baizhi.zq.service.AlbumService;
import com.baizhi.zq.service.ArticleService;
import com.baizhi.zq.service.BannerService;
import com.baizhi.zq.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//根据接口文档响应前台数据
@RequestMapping
@Controller
public class InterfaceController {

    @Autowired
    public BannerService bannerService;

    @Autowired
    public AlbumService albumService;

    @Autowired
    public ArticleService articleService;

    @Autowired
    public ChapterService chapterService;


    //首页接口方法
    @RequestMapping("/first_page")
    @ResponseBody
    public Map<String , Object> firstPage(String uid, String type, String sub_type){

        System.out.println(uid);

        HashMap<String, Object> map = new HashMap<>();

        if(type.equals("all")){
            //首页
            List<Banner> banners = bannerService.queryAllBanner();
            List<Album> albums = albumService.queryAllAlbum();
            List<Article> articles = articleService.queryAllArticle();
            map.put("banner",banners);
            map.put("album",albums);
            map.put("article",articles);
        }
        if(type.equals("wen")){
            //展示闻数据
            List<Album> albums = albumService.queryAllAlbum();
            map.put("album",albums);
        }
        if(type.equals("si")){
            //展示思数据
            if(sub_type.equals("ssyj")){
                List<Article> articles = articleService.queryAllArticle();
                map.put("article",articles);
            }else if(sub_type.equals("xmfy")){
                List<Article> articles = articleService.queryAllArticle();
                map.put("article",articles);
            }
        }

        return map;
    }


    //闻的详情页接口
    @RequestMapping("/detail/wen")
    @ResponseBody
    public HashMap<String ,Object> wen(String id,String uid){
        HashMap<String, Object> map = new HashMap<>();

        Album album = albumService.queryOneAlbum(id);
        map.put("introduction",album);

        List<Chapter> chapters = chapterService.queryAllChapterByAlbumId(id);
        map.put("list",chapters);

        return map;
    }
}
