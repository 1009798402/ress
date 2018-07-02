package com.tscc.ress.service.impl;

import com.tscc.ress.dto.OrderDto;
import com.tscc.ress.service.OrderMasterService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 描述:
 *
 * @author C
 * Date: 2018-07-01
 * Time: 21:53
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PayServiceImplTest {
    @Autowired
    private PayServiceImpl service;
    @Autowired
    private OrderMasterService masterServer;
    @Test
    public void create() {
        OrderDto orderDto = masterServer.findOne("1530340816247703217");
        service.create(orderDto);
    }
}