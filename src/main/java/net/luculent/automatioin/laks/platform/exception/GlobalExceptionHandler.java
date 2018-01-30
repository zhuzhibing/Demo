package net.luculent.automatioin.laks.platform.exception;

import net.luculent.automatioin.laks.platform.domain.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: zhuzb
 * @Description: 全局异常捕获处理
 * @Date Create In 13:48 2018/1/5
 * @Modified By:
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result jsonErrorHandler(Exception e) {

        logger.error("GlobalExceptionHandler ==== >> ", e);

        Result result = new Result(1, e.getMessage());

        // 判断异常类型
//        if (e instanceof MissingServletRequestParameterException) {
//
//
//        }

        return result;
    }

}
