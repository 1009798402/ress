package com.tscc.ress.exception;

import com.tscc.ress.enums.ResultEnum;

/**
 * 描述:Sell中的异常(自定义)
 *
 * @author C
 * Date: 2018-06-30
 * Time: 11:17
 */
public class SellException extends RuntimeException{

    private Integer code;

    public SellException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }

    public SellException(Integer code,String message) {
        super(message);
        this.code = code;
    }
}
