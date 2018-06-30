package com.tscc.ress.service;


import com.tscc.ress.database.ProductCategory;

import java.util.List;

/**
 * 描述:类目服务的接口
 * @author c
 * @date 2016/10/31
 */
public interface ProductCategoryService {
    /**
     * 查询一个类目
     *
     * @param categoryId 类目id
     * @return ProductCategory
     */
    ProductCategory findOne(Integer categoryId);

    /**
     * 查询所有类目
     *
     * @return List<ProductCategory>
     */
    List<ProductCategory> findAll();

    /**
     * 查询类目编号查询类目
     *
     * @param types 1个或多个类目标号
     * @return List<ProductCategory>
     */
    List<ProductCategory> findByCategoryTypeIn(List<Integer> types);

    /**
     * 保存或者更新类目
     *
     * @param productCategory 一个对象
     * @return ProductCategory
     */
    ProductCategory save(ProductCategory productCategory);

}
