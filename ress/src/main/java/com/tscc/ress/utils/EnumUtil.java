package com.tscc.ress.utils;

import com.tscc.ress.enums.CodeEnum;

/**
 * 描述:根据code查询枚举的工具类
 *
 * @author C
 * Date: 2018-07-02
 * Time: 14:43
 */
public class EnumUtil {

    public static <T extends CodeEnum> T getByCode(Integer code, Class<T> enumClass) {
        for (T each : enumClass.getEnumConstants()) {
            if (code.equals(each.getCode())) {
                return each;
            }
        }
        return null;
    }
}
