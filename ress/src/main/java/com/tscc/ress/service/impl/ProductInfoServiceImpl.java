package com.tscc.ress.service.impl;

import com.tscc.ress.dao.ProductInfoRepository;
import com.tscc.ress.database.ProductInfo;
import com.tscc.ress.dto.CartDto;
import com.tscc.ress.enums.ProductStatusEnum;
import com.tscc.ress.enums.ResultEnum;
import com.tscc.ress.exception.SellException;
import com.tscc.ress.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 描述:ProductInfoService的实现类
 *
 * @author C
 * @date 17:29 2018/6/29/029
 */
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
    public ProductInfo save(ProductInfo productInfo) {
        return repository.save(productInfo);
    }

    @Override
    public void increaseProductDescription(List<CartDto> cartDtoList) {
        for (CartDto cartDto : cartDtoList) {
            ProductInfo productInfo = repository.findByProductId(cartDto.getProductId());
            if (productInfo == null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer stock = productInfo.getProductStock() + cartDto.getProductQuantity();
            productInfo.setProductStock(stock);
            repository.save(productInfo);
        }
    }

    @Override
    public void decreaseProductDescription(List<CartDto> cartDtoList) {
        for (CartDto cartDto : cartDtoList) {
            ProductInfo productInfo = repository.findByProductId(cartDto.getProductId());
            if (productInfo == null){
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


}
