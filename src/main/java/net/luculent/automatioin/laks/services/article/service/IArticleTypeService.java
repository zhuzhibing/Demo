package net.luculent.automatioin.laks.services.article.service;

import java.util.List;
import java.util.Map;

/**
 * @Author: zhuzb
 * @Description:
 * @Date Create In 19:11 2018/1/2
 * @Modified By:
 */
public interface IArticleTypeService {
    /**
     * 根据父类型查询子类型
     * @param parentId
     * @return
     */
    List<Map<String, Object>> queryByParentId(String parentId);
}
