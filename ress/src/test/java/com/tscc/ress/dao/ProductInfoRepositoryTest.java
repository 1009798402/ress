package com.tscc.ress.dao;

import com.tscc.ress.database.ProductInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * 描述:
 *
 * @author C
 * @date 15:53 2018/6/29/029
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoRepositoryTest {
    @Autowired
    private ProductInfoRepository repository;

    @Test
    public void testProductInfoRepository(){
        List<ProductInfo> all = repository.findAll();
        for (ProductInfo productInfo : all) {
            System.out.println(productInfo);
        }
    }
    @Test
    public void testFindByProductStatus(){
        List<ProductInfo> byProductStatus = repository.findByProductStatus(1);
        for (ProductInfo productStatus : byProductStatus) {
            System.out.println(productStatus);
        }
    }
}
