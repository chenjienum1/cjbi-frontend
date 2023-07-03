package com.yupi.springbootinit.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : CJ
 * @create 2023/7/2 13:37
 **/
@Component
public class ThreadPoolExecutorConfig {

    @Bean
    public ThreadPoolExecutor threadPoolExecutor(){

        ThreadFactory threadFactory=new ThreadFactory() {

            private int num=1;

            @Override
            public Thread newThread(@NotNull Runnable r) {
                Thread thread=new Thread(r,"线程"+(num++));
                return thread;
            }
        };


        ThreadPoolExecutor threadPoolExecutor=new ThreadPoolExecutor(2,4,10, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(4),threadFactory);

        return  threadPoolExecutor;
    }
}
