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

    //用elasticSearch查询出数据未做高亮
    public List<Article> queryAllByElastic(String keyword);

    //用elasticSearch查询出数据做高亮
    public List<Article> queryAllByElastics(String keyword);
}
