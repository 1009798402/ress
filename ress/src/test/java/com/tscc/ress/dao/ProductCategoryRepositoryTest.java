package com.tscc.ress.dao;

import com.tscc.ress.database.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;


/**
 * Demo class
 *
 * @author keriezhang
 * @date 2016/10/31
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {
    @Autowired
    private ProductCategoryRepository repository;
    @Test
    public void test(){
        List<ProductCategory> all = repository.findAll();
        for (ProductCategory productCategory : all) {
            System.out.println(productCategory.toString());
        }
    }

    @Test
    @Transactional
    public void test2(){
        ProductCategory category = new ProductCategory();
        category.setCategoryType(3);
        category.setCategoryName("嘻嘻");
        ProductCategory p = repository.save(category);
        Assert.assertNotNull(p);
    }
    @Test
    public void findByCategoryTypeTest(){
        List<Integer> types = Arrays.asList(1,2,3);
        List<ProductCategory> list = repository.findByCategoryTypeIn(types);
        Assert.assertNotEquals(0,list.size());
    }
}