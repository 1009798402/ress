package com.tscc.ress.controller;

import com.tscc.ress.database.ProductCategory;
import com.tscc.ress.enums.ResultEnum;
import com.tscc.ress.exception.SellException;
import com.tscc.ress.form.CategoryForm;
import com.tscc.ress.service.ProductCategoryService;
import com.tscc.ress.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
 * 描述:商家类目Controller
 *
 * @author C
 * Date: 2018-07-03
 * Time: 19:56
 */
@Slf4j
@Controller
@RequestMapping("/seller/category")
public class SellerCategoryController {

    @Autowired
    private ProductCategoryService productCategoryService;

    /**
     * 查看商品类目列表
     *
     * @return 跳转list.ftl
     */
    @GetMapping("/list")
    public ModelAndView showList(Map<String, Object> map){

        List<ProductCategory> productCategoryList = productCategoryService.findAll();
        map.put("productCategoryList", productCategoryList);

        return new ModelAndView("/category/list", map);
    }

    /**
     * 展示添加/修改页面
     *
     * @return 跳转添加/修改商品表单填写页面
     */
    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "categoryId", required = false) Integer categoryId,
                              Map<String, Object> map) {

        if (!StringUtils.isEmpty(categoryId)) {
            ProductCategory category = productCategoryService.findOne(categoryId);
            map.put("category", category);
        }
        return new ModelAndView("/category/index", map);
    }

    /**
     * 保存/跟新类目
     * @param categoryForm 用来接收商品表单数据的
     * @param bindingResult  检验表单数据
     * @param map 用来存放页面渲染的参数
     * @return 跳转success.ftl
     */
    @PostMapping("/save")
    public ModelAndView save(@Valid CategoryForm categoryForm,
                             BindingResult bindingResult,
                             Map<String, Object> map) {
        //判断表单校验是否通过
        if (bindingResult.hasErrors()) {
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            if (StringUtils.isEmpty(categoryForm.getCategoryId())) {
                map.put("url", "/sell/seller/category/index");
            } else {
                map.put("url", "/sell/seller/category/index?categoryId=" + categoryForm.getCategoryId());
            }
            return new ModelAndView("/common/error", map);
        }

        //创建category对象  用来接收表单对象的值
        ProductCategory category = new ProductCategory();

        try {
            //如果表单productId不为空, 说明是修改
            if ( ! StringUtils.isEmpty(categoryForm.getCategoryId())) {
                //查询到这个商品
                category = productCategoryService.findOne(categoryForm.getCategoryId());
                //判断表单中的type或者name是否改过
                if(! categoryForm.getCategoryType().equals(category.getCategoryType()) ||
                        ! (categoryForm.getCategoryName().equals(category.getCategoryName()))){
                    //比较数据库中的type和name字段是否已经存在
                    if (productCategoryService.isExistCategoryType(categoryForm.getCategoryType(),
                            categoryForm.getCategoryName())){
                        //如果存在  抛出异常
                        log.error("【类目增加/修改】 发生异常  类目Type已存在  categoryType = {}",categoryForm.getCategoryType());
                        map.put("msg", ResultEnum.CATEGORY_TYPEORNAME_EXIST.getMsg());
                        map.put("url", "/sell/seller/category/index?categoryId=" + categoryForm.getCategoryId() );
                        return new ModelAndView("/common/error", map);
                    }
                }
            }else {
                //新增
                categoryForm.setCategoryId(KeyUtil.getCategoryId());
                //比较数据库中的type和name字段是否已经存在
                if (productCategoryService.isExistCategoryType(categoryForm.getCategoryType(),categoryForm.getCategoryName())){
                    //如果存在  抛出异常
                    log.error("【类目增加/修改】 发生异常  类目Type已存在  categoryType = {}",categoryForm.getCategoryType());
                    map.put("msg", ResultEnum.CATEGORY_TYPEORNAME_EXIST.getMsg());
                    map.put("url", "/sell/seller/category/index?productId"  );
                    return new ModelAndView("/common/error", map);
                }
            }
            //复制
            BeanUtils.copyProperties(categoryForm, category);
            //写入数据库
            productCategoryService.save(category);

        } catch (SellException e) {
            log.error("【类目增加/修改】 发生异常 ", e);
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/category/index");
            return new ModelAndView("/common/error", map);
        }
        //携带参数
        map.put("msg", ResultEnum.CATEGORY_UPDATE_SUCCESS.getMsg());
        map.put("url", "/sell/seller/category/list");
        //页面渲染
        return new ModelAndView("/common/success", map);
    }

    /**
     * 删除
     * @param categoryId 类目Id
     * @param map 用来存放页面渲染的参数
     * @return 跳转success.ftl
     */
    @GetMapping("/delete")
    public ModelAndView delete(@RequestParam("categoryId") Integer categoryId,
                               Map<String,Object> map){

        try {
            productCategoryService.delete(categoryId);

        }catch (SellException e){

            log.error("【删除类目】 发生异常 ", e);
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/category/list");
            return new ModelAndView("/common/error", map);
        }

        map.put("msg", ResultEnum.CATEGORY_DELETE_SUCCESS.getMsg());
        map.put("url", "/sell/seller/category/list");
        return new ModelAndView("/common/success", map);
    }

}
