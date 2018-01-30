package net.luculent.automatioin.laks.services.article.service;


import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface IArticleService {

    /**
     * 文章新增
     *
     * @param article
     * @return
     */
    int save(Map<String, Object> article);

    /**
     * 文章删除
     *
     * @param atcSeq
     * @return
     */
    int delete(int atcSeq);

    /**
     * 根据主键查询文章信息
     *
     * @param atcSeq
     * @return
     */
    Map<String, Object> queryOne(int atcSeq);


    /**
     * 根据类型查询文章
     *
     * @param atcType
     * @return
     */
    List<Map<String, Object>> queryList(String atcType);

    /**
     * 文章分页查询
     *
     * @param condition
     * @param pageNo
     * @param pageSize
     * @return
     */
    PageInfo queryArticlePageInfo(Map<String, Object> condition, int pageNo, int pageSize);

    /**
     * 手机app文章分页查询
     *
     * @param condition
     * @param pageNo
     * @param pageSize
     * @return
     */
    PageInfo queryActListForApp(Map<String, Object> condition, int pageNo, int pageSize);

    /**
     * 手机app查询新闻详情
     *
     * @param atcSeq
     * @return
     */
    Map<String, Object> queryActByIdForApp(int atcSeq);
}