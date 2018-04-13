package net.luculent.automatioin.laks.platform.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.luculent.automatioin.laks.platform.dao.PowerDao;
import net.luculent.automatioin.laks.platform.service.PowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Author: zhuzb
 * @Description:
 * @Date Create In 15:55 2018/3/15
 * @Modified By:
 */
@Service
public class PowerServiceImpl implements PowerService{

    @Autowired
    private PowerDao powerDao;

    @Override
    public int add(Map<String, Object> power) {

        return powerDao.add(power);
    }

    @Override
    public int modify(Map<String, Object> power) {

        return powerDao.modify(power);
    }

    @Override
    public int delete(int powerSeq) {

        return powerDao.delete(powerSeq);
    }

    @Override
    public Map<String, Object> queryOne(int powerSeq) {

        return powerDao.queryOne(powerSeq);
    }

    @Override
    public PageInfo queryPageInfo(Map<String, Object> condition, int pageNo, int pageSize) {
        PageInfo page = PageHelper.startPage(pageNo, pageSize)
                .doSelectPageInfo(() -> powerDao.queryList(condition));
        return page;
    }
}
