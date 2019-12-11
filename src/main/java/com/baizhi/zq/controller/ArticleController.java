package com.baizhi.zq.controller;

import com.baizhi.zq.entity.Article;
import com.baizhi.zq.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("/article")
public class ArticleController {

    @Resource
    ArticleService articleService;

    @RequestMapping("/queryAllArticles")
    @ResponseBody
    public Map<String,Object> queryAllArticles(Integer page, Integer rows){
        Map<String, Object> map = articleService.queryAllArticles(page, rows);
        return map;
    }

    @RequestMapping("/edit")
    @ResponseBody
    public void edit(String oper, Article article){
        if("edit".equals(oper)){
            //修改
        }else if("add".equals(oper)){
            //添加
        }else{
            //删除
            System.out.println(article);
            articleService.deleteOneArticle(article);
        }
    }

    @RequestMapping("/add")
    @ResponseBody
    public void add(Article article){
        System.out.println(article);
        articleService.add(article);
    }

    @RequestMapping("/update")
    @ResponseBody
    public void update(Article article){
        System.out.println(article);
        articleService.update(article);
    }
}
