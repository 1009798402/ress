package com.tscc.ress.service.impl;

import com.tscc.ress.database.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * 描述:
 *
 * @author C
 * @date 15:19 2018/6/29/029
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryServiceImplTest {
    @Autowired
    private ProductCategoryServiceImpl service;
    @Test
    public void findOnd() {
        ProductCategory category = service.findOne(1);
        Assert.assertNotNull(category);
    }

    @Test
    public void findAll() {
        List<ProductCategory> all = service.findAll();
        Assert.assertNotEquals(0,all.size());
    }

    @Test
    public void findByCategoryTypeIn() {
        List<Integer> types = Arrays.asList(1,2,3);
        List<ProductCategory> productCategories = service.findByCategoryTypeIn(types);
        Assert.assertNotEquals(0,productCategories.size());
    }

    @Test
    public void save() {
        ProductCategory productCategory = new ProductCategory();
        ProductCategory save = service.save(productCategory);
        Assert.assertNotNull(save);
    }

    @Test
    public void del() {
        service.delete(13);
    }
}