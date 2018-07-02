package com.tscc.ress.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述:订单状态的枚举
 *
 * @author C
 * @date 20:03 2018/6/29/029
 */
@Getter
@AllArgsConstructor
public enum OrderStatusEnum implements CodeEnum{

    /** 0表示新订单. */
    NEW(0,"新订单"),

    /** 1表示完结订单的状态. */
    FINISHED(1,"已完结"),

    /** 2表示取消订单的状态. */
    CANCEL(2,"已取消"),
    ;
    private Integer code;
    private String msg;
}
