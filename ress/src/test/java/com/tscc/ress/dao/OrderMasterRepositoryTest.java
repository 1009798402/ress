package com.tscc.ress.dao;

import com.tscc.ress.database.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.*;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

/**
 * 描述:OrderMasterRepository接口的测试
 *
 * @author C
 * Date: 2018-06-30
 * Time: 10:17
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {

    @Autowired
    private OrderMasterRepository repository;

    private final String OPENID = "1a456w";

    @Test
    public void testSave(){
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setBuyerOpenid("1");
        orderMaster.setOrderId("2");
        orderMaster.setBuyerName("张三");
        orderMaster.setBuyerPhone("12306");
        orderMaster.setBuyerAddress("老家");
        orderMaster.setBuyerOpenid(OPENID);
        orderMaster.setOrderAmount(new BigDecimal(22));
        OrderMaster save = repository.save(orderMaster);

        Assert.assertNotNull(save);
    }

    @Test
    public void testFindByBuyerOpenid(){
        PageRequest page = new PageRequest(0,5);

        Page<OrderMaster> masters = repository.findByBuyerOpenid(OPENID, page);
        Assert.assertNotEquals(0,masters.getTotalElements());
    }
}