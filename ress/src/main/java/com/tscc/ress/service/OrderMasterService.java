package com.tscc.ress.service;

import com.tscc.ress.dto.OrderDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 描述:订单相关操作Service层
 *
 * @author C
 * Date: 2018-06-30
 * Time: 10:51
 */
public interface OrderMasterService {

    /**
     * 新增一个订单的方法
     *
     * @param orderDto 用来传输OrderMaster的对象
     * @return OrderDto 新增的那个dto对象
     */
    OrderDto createOrder(OrderDto orderDto);

    /**
     * 查询一个订单的方法
     *
     * @param orderId 要查询的订单id
     * @return OrderDto 查询到的那个dto对象
     */
    OrderDto findOne(String orderId);

    /**
     * 查询一个订单列表的方法
     *
     * @param buyerOpenid 买家的微信Openid
     * @param pageable 分页对象
     * @return Page<OrderDto> 分页结果
     */
    Page<OrderDto> findAll(String buyerOpenid, Pageable pageable);

    /**
     * 取消订单的方法
     *
     * @param orderDto 要取消的那个订单Dto
     * @return OrderDto 取消完成的那个订单Dto
     */
    OrderDto cancel(OrderDto orderDto);

    /**
     * 完结订单的方法
     *
     * @param orderDto 要完结的那个订单Dto
     * @return OrderDto 完结后的那个订单Dto
     */
    OrderDto finish(OrderDto orderDto);

    /**
     * 支付订单的方法
     *
     * @param orderDto 要支付的那个订单Dto
     * @return OrderDto 支付完成的那个订单Dto
     */
    OrderDto paid(OrderDto orderDto);

    /**
     * 查询所有订单列表的方法
     *
     * @param pageable 分页对象
     * @return Page<OrderDto> 分页结果
     */
    Page<OrderDto> findAll(Pageable pageable);
}
