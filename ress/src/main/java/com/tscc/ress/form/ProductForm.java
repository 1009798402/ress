package com.tscc.ress.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 描述:用来校验增加修改商品表单提交的数据
 *
 * @author C
 * Date: 2018-07-03
 * Time: 14:28
 */
@Data
public class ProductForm {

    /** 商品id. */
    private String productId;

    /** 商品名称. */
    @NotBlank
    private String productName;

    /** 单价. */
    @NotNull
    private BigDecimal productPrice;

    /** 库存. */
    @NotNull
    private Integer productStock;

    /** 描述. */
    @NotBlank
    private String productDescription;

    /** 小图. */
    @NotBlank
    private String productIcon;

    /** 类目编号. */
    @NotNull
    private Integer categoryType;

    /** 商品状态,0正常1下架. */
    private Integer productStatus ;
}
