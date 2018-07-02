package com.tscc.ress.dao;

import com.tscc.ress.database.OrderDetail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;


/**
 * 描述:OrderDetailRepository接口的测试
 *
 * @author C
 * Date: 2018-06-30
 * Time: 9:50
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailRepositoryTest {
    @Autowired
    private OrderDetailRepository repository;

    @Test
    public void testSave(){
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("123456111");
        orderDetail.setOrderId("12222");
        orderDetail.setProductId("12306333");
        orderDetail.setProductName("测试数据");
        orderDetail.setProductIcon("a.jpg");
        orderDetail.setProductPrice(new BigDecimal(2.2));
        orderDetail.setProductQuantity(2);

        OrderDetail save = repository.save(orderDetail);
        Assert.assertNotNull(save);
    }
    @Test
    public void testFindByOrOrderId(){
        List<OrderDetail> byOrOrderId = repository.findByOrderId("1530340816247703217");
        Assert.assertNotEquals(0,byOrOrderId.size());
    }
    @Test
    public void testFindByDetailId(){
        OrderDetail detail = repository.findByDetailId("123456111");
        Assert.assertNotNull(detail);
    }
}