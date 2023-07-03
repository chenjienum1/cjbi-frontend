package com.yupi.springbootinit.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by IntelliJ IDEA.
 * Excel 相关工具类
 * @Author : CJ
 * @create 2023/6/30 9:07
 **/
@Slf4j
public class ExcelUtils {


    /**
     * excel 转 CSV
     * @param multipartFile
     * @return
     */
    public static String excelToCsv(MultipartFile multipartFile){

        List<Map<Integer, String>> list = null;
        try {
            list = EasyExcel.read(multipartFile.getInputStream())
                    .excelType(ExcelTypeEnum.XLSX)
                    .sheet()
                    .headRowNumber(0)
                    .doReadSync();
        } catch (IOException e) {
            log.error("IO 错误");
            throw new RuntimeException(e);
        }
        System.out.println(list);
        StringBuffer sb=new StringBuffer();
        LinkedHashMap<Integer,String> headerMap = (LinkedHashMap) list.get(0);
        List<String> headList = headerMap.values().stream().filter(ObjectUtils::isNotEmpty).collect(Collectors.toList());
        sb.append(StringUtils.join(headList,","));
        sb.append("\n");

        for(int i=1;i<list.size();i++){
            LinkedHashMap<Integer,String> bodyMap = (LinkedHashMap) list.get(i);
            List<String> bodyList = bodyMap.values().stream().filter(ObjectUtils::isNotEmpty).collect(Collectors.toList());
            sb.append(StringUtils.join(bodyList,","));
            sb.append("\n");

        }
        return sb.toString();
    }
}
