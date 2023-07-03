package com.yupi.springbootinit.config;

import lombok.Data;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : CJ
 * @create 2023/7/1 18:48
 **/
@Configuration
@ConfigurationProperties(prefix = "spring.redis")
@Data
public class RedissonConfig {

    private String host;

    private String port;

    private String password;


    @Bean
    public RedissonClient getRedissonClient(){
        Config config = new Config();
        String address=String.format("redis://%s:%s",host,port);

        config.useSingleServer()
                // use "rediss://" for SSL connection
                .setAddress(address).setPassword(password).setDatabase(3);


        RedissonClient redissonClient=Redisson.create(config);
        return  redissonClient;
    }
}
