package com.yupi.springbootinit.manager;

import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.exception.BusinessException;
import com.yupi.yucongming.dev.client.YuCongMingClient;
import com.yupi.yucongming.dev.common.BaseResponse;
import com.yupi.yucongming.dev.model.DevChatRequest;
import com.yupi.yucongming.dev.model.DevChatResponse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : CJ
 * @create 2023/6/30 13:57
 **/
@Service
public class AiManager {

    @Resource
    private YuCongMingClient client;

    public String doChart(String message,long modelId){
        DevChatRequest devChatRequest = new DevChatRequest();
        devChatRequest.setModelId(modelId);
        devChatRequest.setMessage(message);

        BaseResponse<DevChatResponse> response = client.doChat(devChatRequest);
        if(response==null){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"AI 响应错误");
        }
        return  response.getData().getContent();
    }
}
