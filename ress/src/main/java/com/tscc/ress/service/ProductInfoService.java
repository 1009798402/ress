package com.tscc.ress.service;

import com.tscc.ress.database.ProductInfo;
import com.tscc.ress.dto.CartDto;
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
    ProductInfo findOne(String productId);

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

    /**
     * 增加库存的方法
     *
     * @param cartDtoList  CartDto对象 包含要改变库存的商品以及它的数量
     */
    void increaseProductDescription(List<CartDto> cartDtoList);

    /**
     * 减少库存的方法
     *
     * @param cartDtoList  CartDto对象 包含要改变库存的商品以及它的数量
     */
    void decreaseProductDescription(List<CartDto> cartDtoList);

    /**
     * 商品上架
     *
     * @param productId 商品ID
     */
    void onSale(String productId);

    /**
     * 商品下架
     *
     * @param productId 商品ID
     */
    void offSale(String productId);

    /**
     * 删除商品
     *
     * @param productId 商品ID
     */
    void delete(String productId);
}
