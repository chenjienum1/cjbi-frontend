package com.yupi.springbootinit.manager;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : CJ
 * @create 2023/7/1 19:44
 **/
@SpringBootTest
class RedissonLimitManagerTest {

    @Resource
    private RedissonLimitManager redissonLimitManager;

    @Test
    void test1(){

        for (int i=0;i<5;i++){
            redissonLimitManager.doRateLimit("123");
            System.out.println("成功");
        }


    }
}