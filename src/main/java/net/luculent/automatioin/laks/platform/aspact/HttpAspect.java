package net.luculent.automatioin.laks.platform.aspact;

import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * @Author: zhuzb
 * @Description:
 * @Date Create In 13:45 2018/1/30
 * @Modified By:
 */
//@Aspect
//@Component
public class HttpAspect {

    private static final Logger logger = LoggerFactory.getLogger(HttpAspect.class);

    @Pointcut("execution(public * net.luculent.automatioin.laks.platform.controller.LoginController.login(..))")
    public void http() {
    }

    /**
     * 请求调用前 打印请求参数信息
     */
    @Before("http()")
    public void doBefore() {
        if (logger.isInfoEnabled()) {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();

            String requestURL = request.getRequestURI();

            Enumeration enu = request.getParameterNames();
            String logInfo = "";
            while (enu.hasMoreElements()) {
                String paraName = (String) enu.nextElement();
                logInfo += "|" + paraName + "=" + request.getParameter(paraName);
            }

            logger.info("requestURL >>'" + requestURL + "', " + "requestParam >>" + logInfo);
        }

    }

    /**
     * 请求调用后
     */
    @After("http()")
    public void doAfter() {
    }

    /**
     * 返回结果显示
     *
     * @param object
     */
    @AfterReturning(returning = "object", pointcut = "http()")
    public void AfterReturning(Object object) {
        if (logger.isInfoEnabled()) {
            logger.info("[" + object + "]");
        }
    }
}
