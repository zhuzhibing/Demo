package net.luculent.automatioin.laks.platform.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: zhuzb
 * @Description:
 * @Date Create In 11:42 2018/1/5
 * @Modified By:
 */
@Data
public class UserEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private int userId;

    /**
     * 用户姓名
     */
    private String userName;

    /**
     * 性别
     */
    private String userGender;

    /**
     * 手机
     */
    private String userMobile;

    /**
     * 邮箱
     */
    private String userEmail;

    /**
     * 身份证号码
     */
    private String userIdcard;

    /**
     * 头像地址
     */
    private String userPhoto;

    public UserEntity() {
    }
}
