package net.luculent.automatioin.laks.services.article.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.Map;

/**
 * @Author: zhuzb
 * @Description:
 * @Date Create In 18:55 2018/1/2
 * @Modified By:
 */
@Mapper
public interface ArticleTypeDao {

    /**
     * 根据父类型查询子类型
     * @param parentId
     * @return
     */
    List<Map<String, Object>> queryByParentId(@Param(value="parentId") String parentId);
}
