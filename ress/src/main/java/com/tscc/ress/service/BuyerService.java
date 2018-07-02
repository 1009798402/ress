package com.tscc.ress.service;

import com.tscc.ress.dto.OrderDto;

/**
 * 描述:提供买家会用到的功能
 *      检验买家的openid是否正确
 *
 * @author C
 * Date: 2018-07-01
 * Time: 10:27
 */
public interface BuyerService {

    /**
     * 查询一个订单的方法
     *
     * @param openId 买家的openId
     * @param orderId 要查询的订单id
     * @return OrderDto 查询到的那个dto对象
     */
    OrderDto findOneOrder(String openId,String orderId);

    /**
     * 取消订单的方法
     *
     * @param openid 买家的openId
     * @param orderId 订单号
     * @return Page<OrderDto> 分页结果
     */
    OrderDto cancelOrder(String openid,String orderId);

}
