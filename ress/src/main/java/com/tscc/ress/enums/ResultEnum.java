package com.tscc.ress.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述:返回异常结果的枚举
 *
 * @author C
 * Date: 2018-06-30
 * Time: 11:18
 */
@Getter
@AllArgsConstructor
public enum ResultEnum {

    /** 1表示参数不正确. */
    PARAM_ERROR(1, "参数不正确"),

    /** 2表示格式转换错误. */
    CONVERTER_ERROR(2, "格式转换错误"),

    /** 3表示openid不相同. */
    OPENID_NOT_EQUAL(3,"openid不相同"),

    /** 订单支付价格与订单价格不一致. */
    ORDER_AMOUNT_NOT_EQUAL(4,"订单支付价格与订单价格不一致"),
    /** 10表示商品不存在. */
    PRODUCT_NOT_EXIST(10, "商品不存在"),

    /** 11表示库存不足. */
    PRODUCT_INVENTORY_INVENTORY(11, "商品库存不足"),

    /** 12表示该订单不存在. */
    MASTER_NOT_EXIST(12, "订单不存在"),

    /** 13表示商品详情不存在. */
    DETAIL_NOT_EXIST(13, "订单详情不存在"),

    /** 14表示订单状态不正确. */
    ORDER_STATUS_ERROR(14, "订单状态不正确"),

    /** 15表示订单更新失败. */
    ORDER_UPDATE_FAIL(15, "订单更新失败"),

    /** 16表示订单详情为空. */
    ORDER_DETAIL_EMPTY(16, "订单详情为空"),

    /** 17表示订单支付状态不正确. */
    PAY_STATUS_ERROR(17,"订单支付状态不正确"),

    /** 18表示购物车不能为空. */
    CART_NOT_EMPTY(18, "购物车不能为空"),

    /** 19表示openid不能为空*/
    OPENID_NOT_EMPTY(19,"openid不能为空"),

    /** 20表示微信公众号账号方面错误*/
    WECHAT_MP_ERROR(20,"微信公众号账号方面错误"),
    ;
    private Integer code;
    private String msg;
}
