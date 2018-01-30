package net.luculent.automatioin.laks.platform.service;

import com.alipay.api.AlipayApiException;

import java.util.Date;
import java.util.Map;

/**
 * Description 支付宝调用接口
 * Author chenmingming
 * CreateTime 2018-01-10 9:08
 **/

public interface AlipayService {
    String generateOrders(int userId, Long time, String money, String usage, String payType) throws AlipayApiException;

    boolean receiveNotify(Map<String, String> params) throws AlipayApiException;
}
