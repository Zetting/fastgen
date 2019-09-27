package com.fastgen.core.base;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常捕获
 *
 * @author zet
 * @date 2018-11-23
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    /**
     * 处理所有不可知的异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Response handleException(HttpServletRequest request, Exception e) {
        // 打印堆栈信息
        log.error("系统异常[{}]", e);
        return Response.failed("系统异常");
    }

    /**
     * 处理 ServerException
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = ServerException.class)
    public Response ServerException(HttpServletRequest request, ServerException e) {
        log.error("业务异常[{}]", e);
        return Response.failed(e.getErrorMessage());
    }


}
