package com.tscc.ress.utils;

import com.tscc.ress.vo.ResultVo;

/**
 * 描述:返回ResultVo的工具类
 *
 * @author C
 * @date 21:44 2018/6/29/029
 */
public class SellResult {
    public static ResultVo success(Object data){
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(0);
        resultVo.setMsg("成功");
        resultVo.setData(data);
        return resultVo;
    }

    public static ResultVo success(){
        return success(null);
    }

    public static ResultVo error(Integer code,String msg){
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(code);
        resultVo.setMsg(msg);
        return resultVo;
    }
}
