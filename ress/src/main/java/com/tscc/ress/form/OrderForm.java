package com.tscc.ress.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 描述:用来进行订单表单验证
 *
 * @author C
 * Date: 2018-06-30
 * Time: 20:37
 */
@Data
public class OrderForm {

    @NotEmpty(message = "姓名必填")
    private String name;
    @NotEmpty(message = "电话必填")
    private String phone;
    @NotEmpty(message = "地址必填")
    private String address;
    @NotEmpty(message = "openid不能为空")
    private String openid;
    @NotEmpty(message = "购买的商品不能为空")
    private String items;

}
