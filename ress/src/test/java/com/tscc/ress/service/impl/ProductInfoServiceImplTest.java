package com.tscc.ress.service.impl;

import com.tscc.ress.service.ProductInfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 描述:
 *
 * @author C
 * Date: 2018-07-03
 * Time: 11:32
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoServiceImplTest {
    @Autowired
    private ProductInfoService productInfoService;

    @Test
    public void testOnSale() {
        productInfoService.offSale("123456");
    }


}