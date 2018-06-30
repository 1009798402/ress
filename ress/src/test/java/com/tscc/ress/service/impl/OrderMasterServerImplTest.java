package com.tscc.ress.service.impl;

import com.tscc.ress.database.OrderDetail;
import com.tscc.ress.dto.OrderDto;
import com.tscc.ress.enums.OrderStatusEnum;
import com.tscc.ress.enums.PayStatusEnum;
import com.tscc.ress.enums.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.criterion.Order;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * 描述:OrderMasterServerImpl的测试类
 *
 * @author C
 * Date: 2018-06-30
 * Time: 13:24
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderMasterServerImplTest {
    @Autowired
    private OrderMasterServerImpl orderMasterServer;

    private final String BUYER_OPENID = "1qq2r3";

    @Test
    public void testCreateOrder() {
        OrderDto orderDto = new OrderDto();
        orderDto.setBuyerAddress("电大东门");
        orderDto.setBuyerName("阿陶");
        orderDto.setBuyerOpenid(BUYER_OPENID);
        orderDto.setBuyerPhone("13600002748");
        //购物车
        List<OrderDetail> orderDetailList = new ArrayList<>();
        OrderDetail o1 = new OrderDetail();
        o1.setProductId("123456");
        o1.setProductQuantity(11);
        orderDetailList.add(o1);

        OrderDetail o2 = new OrderDetail();
        o2.setProductId("123457");
        o2.setProductQuantity(3);
        orderDetailList.add(o2);
        orderDto.setOrderDetails(orderDetailList);
        OrderDto result = orderMasterServer.createOrder(orderDto);
        log.info("【创建订单】 result = {}", result);

        Assert.assertNotNull(result);
    }

    @Test
    public void testFindOne() {
        OrderDto result = orderMasterServer.findOne("1530340816247703217");
        log.info("【查找单个订单】 result = {}", result);

        Assert.assertNotNull(result);
    }

    @Test
    public void testFindAll() {
        PageRequest page = new PageRequest(0,5);
        Page<OrderDto> orderDtos = orderMasterServer.findAll(BUYER_OPENID, page);
        log.info("【查到所有订单并且分页】 result = {}",orderDtos);

        Assert.assertNotEquals(0,orderDtos.getTotalElements());
    }

    @Test
    public void testCancel () {
        OrderDto orderDto = orderMasterServer.findOne("1530340816247703217");
        OrderDto cancel = orderMasterServer.cancel(orderDto);
        Assert.assertEquals(OrderStatusEnum.CANCEL.getCode(),cancel.getOrderStatus());
    }
    @Test
    public void testFinish () {
        OrderDto orderDto = orderMasterServer.findOne("1530340816247703217");
        OrderDto result = orderMasterServer.finish(orderDto);
        Assert.assertEquals(OrderStatusEnum.FINISHED.getCode(),result.getOrderStatus());
    }
    @Test
    public void testPaid () {
        OrderDto orderDto = orderMasterServer.findOne("1530340816247703217");
        OrderDto result = orderMasterServer.paid(orderDto);
        Assert.assertEquals(PayStatusEnum.SUCCESS.getCode(),result.getOrderStatus());
}
}