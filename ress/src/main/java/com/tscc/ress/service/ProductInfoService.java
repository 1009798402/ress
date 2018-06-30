package com.tscc.ress.service;

import com.tscc.ress.database.ProductCategory;
import com.tscc.ress.database.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 描述:商品服务的接口
 *
 * @author C
 * @date 17:05 2018/6/29/029
 */
public interface ProductInfoService {
    /**
     * 查询一个商品
     *
     * @param productId 商品id
     * @return ProductInfo
     */
    ProductInfo getOne(String productId);

    /**
     * 查询所有商品
     *
     * @param pageable 分页
     * @return  Page<ProductInfo> 分页后的商品
     */
    Page<ProductInfo> findAll(Pageable pageable);

    /**
     * 根据商品状态查询
     *
     *
     * @return List<ProductInfo> 这个状态的的商品
     */
    List<ProductInfo> findOnLine();

    /**
     * 保存或者修改商品
     *
     * @param productInfo 要保存或修改的商品
     * @return ProductInfo 保存或者修改后的商品
     */
    ProductInfo save(ProductInfo productInfo);
}
