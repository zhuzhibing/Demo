package net.luculent.automatioin.laks.platform.service;

import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * @Author: zhuzb
 * @Description:
 * @Date Create In 15:52 2018/3/15
 * @Modified By:
 */
public interface PowerService {

    int add(Map<String, Object> power);

    int modify(Map<String, Object> power);

    int delete(int powerSeq);

    Map<String, Object> queryOne(int powerSeq);

    PageInfo queryPageInfo(Map<String, Object> condition, int pageNo, int pageSize);

}
