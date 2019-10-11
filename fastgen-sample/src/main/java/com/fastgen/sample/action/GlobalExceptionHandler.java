package com.fastgen.sample.action;

import com.fastgen.sample.action.base.Response;
import com.fastgen.sample.action.base.ResponseCode;
import com.fastgen.sample.action.base.ServerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 全局捕获
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
    public Response handleException(Exception e) {
        log.error("系统异常", e);
        return Response.failed(ResponseCode.SYS_EXCEPTION,
                ResponseCode.SYS_EXCEPTION.getMessage());
    }

    /**
     * 处理 ServerException
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = ServerException.class)
    public Response ServerException(HttpServletRequest request, ServerException e) {
        return Response.failed(e.getErrorMessage());
    }


    /**
     * 请求的 JSON 参数在请求体内的参数校验
     *
     * @param e 异常信息
     * @return 返回数据
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public Response<String> handleBindException1(BindException e) {
        String errorMessage = paramError(e);
        return Response.failed(ResponseCode.PARAM_ILLEGAL, errorMessage);
    }

    /**
     * 获取参数异常错误提示
     *
     * @param e
     * @return
     */
    private String paramError(BindException e) {
        List<FieldError> errors = e.getFieldErrors();
        if (CollectionUtils.isEmpty(errors)) {
            return "";
        }
        StringBuilder strb = new StringBuilder();
        errors.forEach(errorInfo -> {
            String singleMessage = errorInfo.getField() + errorInfo.getDefaultMessage() + ";";
            strb.append(singleMessage);
        });
        return strb.toString();
    }


}
