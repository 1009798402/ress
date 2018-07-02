package com.tscc.ress.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述:支付状态的枚举
 *
 * @author C
 * @date 20:03 2018/6/29/029
 */
@Getter
@AllArgsConstructor
public enum  PayStatusEnum {

    /** 0表示等待支付. */
    WAIT(0,"等待支付"),

    /** 1表示支付成功. */
    SUCCESS(1,"支付成功"),
    ;
    private Integer code;
    private String msg;
}
