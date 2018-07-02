package com.tscc.ress.service.impl;

import com.tscc.ress.database.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;


/**
 * 描述:
 *
 * @author C
 * @date 17:42 2018/6/29/029
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoImplTest {
    @Autowired
    private ProductInfoServiceImpl service;

    @Test
    public void findOne() {
        ProductInfo one = service.findOne("2");
        Assert.assertEquals("2",one.getProductId());
    }

    @Test
    public void findAll() {
        PageRequest request = new PageRequest(0,2);
        Page<ProductInfo> page = service.findAll(request);
        Assert.assertNotEquals(0,page.getTotalElements());
    }

    @Test
    public void findOnLine() {
        List<ProductInfo> onLine = service.findOnLine();
        Assert.assertNotEquals(0,onLine.size());
    }

    @Test
    public void save() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setCategoryType(2);
        productInfo.setProductDescription("好吃不如饺子");
        productInfo.setProductId("3");
        productInfo.setProductName("饺子");
        productInfo.setProductPrice(new BigDecimal(13.2));
        productInfo.setProductStatus(0);
        productInfo.setProductStock(22);
        ProductInfo save = service.save(productInfo);
        Assert.assertNotNull(save);
    }
}
