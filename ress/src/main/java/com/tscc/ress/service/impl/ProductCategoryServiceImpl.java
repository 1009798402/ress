package com.tscc.ress.service.impl;

import com.tscc.ress.dao.ProductCategoryRepository;
import com.tscc.ress.dao.ProductInfoRepository;
import com.tscc.ress.database.ProductCategory;
import com.tscc.ress.database.ProductInfo;
import com.tscc.ress.enums.ResultEnum;
import com.tscc.ress.exception.SellException;
import com.tscc.ress.service.ProductCategoryService;
import com.tscc.ress.service.ProductInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ProductCategoryService的实现类
 *
 * @author keriezhang
 * @date 2016/10/31
 */
@Slf4j
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryRepository repository;
    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Override
    public ProductCategory findOne(Integer categoryId) {
        return repository.findByCategoryId(categoryId);
    }

    @Override
    public List<ProductCategory> findAll() {
        return repository.findAll();
    }

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> types) {
        return repository.findByCategoryTypeIn(types);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProductCategory save(ProductCategory productCategory) {
        return repository.save(productCategory);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer categoryId) {

        ProductCategory category = repository.findByCategoryId(categoryId);
        Integer type = category.getCategoryType();
        List<ProductInfo> productInfoList = productInfoRepository.findByCategoryType(type);
        if (productInfoList.size() == 0 ){
            repository.deleteById(categoryId);
        } else {
            log.error("【删除类目】 该类目下还有商品 无法删除, categoryId = {}",categoryId);
            throw new SellException(ResultEnum.CATEGORY_CANNOT_DELETE);
        }
    }

    @Override
    public Boolean isExistCategoryType(Integer categoryType,String categoryName){

        Boolean flag = false;

        List<ProductCategory> productCategoryList = repository.findAll();

        for (ProductCategory productCategory : productCategoryList) {
            if (productCategory.getCategoryType().equals(categoryType) || productCategory.getCategoryName().equals(categoryName)){
                flag = true;
            }
        }
        return flag;
    }
}
