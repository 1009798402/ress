package com.tscc.ress.vo;

import lombok.Data;

/**
 * 描述: 用来返回结果的vo
 *
 * @author C
 * @date 19:58 2018/6/29
 */
@Data
public class ResultVo<T> {
    /** 错误码.*/
    private Integer code;
    /** 提示信息.*/
    private String msg;
    /** 返回数据.*/
    private Object data;
}
