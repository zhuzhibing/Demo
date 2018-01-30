package net.luculent.automatioin.laks.platform.service;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;

/**
 * Description 短信发送服务
 * Author chenmingming
 * CreateTime 2017-12-06 9:14
 **/

public interface SmsSendService {
    public SendSmsResponse sendSms(String phoneNums, String signName, String templateCode, String template);

    public String sendVerifySms(String phoneNum);
}
