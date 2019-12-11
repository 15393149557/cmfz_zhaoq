package com.baizhi.zq.service;

import com.baizhi.zq.annotation.AddCache;
import com.baizhi.zq.dao.ChapterMapper;
import com.baizhi.zq.entity.Album;
import com.baizhi.zq.entity.AlbumExample;
import com.baizhi.zq.entity.Chapter;
import com.baizhi.zq.entity.ChapterExample;
import com.baizhi.zq.util.UUIDUtil;
import org.apache.ibatis.session.RowBounds;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class ChapterServiceImpl implements ChapterService {
    @Resource
    ChapterMapper chapterMapper;

    @AddCache
    @Override
    public Map<String, Object> queryAllChapters(Integer rows, Integer page,String rowId) {
        Map<String, Object> map = new HashMap<>();
        //设置查询条件
        ChapterExample example = new ChapterExample();
        example.createCriteria().andAlbumIdEqualTo(rowId);

        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);
        List<Chapter> chapters = chapterMapper.selectByExampleAndRowBounds(example,rowBounds);
        //设置rows属性
        map.put("rows",chapters);

        //设置总页数total属性
        List<Chapter> chapters1 = chapterMapper.selectAll();
        int totalPage = 0;
        if(chapters1.size()%rows==0){
            totalPage=chapters1.size()/rows;
        }else{
            totalPage=chapters1.size()/rows+1;
        }
        map.put("total",totalPage);
        //设置当前页page属性
        map.put("page",page);
        //设置总条数records属性
        map.put("records",chapters.size());
        return map;
    }

    @Override
    public void modifyOneChapter(Chapter chapter) {
        AlbumExample albumExample = new AlbumExample();
        albumExample.createCriteria().andIdEqualTo(chapter.getId());
        chapterMapper.updateByExample(chapter,albumExample);
    }

    @Override
    public String addOneChapter(Chapter chapter) {
        System.out.println(chapter);
        //获取uuid
        String uuid = UUIDUtil.getUUID();
        chapter.setId(uuid);
        chapterMapper.insert(chapter);
        return uuid;
    }

    @Override
    public void deleteOneChapter(Chapter chapter) {
        chapterMapper.delete(chapter);
    }

    @Override
    public void uploadChapter(MultipartFile src, String id, HttpServletRequest request) {
        //先把图片上传到服务器中，然后再把该轮播图的路径改了
        //1.获取到图片要上传的最终路径
        String realPath = request.getRealPath("/upload/audios");

        File file = new File(realPath);
        if(!file.exists()){
            file.mkdir();
        }

        //2.获取到图片的真实名称
        String filename = src.getOriginalFilename();
        //重新命名
        String newName = new Date().getTime()+"_"+filename;
        System.out.println(filename);
        System.out.println(newName);
        //3.将图片上传到指定路径
        try {
            src.transferTo(new File(realPath,newName));


            //获取文件时长   分
            AudioFile audioFile = null;
            audioFile = AudioFileIO.read(new File(realPath,newName));
            AudioHeader audioHeader = audioFile.getAudioHeader();

            //获取音频大小    zijie
            long size = src.getSize();
            System.out.println("文件大小 :"+size);
            String sizes = size/1024/1024+"MB";
            System.out.println("文件大小 :"+sizes);

            int length = audioHeader.getTrackLength();
            String duration=length/60+"分"+length%60+"秒";
            //由于添加时上传的图片路径是错误的，在此处需要修改图片信息
            ChapterExample chapterExample = new ChapterExample();
            chapterExample.createCriteria().andIdEqualTo(id);
            Chapter chapter = chapterMapper.selectOneByExample(chapterExample);
            chapter.setDuration(duration);
            chapter.setSrc(newName);
            chapter.setSize(sizes);


            //修改chapter的图片路径
            chapterExample.createCriteria().andIdEqualTo(chapter.getId());
            chapterMapper.updateByExample(chapter,chapterExample);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //接口方法
    @AddCache
    @Override
    public List<Chapter> queryAllChapterByAlbumId(String id) {
        ChapterExample chapterExample = new ChapterExample();
        chapterExample.createCriteria().andAlbumIdEqualTo(id);
        List<Chapter> chapters = chapterMapper.selectByExample(chapterExample);
        return chapters;
    }
}
