package com.baizhi.zq.controller;

import com.baizhi.zq.entity.Banner;
import com.baizhi.zq.entity.Chapter;
import com.baizhi.zq.service.AlbumService;
import com.baizhi.zq.service.ChapterService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.util.Map;

@Controller
@RequestMapping("chapter")
public class ChapterController {

    @Autowired
    ChapterService chapterService;

    @RequestMapping("/queryAll")
    @ResponseBody
    public Map<String,Object> queryAllBanners(Integer rows, Integer page,String rowId){
        Map<String, Object> map = chapterService.queryAllChapters(rows,page,rowId);
        return map;
    }

    @RequestMapping("/edit")
    @ResponseBody
    public String edit(Chapter chapter, String oper){
        String id= null;
        if ("edit".equals(oper)){
            //修改
            chapterService.modifyOneChapter(chapter);
        }else if("add".equals(oper)){
            //添加
            id =chapterService.addOneChapter(chapter);
        }else {
            //删除
            chapterService.deleteOneChapter(chapter);
        }
        return id;
    }

    @RequestMapping("/uploadChapter")
    @ResponseBody
    public void uploadChapter(MultipartFile src , String id, HttpServletRequest request){
        chapterService.uploadChapter(src,id,request);
    }


    @RequestMapping("/download")
    public void downloadAudio(String fileName, HttpServletRequest request, HttpServletResponse response){
        String realPath = request.getServletContext().getRealPath("/upload/audios");
        //读取文件输入流
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(new File(realPath, fileName));

            //设置响应的方式   响应头   //attachment;以附件形式下载   inline 在线打开
            response.setHeader("content-disposition","attachment;fileName=" + URLEncoder.encode(fileName,"UTF-8"));

            //下载文件
            IOUtils.copy(fis,response.getOutputStream());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
