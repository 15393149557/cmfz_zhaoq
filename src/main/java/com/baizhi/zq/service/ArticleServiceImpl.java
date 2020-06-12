package com.baizhi.zq.service;

import com.baizhi.zq.dao.ArticleMapper;
import com.baizhi.zq.entity.Article;
import com.baizhi.zq.entity.ArticleExample;
import com.baizhi.zq.repository.ArticleRepository;
import com.baizhi.zq.util.UUIDUtil;
import org.apache.ibatis.session.RowBounds;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Resource
    ArticleMapper articleMapper;

    @Resource
    ArticleRepository articleRepository;

    @Resource
    ElasticsearchTemplate elasticsearchTemplate;

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

        //向索引库添加索引
        articleRepository.save(article);
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

    @Override
    public List<Article> queryAllByElastic(String keyword) {
        NativeSearchQuery build = new NativeSearchQueryBuilder()
                .withIndices("cmfz") //指定索引名
                .withTypes("article")  //指定索引类型
                .withQuery(QueryBuilders.queryStringQuery(keyword).field("title").field("content"))
                .build();

        List<Article> articles = elasticsearchTemplate.queryForList(build, Article.class);

        return articles;
    }

    @Override
    public List<Article> queryAllByElastics(String keyword) {
        HighlightBuilder.Field field = new HighlightBuilder.Field("*");
        field.preTags("<span style='color:red'>"); //前缀
        field.postTags("</span>"); //后缀

        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withIndices("cmfz") //指定索引名
                .withTypes("article")  //指定索引类型
                .withQuery(QueryBuilders.queryStringQuery(keyword).field("title").field("content")) //搜索条件
                .withHighlightFields(field)//处理高亮
                //.withFields("id","title","content")
                .build();

        AggregatedPage<Article> articles = elasticsearchTemplate.queryForPage(searchQuery, Article.class, new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {

                ArrayList<Article> articleList = new ArrayList<>();

                //获取查询结果
                SearchHit[] hits = searchResponse.getHits().getHits();

                for (SearchHit hit : hits) {

                    //处理数据  获取数据的集合
                    Map<String, Object> sourceAsMap = hit.getSourceAsMap();

                    String id=sourceAsMap.get("id")!=null?sourceAsMap.containsKey("id")?sourceAsMap.get("id").toString():null:null;
                    String title=sourceAsMap.get("title")!=null?sourceAsMap.containsKey("title")?sourceAsMap.get("title").toString():null:null;
                    String guruName=sourceAsMap.get("guruName")!=null?sourceAsMap.containsKey("guruName")?sourceAsMap.get("guruName").toString():null:null;
                    String guruId=sourceAsMap.get("guruId")!=null?sourceAsMap.containsKey("guruId")?sourceAsMap.get("guruId").toString():null:null;
                    String content=sourceAsMap.get("content")!=null?sourceAsMap.containsKey("content")?sourceAsMap.get("content").toString():null:null;

                    Date uploadTime = null;
                    if(sourceAsMap.get("uploadTime")!=null){
                        boolean times = sourceAsMap.containsKey("uploadTime");
                        if(times){
                            String uploadTimes = sourceAsMap.get("uploadTime").toString();
                            //处理日期操作
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                            try {
                                uploadTime = dateFormat.parse(uploadTimes);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    Article article = new Article(id,title,uploadTime,guruName,guruId,content);

                    //处理高亮  获取高亮的集合
                    Map<String, HighlightField> highlightFields = hit.getHighlightFields();

                    //判断title 不等于null才处理高亮
                    if(title!=null){
                        if(highlightFields.get("title")!=null){
                            HighlightField title1 = highlightFields.get("title");
                            String titles = highlightFields.get("title").fragments()[0].toString();
                            article.setTitle(titles);
                        }
                    }

                    if(content!=null){
                        if(highlightFields.get("content")!=null){
                            String contents = highlightFields.get("content").fragments()[0].toString();
                            article.setContent(contents);
                        }
                    }


                    //将封装好的对象放入集合
                    articleList.add(article);
                }

                //强转 返回
                return new AggregatedPageImpl<T>((List<T>) articleList);
            }
        });

        //获取到做过高亮的数据集合返回
        List<Article> content1 = articles.getContent();

        for (Article article : content1) {
            System.out.println("==="+article);
        }
        return content1;
    }

}
