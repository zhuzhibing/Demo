package net.luculent.automatioin.laks.services.article.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.luculent.automatioin.laks.services.article.dao.ArticleDao;
import net.luculent.automatioin.laks.services.article.service.IArticleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceimpl implements IArticleService {

    @Resource
    private ArticleDao articleDao;

    @Override
    public int save(Map<String, Object> article) {

        int b = 0;
        if ((Integer) article.get("atcSeq") > 0) {

            article.put("lstusrId", 1);
            article.put("lstusrDtm", new Timestamp(System.currentTimeMillis()));

            b = articleDao.modify(article);

        } else {

            article.put("fstusrId", 1);
            article.put("fstusrDtm", new Timestamp(System.currentTimeMillis()));
            article.put("orgSeq", 1);

            b = articleDao.add(article);
        }

        return b;

    }


    @Override
    public int delete(int atcSeq) {

        return articleDao.delete(atcSeq);
    }

    @Override
    public Map<String, Object> queryOne(int atcSeq) {

        return articleDao.queryOne(atcSeq);
    }

    @Override
    public List<Map<String, Object>> queryList(String atcType) {

        Map<String, Object> condition = new HashMap<>();
        condition.put("atcType", atcType);

        return articleDao.queryList(condition);
    }

    @Override
    public PageInfo queryArticlePageInfo(Map<String, Object> condition, int pageNo, int pageSize) {

        PageInfo page = PageHelper.startPage(pageNo, pageSize).doSelectPageInfo(() -> articleDao.queryList(condition));

        return page;
    }

    /**
     * 手机app文章分页查询
     *
     * @param condition
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageInfo queryActListForApp(Map<String, Object> condition, int pageNo, int pageSize) {
        PageInfo page = PageHelper.startPage(pageNo, pageSize).doSelectPageInfo(() -> articleDao.queryActListForApp(condition));

        return page;
    }

    /**
     * 手机app查询新闻详情
     *
     * @param atcSeq
     * @return
     */
    public Map<String, Object> queryActByIdForApp(int atcSeq){
        return articleDao.queryActByIdForApp(atcSeq);
    }
}