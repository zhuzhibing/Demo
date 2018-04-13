package net.luculent.automatioin.laks.platform.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.luculent.automatioin.laks.platform.dao.MenuDao;
import net.luculent.automatioin.laks.platform.service.MenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Author: zhuzb
 * @Description:
 * @Date Create In 16:39 2018/3/7
 * @Modified By:
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Resource
    private MenuDao menuDao;

    @Override
    public List<Map<String, String>> getMenuByUserId(Integer userId) {

        return menuDao.getMenuByUserId(userId);
    }

    @Override
    public int add(Map<String, Object> menu) {

        return menuDao.add(menu);
    }


    @Override
    public int modify(Map<String, Object> menu) {

        return  menuDao.modify(menu);
    }

    @Override
    public int delete(Integer menuSeq) {

        return menuDao.delete(menuSeq);
    }

    @Override
    public Map<String, Object> queryOne(Integer menuSeq) {

        return menuDao.queryOne(menuSeq);
    }

    @Override
    public PageInfo queryMenuPageInfo(Map<String, Object> condition, int pageNo, int pageSize) {

        PageInfo page = PageHelper.startPage(pageNo, pageSize)
                .doSelectPageInfo(() -> menuDao.queryList(condition));
        return page;
    }
}
