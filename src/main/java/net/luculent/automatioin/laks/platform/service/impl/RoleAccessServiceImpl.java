package net.luculent.automatioin.laks.platform.service.impl;

import net.luculent.automatioin.laks.platform.dao.RoleAccessDao;
import net.luculent.automatioin.laks.platform.service.RoleAccessService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zhuzb
 * @Description:
 * @Date Create In 15:28 2018/2/28
 * @Modified By:
 */
@Service
public class RoleAccessServiceImpl implements RoleAccessService {

    @Resource
    private RoleAccessDao roleAccessDao;

    @Override
    public void allotUserRole(Integer userId, Integer roleId) {

        roleAccessDao.allotUserRole(userId, roleId);

    }

    @Override
    public void deleteUserRole(Integer userId, Integer roleId) {

    }

    @Override
    public void allotRolePower(Integer roleId, Integer powerId) {

    }

    @Override
    public void deleteRolePower(Integer roleId, Integer powerId) {

    }

    @Override
    public void allotPowerMenu(Integer powerId, Integer menuId) {

    }

    @Override
    public void deletePowerMenu(Integer powerId, Integer menuId) {

    }

    @Override
    public void allotPowerAccess(Integer powerId, Integer accessId) {

    }

    @Override
    public void deletePowerAccess(Integer powerId, Integer accessId) {

    }

    @Override
    public boolean isAllowAccess(Integer userId, String url, String urlMethod) {

        int count = roleAccessDao.getUserAccess(userId, url, urlMethod);

        if (count > 0) {
            return true;
        }
        return false;

    }

    @Override
    public Map<String, Object> getUserAccessUrl(Integer userId) {

        return roleAccessDao.getUserAccessUrl(userId);
    }

    @Override
    @Transactional
    public int addAccessUrl(String url, String method) {

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("accessName", "");
        map.put("accessUrl", url);
        map.put("urlMethod", method);
        map.put("description", "");

        map.put("fstusrId", 10000);
        map.put("fstusrDtm", LocalDateTime.now());

        roleAccessDao.addAccessUrl(map);

        // roleAccessDao.deletePowerAccessByPowerId(9999);
        roleAccessDao.allotPowerAccess(9999, (Integer) map.get("accessSeq"));

        return 0;
    }

    @Override
    public void clearAccessUrl() {

        roleAccessDao.clearAccessUrl();
    }
}
