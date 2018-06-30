package com.tscc.ress.controller;

import com.tscc.ress.database.ProductCategory;
import com.tscc.ress.database.ProductInfo;
import com.tscc.ress.service.ProductCategoryService;
import com.tscc.ress.service.ProductInfoService;
import com.tscc.ress.utils.SellResult;
import com.tscc.ress.vo.ProductInfoVo;
import com.tscc.ress.vo.ProductVo;
import com.tscc.ress.vo.ResultVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * 描述:
 *
 * @author C
 * @date 20:03 2018/6/29/029
 */
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {
    @Autowired
    private ProductInfoService productInfoService;
    @Autowired
    private ProductCategoryService categoryService;

    @RequestMapping("/list")
    public ResultVo showProductList() {
        //1.查询所有上架中的商品  数据库查询
        List<ProductInfo> productInfos = productInfoService.findOnLine();

        //2.查询出上架商品所对应的类型
        //lambda写法
        List<Integer> types = productInfos.stream().map(e -> e.getCategoryType()).collect(Collectors.toList());

        //3.根据类型查找对应的类目  数据库查询
        List<ProductCategory> categorys = categoryService.findByCategoryTypeIn(types);

        //4.数据拼装
        List<ProductVo> data = new ArrayList<>();

        //5.循环生成每个productVo
        for (ProductCategory category : categorys) {
            ProductVo productVo = new ProductVo();
            //5.1填充productVo中的字段
            productVo.setCategoryName(category.getCategoryName());
            productVo.setCategoryType(category.getCategoryType());

            List<ProductInfoVo> list = new ArrayList<>();
            for (ProductInfo productInfo : productInfos) {
                if (productInfo.getCategoryType().equals(category.getCategoryType())) {
                    ProductInfoVo productInfoVo = new ProductInfoVo();
                    //对象copy
                    BeanUtils.copyProperties(productInfo, productInfoVo);
                    list.add(productInfoVo);
                }
            }
            productVo.setProductInfoVos(list);
            //5.2添加resultVo中的返回数据字段
            data.add(productVo);
        }

        //6.返回结果对象
        return SellResult.success(data);
    }
}
