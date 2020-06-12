package com.baizhi.zq.repository;

import com.baizhi.zq.entity.Article;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

                                               //参数：<操作对象类型,序列化主键的类型>
public interface ArticleRepository extends ElasticsearchRepository<Article,String> {
}
