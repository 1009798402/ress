package com.tscc.ress.enums;

import lombok.*;

/**
 * 描述:商品状态枚举
 *
 * @author C
 * @date 18:06 2018/6/29/029
 */
@Getter
@AllArgsConstructor
public enum ProductStatusEnum {

    /** 0表示在架. */
    ONLINE(0,"在架"),

    /** 1表示下架. */
    DOWN(1,"下架"),
    ;
    private Integer code;
    private String msg;
}
