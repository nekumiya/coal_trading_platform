package com.guet.coal_trading_platform.config;

import com.fasterxml.jackson.core.JsonParseException;
import com.guet.coal_trading_platform.common.api.CommonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * @author: Hps
 * @date: 2021/3/18 20:32
 * @description: 全局异常处理
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger= LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResult<Object> handler(MethodArgumentNotValidException e){
        logger.warn("参数解析出现异常");
        BindingResult bindingResult = e.getBindingResult();
        if (bindingResult.hasErrors()){
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            if (!allErrors.isEmpty()){
                return CommonResult.failed(((FieldError)allErrors.get(0)).getDefaultMessage());
            }
        }
        return CommonResult.validateFailed();
    }

    @ExceptionHandler(JsonParseException.class)
    public CommonResult<Object> handleParse(JsonParseException e){
        logger.warn("json 解析出错");
        return CommonResult.failed("Json数据解析出错:"+e.getMessage());
    }



}
