package com.tscc.ress.enums;

import lombok.*;

/**
 * 描述:商品状态枚举
 *
 * @author C
 * @date 18:06 2018/6/29/029
 */
@AllArgsConstructor
@Getter
public enum ProductStatusEnum {
    /** 在架*/
    ONLINE(0),
    /** 下架*/
    DOWN(1)
    ;
    private Integer code;
}
