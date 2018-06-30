package com.tscc.ress.dao;

import com.tscc.ress.database.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 商品类目表的Repository
 *
 * @author c
 * @date 2016/10/31
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategory,Integer> {

    /**
     * 根据1个或者多个type查询类目
     *
     * @param types 一个或多个类目编号
     * @return List<ProductCategory>
     */
    List<ProductCategory> findByCategoryTypeIn(List<Integer> types);

    /**
     * 等于findById
     *
     * @param primaryId 主键Id
     * @return OrderDetail
     */
    ProductCategory findByCategoryId(Integer primaryId);
}
