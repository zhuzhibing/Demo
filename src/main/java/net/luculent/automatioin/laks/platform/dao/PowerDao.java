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
public interface PowerDao {

    int add(Map<String, Object> power);

    int modify(Map<String, Object> power);

    int delete(int powerSeq);

    Map<String, Object> queryOne(int powerSeq);

    List<Map<String, Object>> queryList(Map<String, Object> condition);

}
