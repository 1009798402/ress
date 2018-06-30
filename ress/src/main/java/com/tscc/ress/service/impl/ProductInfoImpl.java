package com.tscc.ress.service.impl;

import com.tscc.ress.dao.ProductInfoRepository;
import com.tscc.ress.database.ProductInfo;
import com.tscc.ress.enums.ProductStatusEnum;
import com.tscc.ress.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 描述:
 *
 * @author C
 * @date 17:29 2018/6/29/029
 */
@Service
public class ProductInfoImpl implements ProductInfoService{
    @Autowired
    private ProductInfoRepository repository;

    @Override
    public ProductInfo getOne(String productId) {
        return repository.getOne(productId);
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
}
