package com.tscc.ress.dao;

import com.tscc.ress.database.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 描述:商品表的Repository
 *
 * @author C
 * @date 15:51 2018/6/29/029
 */
public interface ProductInfoRepository extends JpaRepository<ProductInfo,String>{

    /**
     * 根据商品状态查询商品
     *
     * @param productStatus 商品状态,0正常1下架
     * @return List<ProductInfo>
     */
    List<ProductInfo>  findByProductStatus(Integer productStatus);

    /**
     * 等于findById
     *
     * @param primaryId 主键Id
     * @return OrderDetail
     */
    ProductInfo findByProductId(String primaryId);
}
