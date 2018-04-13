package net.luculent.automatioin.laks.platform.controller;

import net.luculent.automatioin.laks.platform.domain.Result;
import net.luculent.automatioin.laks.platform.service.RoleAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: zhuzb
 * @Description:
 * @Date Create In 9:39 2018/3/1
 * @Modified By:
 */
@RestController
@RequestMapping(value = "/platform/url")
public class UrlController extends BaseController {

    @Autowired
    WebApplicationContext applicationContext;

    @Resource
    private RoleAccessService roleAccessService;

    @GetMapping("/all/public")
    public Result getAllUrl() {
        RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        //获取url与类和方法的对应信息
        Map<RequestMappingInfo, HandlerMethod> requestMap = mapping.getHandlerMethods();

        List<Map<String, String>> urlList = requestMap.keySet().stream().map(m -> {
            Map<String, String> target = new HashMap<>();
            //获取url的path Set集合，一个方法可能对应多个url;但是我们目前的系统只有一个
            Set<String> patterns = m.getPatternsCondition().getPatterns();
            if (patterns.iterator().hasNext()) {
                target.put("url", patterns.iterator().next());
            }
            //获取url的状态Set集合，同上
            Set<RequestMethod> requestMethod = m.getMethodsCondition().getMethods();
            if (requestMethod.iterator().hasNext()) {
                target.put("requestMethod", requestMethod.iterator().next().name());
            }

            return target;
        }).collect(Collectors.toList());

        Result result = this.success(urlList);

        return result;
    }


    @PutMapping("/update/public")
    @Transactional
    public Result updateAllUrl() {

        roleAccessService.clearAccessUrl();

        RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);

        //获取url与类和方法的对应信息
        Map<RequestMappingInfo, HandlerMethod> requestMap = mapping.getHandlerMethods();

        long count = requestMap.keySet().stream().map(m -> {

            String method = null;
            //获取url的状态Set集合
            Set<RequestMethod> requestMethod = m.getMethodsCondition().getMethods();
            if (requestMethod.iterator().hasNext()) {
                method = requestMethod.iterator().next().name();
            }

            //获取url的path Set集合，一个方法可能对应多个url;但是我们目前的系统只有一个
            Set<String> patterns = m.getPatternsCondition().getPatterns();
            if (patterns.iterator().hasNext()) {
                return roleAccessService.addAccessUrl(patterns.iterator().next(), method);
            }

            return 0;

        }).count();

        Result result = this.success(count);

        return result;

    }

}
