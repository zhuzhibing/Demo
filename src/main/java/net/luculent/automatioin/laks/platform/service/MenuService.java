package net.luculent.automatioin.laks.platform.service;

import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * @Author: zhuzb
 * @Description:
 * @Date Create In 16:38 2018/3/7
 * @Modified By:
 */
public interface MenuService {

    List<Map<String,String>> getMenuByUserId(Integer userId);

    /**
     * 菜单新增
     * @param menu
     * @return
     */
    int add(Map<String, Object> menu);

    /**
     * 菜单修改
     * @param menu
     * @return
     */
    int modify(Map<String, Object> menu);

    /**
     * 菜单删除
     *
     * @param menuSeq
     * @return
     */
    int delete(Integer menuSeq);

    /**
     * 根据主键查询菜单信息
     *
     * @param menuSeq
     * @return
     */
    Map<String, Object> queryOne(Integer menuSeq);

    /**
     * 菜单分页查询
     *
     * @param condition
     * @param pageNo
     * @param pageSize
     * @return
     */
    PageInfo queryMenuPageInfo(Map<String, Object> condition, int pageNo, int pageSize);

}
