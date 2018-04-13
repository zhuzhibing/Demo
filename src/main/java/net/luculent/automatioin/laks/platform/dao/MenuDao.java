package net.luculent.automatioin.laks.platform.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @Author: zhuzb
 * @Description:
 * @Date Create In 16:33 2018/3/7
 * @Modified By:
 */
@Mapper
public interface MenuDao {

    List<Map<String,String>> getMenuByUserId(Integer userId);

    int add(Map<String, Object> menu);

    int modify(Map<String, Object> menu);

    int delete(int menuSeq);

    Map<String, Object> queryOne(int menuSeq);

    List<Map<String, Object>> queryList(Map<String, Object> condition);

}
