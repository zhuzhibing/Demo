package net.luculent.automatioin.laks.platform.domain;

import lombok.Data;
import net.luculent.automatioin.laks.platform.enums.SysExpEnum;

import java.io.Serializable;

/**
 * Description http请求返回最外层对象
 * Author chenmingming
 * CreateTime 2017-11-03 15:04
 **/
@Data
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer code;  //返回码

    private String message;  //提示信息

    private String token;  //token信息

    private T data;  //具体内容：泛型

    public Result() {
    }

    public Result(SysExpEnum sysEnum) {
        this.code = sysEnum.getCode();
        this.message = sysEnum.getMessage();
    }

    public Result(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

}