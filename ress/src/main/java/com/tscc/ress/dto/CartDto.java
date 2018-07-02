package com.tscc.ress.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 描述:传递购物车相关数据
 *
 * @author C
 * Date: 2018-06-30
 * Time: 12:27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {

    /** 商品Id */
    private String productId;

    /** 要增加或减少的数量 */
    private Integer productQuantity;
}
