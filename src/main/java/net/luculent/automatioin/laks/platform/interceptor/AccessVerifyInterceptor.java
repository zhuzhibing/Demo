package net.luculent.automatioin.laks.platform.interceptor;

import net.luculent.automatioin.laks.platform.BasicHttpHandler;
import net.luculent.automatioin.laks.platform.aspact.RequestUserContextHolder;
import net.luculent.automatioin.laks.platform.domain.Result;
import net.luculent.automatioin.laks.platform.service.RoleAccessService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: zhuzb
 * @Description:
 * @Date Create In 14:08 2018/2/28
 * @Modified By:
 */
@Component
public class AccessVerifyInterceptor extends BasicHttpHandler implements HandlerInterceptor {


    @Resource
    private RoleAccessService roleAccessService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 获取url
        String url = request.getRequestURI();
        url = url.substring(0, url.lastIndexOf("/"));

        String urlMethod = request.getMethod();
//        String regex = "/login";
//        Pattern p = Pattern.compile(regex);
//        Matcher m = p.matcher(url);
//        if (m.find()) {
//            return true;
//        }

        // 获取当前访问用户信息
        Integer userId = RequestUserContextHolder.getRequestLoginUser().getUserId();
        // 获取访问权限
        // 判断权限是否可以访问当前url
        boolean b = roleAccessService.isAllowAccess(userId, url,urlMethod);
        if(!b){
            this.sendJsonMessage(response, new Result(21100, "access denied"));
        }
        return b;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
