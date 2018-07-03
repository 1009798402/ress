package com.tscc.ress.service.impl;

import com.tscc.ress.dao.ProductInfoRepository;
import com.tscc.ress.database.ProductInfo;
import com.tscc.ress.dto.CartDto;
import com.tscc.ress.enums.ProductStatusEnum;
import com.tscc.ress.enums.ResultEnum;
import com.tscc.ress.exception.SellException;
import com.tscc.ress.service.ProductInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 描述:ProductInfoService的实现类
 *
 * @author C
 * @date 17:29 2018/6/29/029
 */
@Slf4j
@Service
public class ProductInfoServiceImpl implements ProductInfoService {

    @Autowired
    private ProductInfoRepository repository;

    @Override
    public ProductInfo findOne(String productId) {

        return repository.findByProductId(productId);
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public List<ProductInfo> findOnLine() {
        return repository.findByProductStatus(ProductStatusEnum.ONLINE.getCode());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProductInfo save(ProductInfo productInfo) {
        return repository.save(productInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void increaseProductDescription(List<CartDto> cartDtoList) {
        for (CartDto cartDto : cartDtoList) {
            ProductInfo productInfo = repository.findByProductId(cartDto.getProductId());
            if (productInfo == null) {
                log.error("【增加库存】 商品不存在 productId = {}",cartDto.getProductId());
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer stock = productInfo.getProductStock() + cartDto.getProductQuantity();
            productInfo.setProductStock(stock);
            repository.save(productInfo);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void decreaseProductDescription(List<CartDto> cartDtoList) {
        for (CartDto cartDto : cartDtoList) {
            ProductInfo productInfo = repository.findByProductId(cartDto.getProductId());
            if (productInfo == null) {
                log.error("【减少库存】 商品不存在 productId = {}",cartDto.getProductId());
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer stock = productInfo.getProductStock() - cartDto.getProductQuantity();
            if (stock < 0) {
                throw new SellException(ResultEnum.PRODUCT_INVENTORY_INVENTORY);
            }
            productInfo.setProductStock(stock);
            repository.save(productInfo);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void onSale(String productId) {

        ProductInfo productInfo = repository.findByProductId(productId);
        if (productInfo == null) {
            log.error("【商品上架】 商品不存在 productId = {}",productId);
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if (productInfo.getProductStatusEnum() == ProductStatusEnum.ONLINE){
            log.error("【商品上架】 商品是上架状态,不能再次上架 productId = {}",productId);
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        productInfo.setProductStatus(ProductStatusEnum.ONLINE.getCode());
        repository.save(productInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void offSale(String productId) {

        ProductInfo productInfo = repository.findByProductId(productId);
        if (productInfo == null) {
            log.error("【商品下架】 商品不存在 productId = {}",productId);
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        if (productInfo.getProductStatusEnum() == ProductStatusEnum.DOWN){
            log.error("【商品下架】 商品是下架状态,不能再次下架 productId = {}",productId);
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        repository.save(productInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String productId) {

        ProductInfo pro = repository.findByProductId(productId);
        if (pro == null){
            log.error("【删除商品】 商品Id不存在 productId = {}",productId);
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        repository.deleteById(productId);
    }


}
