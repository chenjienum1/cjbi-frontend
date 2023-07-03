package com.yupi.springbootinit.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.yupi.springbootinit.annotation.AuthCheck;
import com.yupi.springbootinit.common.BaseResponse;
import com.yupi.springbootinit.common.DeleteRequest;
import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.common.ResultUtils;
import com.yupi.springbootinit.constant.CommonConstant;
import com.yupi.springbootinit.constant.UserConstant;
import com.yupi.springbootinit.exception.BusinessException;
import com.yupi.springbootinit.exception.ThrowUtils;
import com.yupi.springbootinit.manager.AiManager;
import com.yupi.springbootinit.manager.RedissonLimitManager;
import com.yupi.springbootinit.model.dto.chart.ChartAddRequest;
import com.yupi.springbootinit.model.dto.chart.ChartEditRequest;
import com.yupi.springbootinit.model.dto.chart.ChartQueryRequest;
import com.yupi.springbootinit.model.dto.chart.ChartUpdateRequest;
import com.yupi.springbootinit.model.dto.file.GenChartByAiRequest;
import com.yupi.springbootinit.model.entity.Chart;
import com.yupi.springbootinit.model.entity.User;
import com.yupi.springbootinit.model.vo.BiResponse;
import com.yupi.springbootinit.service.ChartService;
import com.yupi.springbootinit.service.UserService;
import com.yupi.springbootinit.utils.ExcelUtils;
import com.yupi.springbootinit.utils.SqlUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 测试线程池
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@RestController
@RequestMapping("/queue")
@Slf4j
public class QueueController {

   @Resource
   ThreadPoolExecutor threadPoolExecutor;


    @GetMapping("/add")
    public void addQueue(String name){
        CompletableFuture.runAsync(()->{
            System.out.println("任务名："+name+"线程名字："+Thread.currentThread().getName());
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        },threadPoolExecutor);
    }

    @GetMapping("/get")
    public String getQueue(){
        Map<String,Object> map=new HashMap<>();
        int poolSize = threadPoolExecutor.getQueue().size();
        map.put("队列长度：",poolSize);
        int corePoolSize = threadPoolExecutor.getCorePoolSize();
        map.put("核心线程数：",corePoolSize);
        int maximumPoolSize = threadPoolExecutor.getMaximumPoolSize();
        map.put("最大线程数：",maximumPoolSize);
        int activeCount = threadPoolExecutor.getActiveCount();
        map.put("存活的线程数：",activeCount);
        return JSONUtil.toJsonStr(map);
    }
}
