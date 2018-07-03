package com.tscc.ress.controller;

import com.tscc.ress.database.ProductCategory;
import com.tscc.ress.database.ProductInfo;
import com.tscc.ress.enums.ProductStatusEnum;
import com.tscc.ress.enums.ResultEnum;
import com.tscc.ress.exception.SellException;
import com.tscc.ress.form.ProductForm;
import com.tscc.ress.service.ProductCategoryService;
import com.tscc.ress.service.ProductInfoService;
import com.tscc.ress.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 描述:商品操作Controller
 *
 * @author C
 * Date: 2018-07-02
 * Time: 23:02
 */
@Slf4j
@Controller
@RequestMapping("/seller/product")
public class SellerProductController {

    @Autowired
    private ProductInfoService productInfoService;
    @Autowired
    private ProductCategoryService productCategoryService;

    /**
     * 查看商品列表
     *
     * @return 跳转list.ftl
     */
    @GetMapping("/list")
    public ModelAndView showList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                 @RequestParam(value = "size", defaultValue = "5") Integer size,
                                 Map<String, Object> map) {

        PageRequest pageRequest = new PageRequest(page - 1, size);
        Page<ProductInfo> productInfos = productInfoService.findAll(pageRequest);
        map.put("productInfos", productInfos);
        map.put("currentPage", page);
        map.put("size", size);
        return new ModelAndView("/product/list", map);
    }

    /**
     * 商品上架
     *
     * @return 跳转success.ftl
     */
    @GetMapping("/on_sale")
    public ModelAndView onSale(@RequestParam("productId") String productId,
                               Map<String, Object> map) {
        try {
            productInfoService.onSale(productId);
        } catch (SellException e) {
            log.error("【商品上架】 发生异常 ", e);
            map.put("msg", e.getMessage());
            map.put("url", "http://tscc.natapp1.cc/sell/seller/product/list");
            return new ModelAndView("/common/error", map);
        }
        map.put("msg", ResultEnum.PRODUCT_ONSALE_SUCCESS.getMsg());
        map.put("url", "http://tscc.natapp1.cc/sell/seller/product/list");
        return new ModelAndView("/common/success", map);
    }

    /**
     * 商品下架
     *
     * @return 跳转success.ftl
     */
    @GetMapping("/off_sale")
    public ModelAndView offSale(@RequestParam("productId") String productId,
                                Map<String, Object> map) {

        try {
            productInfoService.offSale(productId);
        } catch (SellException e) {
            log.error("【商品下架】 发生异常 ", e);
            map.put("msg", e.getMessage());
            map.put("url", "http://tscc.natapp1.cc/sell/seller/product/list");
            return new ModelAndView("/common/error", map);
        }
        map.put("msg", ResultEnum.PRODUCT_OFFSALE_SUCCESS.getMsg());
        map.put("url", "http://tscc.natapp1.cc/sell/seller/product/list");
        return new ModelAndView("/common/success", map);
    }

    /**
     * 展示添加/修改页面
     *
     * @return 跳转添加/修改商品表单填写页面
     */
    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "productId", required = false) String productId,
                              Map<String, Object> map) {

        if (!StringUtils.isEmpty(productId)) {
            ProductInfo productInfo = productInfoService.findOne(productId);
            map.put("productInfo", productInfo);
        }
        List<ProductCategory> productCategoryList = productCategoryService.findAll();
        map.put("productCategoryList", productCategoryList);
        return new ModelAndView("/product/index", map);
    }

    /**
     * 保存/跟新商品
     * @param productForm 用来接收商品表单数据的
     * @param bindingResult  检验表单数据
     * @param map 用来存放页面渲染的参数
     * @return 跳转success.ftl
     */
    @PostMapping("/save")
    public ModelAndView save(@Valid ProductForm productForm,
                             BindingResult bindingResult,
                             Map<String, Object> map) {
        //判断表单校验是否通过
        if (bindingResult.hasErrors()) {
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            if (StringUtils.isEmpty(productForm.getProductId())) {
                map.put("url", "/sell/seller/product/index");
            } else {
                map.put("url", "/sell/seller/product/index?productId=" + productForm.getProductId());
            }
            return new ModelAndView("/common/error", map);
        }
        //创建product对象  用来接收表单对象的值
        ProductInfo productInfo = new ProductInfo();
        try {
            //如果productId为空, 说明是新增
            if (StringUtils.isEmpty(productForm.getProductId())) {
                //给个ID  和默认是下架状态
                productForm.setProductId(KeyUtil.getUniqueKey());
                productForm.setProductStatus(ProductStatusEnum.DOWN.getCode());
            } else {
                //查询到这个商品
                productInfo = productInfoService.findOne(productForm.getProductId());
                //为了让copy后的Status不为null  给表单对象赋个值
                productForm.setProductStatus(productInfo.getProductStatus());
            }
            //复制
            BeanUtils.copyProperties(productForm, productInfo);
            //写入数据库
            productInfoService.save(productInfo);
        } catch (SellException e) {
            log.error("【商品增加/修改】 发生异常 ", e);
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/product/index");
            return new ModelAndView("/common/error", map);
        }
        //携带参数
        map.put("msg", ResultEnum.PRODUCT_UPDATE_SUCCESS.getMsg());
        map.put("url", "/sell/seller/product/list");
        //页面渲染
        return new ModelAndView("/common/success", map);
    }

    /**
     * 删除
     * @param productId 商品Id
     * @param map 用来存放页面渲染的参数
     * @return 跳转success.ftl
     */
    @GetMapping("/delete")
    public ModelAndView delete(@RequestParam("productId") String productId,
                               Map<String,Object> map){

        try {
            productInfoService.delete(productId);

        }catch (SellException e){

            log.error("【删除商品】 发生异常 ", e);
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/product/list");
            return new ModelAndView("/common/error", map);
        }

        map.put("msg", ResultEnum.PRODUCT_DELETE_SUCCESS.getMsg());
        map.put("url", "/sell/seller/product/list");
        return new ModelAndView("/common/success", map);

    }

}
