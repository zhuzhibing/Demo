package net.luculent.automatioin.laks.platform.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @Author: zhuzb
 * @Description:
 * @Date Create In 15:31 2018/3/15
 * @Modified By:
 */
@Mapper
public interface RoleDao {

    int add(Map<String, Object> role);

    int modify(Map<String, Object> role);

    int delete(int roleSeq);

    Map<String, Object> queryOne(int roleSeq);

    List<Map<String, Object>> queryList(Map<String, Object> condition);

}
