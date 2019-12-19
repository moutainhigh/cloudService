package com.cold.searchservice.exception;

import com.cold.response.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: ohj
 * @Date: 2018/8/15 08:18
 * @Description:全局异常处理
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public CommonResult handleException(Exception e) {
        log.error(ExceptionUtils.getMessage(e));  // 记录错误信息
//        boolean success = false;
        String msg = e.getMessage();
//        if (msg == null || msg.equals("")) {
//            msg = "服务器出错";
//            //success = false;
//        }
//        Map map = new HashMap(5);
//        map.put("code", 500);
//        map.put("msg", msg);
//        map.put("success", success);
        log.error(msg,e);
        return CommonResult.failed(msg);
    }
}