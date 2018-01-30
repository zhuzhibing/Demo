package net.luculent.automatioin.laks.services.article.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ArticleDao {

   int add(Map<String, Object> article);

   int modify(Map<String, Object> article);

   int delete(int atcSeq);

   Map<String, Object> queryOne(int atcSeq);

   List<Map<String, Object>> queryList(Map<String, Object> condition);

   List<Map<String, Object>> queryActListForApp(Map<String, Object> condition);

   Map<String, Object> queryActByIdForApp(int atcSeq);
}
