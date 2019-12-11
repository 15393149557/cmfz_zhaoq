package com.baizhi.zq.service;

import com.baizhi.zq.entity.Article;

import java.util.List;
import java.util.Map;

public interface ArticleService {
    public Map<String, Object> queryAllArticles(Integer page, Integer rows);

    public void deleteOneArticle(Article article);

    public void add(Article article);

    public void update(Article article);

    //用于接口的方法
    public List<Article>  queryAllArticle();
}
