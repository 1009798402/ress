package com.tscc.ress.utils;

import java.util.Random;

/**
 * 生成随机数的工具类
 * 2017-06-11 19:12
 */
public class KeyUtil {

    /**
     * 生成唯一的主键
     * 格式: 时间+随机数
     */
    public static synchronized String getUniqueKey() {
        Random random = new Random();
        Integer number = random.nextInt(900000) + 100000;

        return System.currentTimeMillis() + String.valueOf(number);
    }

    /**
     * 每新增一个category自增1  理论上不会重复
     */
    public static synchronized Integer getCategoryId(){
        Integer categoryId = 10086;
        categoryId ++;
        return categoryId;
    }
}
