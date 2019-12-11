package com.baizhi.zq.service;

import com.baizhi.zq.dao.ArticleMapper;
import com.baizhi.zq.entity.Article;
import com.baizhi.zq.entity.ArticleExample;
import com.baizhi.zq.util.UUIDUtil;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class ArticleServiceImpl implements ArticleService {
    @Resource
    ArticleMapper articleMapper;

    @Override
    public Map<String, Object> queryAllArticles(Integer page, Integer rows) {
        //返回数据的类型
        Map<String, Object> map = new HashMap<>();

        //1.设置总条数records
        ArticleExample example = new ArticleExample();
        int records = articleMapper.selectCountByExample(example);
        map.put("records",records);
        //2.设置总页数total
        int total=0;
        if(records%rows==0){
            total = records/rows;
        }else{
            total = records/rows+1;
        }
        map.put("total",total);
        //3.设置当前页page
        map.put("page",page);
        //4.设置查询到的数据rows
        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);
        List<Article> articles = articleMapper.selectByRowBounds(new Article(), rowBounds);
        map.put("rows",articles);
        return map;
    }

    @Override
    public void deleteOneArticle(Article article) {
        ArticleExample example = new ArticleExample();
        example.createCriteria().andIdEqualTo(article.getId());
        articleMapper.deleteByExample(example);
    }

    @Override
    public void add(Article article) {
        article.setId(UUIDUtil.getUUID());
        article.setUploadTime(new Date());
        articleMapper.insert(article);
    }

    @Override
    public void update(Article article) {
        ArticleExample example = new ArticleExample();
        example.createCriteria().andIdEqualTo(article.getId());
        articleMapper.updateByExampleSelective(article,example);
    }

    @Override
    public List<Article> queryAllArticle() {
        List<Article> articles = articleMapper.selectAll();
        return articles;
    }

}
