package com.tscc.ress.log;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * LoggerTest
 *
 * @author c
 * @date 2016/10/31
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class LoggerTest {

    @Test
    public void test1(){
        String name = "张三";
        Integer age = 20;
        String sex = "男";
        log.debug("debug");
        log.info("name :{},  age :{},  sex :{}",name,age,sex);
        log.error("发生error");
    }
}
