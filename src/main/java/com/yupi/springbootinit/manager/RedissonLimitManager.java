package com.yupi.springbootinit.manager;

import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.exception.BusinessException;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : CJ
 * @create 2023/7/1 19:05
 **/

/**
 * 专门提供RedissonLimiter限流基础服务的。
 */
@Service
public class RedissonLimitManager {

    @Resource
    RedissonClient redissonClient;

    /**
     * 限流操作
     * @param key 区分不同的限流器，比如不同的用户id应该分别统计
     */
    public void doRateLimit(String key){
        // 创建漏桶限流器
        RRateLimiter limiter = redissonClient.getRateLimiter(key);

        // 初始化限流器，设置容量为5个请求，每秒处理2个请求
        limiter.trySetRate(RateType.OVERALL, 2, 5, RateIntervalUnit.SECONDS);

        // 尝试获取令牌，如果获取成功则进行业务处理，否则进行限流处理
        boolean acquired = limiter.tryAcquire();
        if (acquired) {
            // 业务处理逻辑
            System.out.println("获得了令牌，进行业务处理");
        } else {
            // 限流处理逻辑
            System.out.println("限流处理，请稍后再试");
            throw new BusinessException(ErrorCode.TO_MANY_REQUEST);
        }

    }
}
