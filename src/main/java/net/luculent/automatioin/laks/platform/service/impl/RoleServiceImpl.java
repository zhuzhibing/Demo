package net.luculent.automatioin.laks.platform.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.luculent.automatioin.laks.platform.dao.RoleDao;
import net.luculent.automatioin.laks.platform.service.RoleService;
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
public class RoleServiceImpl implements RoleService{

    @Autowired
    private RoleDao roleDao;


    @Override
    public int add(Map<String, Object> role) {

        return roleDao.add(role);
    }

    @Override
    public int modify(Map<String, Object> role) {

        return roleDao.modify(role);
    }

    @Override
    public int delete(int roleSeq) {

        return roleDao.delete(roleSeq);
    }

    @Override
    public Map<String, Object> queryOne(int roleSeq) {

        return roleDao.queryOne(roleSeq);
    }

    @Override
    public PageInfo queryPageInfo(Map<String, Object> condition, int pageNo, int pageSize) {
        PageInfo page = PageHelper.startPage(pageNo, pageSize)
                .doSelectPageInfo(() -> roleDao.queryList(condition));
        return page;
    }
}
