package com.tscc.ress.service;

import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundResponse;
import com.tscc.ress.dto.OrderDto;

/**
 * 描述:支付的Service
 *
 * @author C
 * Date: 2018-06-30
 * Time: 17:45
 */
public interface PayService {

    /**
     * 微信支付创建订单
     *
     * @param orderDto 要创建支付订单的orderDto
     * @return 
     */
    PayResponse create(OrderDto orderDto);

    /**
     * 处理异步通知
     *
     * @param notifyData 微信异步通知的内容
     */
    PayResponse notify(String notifyData);

    /**
     * 微信退款
     *
     * @param orderDto 需要退款的orderDto对象
     */
    RefundResponse refund(OrderDto orderDto);
}
