package net.luculent.automatioin.laks.services.article.service.impl;

import net.luculent.automatioin.laks.services.article.dao.ArticleTypeDao;
import net.luculent.automatioin.laks.services.article.service.IArticleTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Author: zhuzb
 * @Description:
 * @Date Create In 19:13 2018/1/2
 * @Modified By:
 */
@Service
public class ArticleTypeServiceImpl implements IArticleTypeService {

    @Resource
    private ArticleTypeDao articleTypeDao;


    @Override
    public List<Map<String, Object>> queryByParentId(String parentId) {

        List<Map<String, Object>> map= articleTypeDao.queryByParentId(parentId);

        return map;
    }
}
