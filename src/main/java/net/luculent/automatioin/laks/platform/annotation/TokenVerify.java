package net.luculent.automatioin.laks.platform.annotation;

import java.lang.annotation.*;

/**
 * @Author: zhuzb
 * @Description: 自定义注解 拦截Controller
 * @Date Create In 14:04 2018/3/20
 * @Modified By:
 */

@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TokenVerify {

    String module() default "";  //模块名称

    String methods() default "";  //新增用户

    String description() default "";  //


}
