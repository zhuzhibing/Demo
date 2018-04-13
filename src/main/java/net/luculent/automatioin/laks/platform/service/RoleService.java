package net.luculent.automatioin.laks.platform.service;

import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * @Author: zhuzb
 * @Description:
 * @Date Create In 15:51 2018/3/15
 * @Modified By:
 */
public interface RoleService {

    int add(Map<String, Object> role);

    int modify(Map<String, Object> role);

    int delete(int roleSeq);

    Map<String, Object> queryOne(int roleSeq);

    PageInfo queryPageInfo(Map<String, Object> condition, int pageNo, int pageSize);

}
