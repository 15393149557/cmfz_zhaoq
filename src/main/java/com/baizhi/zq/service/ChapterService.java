package com.baizhi.zq.service;

import com.baizhi.zq.entity.Banner;
import com.baizhi.zq.entity.Chapter;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface ChapterService {
    //分页查询所有chapter
    public Map<String,Object> queryAllChapters(Integer rows, Integer page,String row_id);

    public void modifyOneChapter(Chapter chapter);

    public String addOneChapter(Chapter chapter);

    public void deleteOneChapter(Chapter chapter);

    public void uploadChapter(MultipartFile title, String id, HttpServletRequest request);

    //接口调用的方法
    public List<Chapter> queryAllChapterByAlbumId(String id);
}
