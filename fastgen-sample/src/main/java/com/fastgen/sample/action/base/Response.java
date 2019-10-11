package com.fastgen.sample.action.base;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * 通用返回对象
 *
 * @author zet
 * @date 2019/4/19
 */
public class Response<T> implements Serializable {

    @ApiModelProperty("响应码")
    private Integer code;

    @ApiModelProperty("响应消息")
    private String message;

    @ApiModelProperty("响应数据")
    private T data;

    /**
     * 成功返回结果
     */
    public static <T> Response<T> success() {
        return new Response(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), null);
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     */
    public static <T> Response<T> success(T data) {
        return new Response(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), data);
    }

    /**
     * 成功返回结果
     *
     * @param data    获取的数据
     * @param message 提示信息
     */
    public static <T> Response<T> success(T data, String message) {
        return new Response(ResponseCode.SUCCESS.getCode(), message, data);
    }

    /**
     * 失败返回结果
     *
     * @param code 错误码
     */
    public static <T> Response<T> failed(ResponseCode code) {
        return new Response(code.getCode(), code.getMessage(), null);
    }

    /**
     * 失败返回结果
     *
     * @param code 错误码
     */
    public static <T> Response<T> failed(ResponseCode code, String message) {
        return new Response(code.getCode(), message, null);
    }

    /**
     * 失败返回结果
     *
     * @param message 提示信息
     */
    public static <T> Response<T> failed(String message) {
        return new Response(ResponseCode.FAILED.getCode(), message, null);
    }

    /**
     * 失败返回结果
     */
    public static <T> Response<T> failed() {
        return failed(ResponseCode.FAILED);
    }

    /**
     * 参数验证失败返回结果
     */
    public static <T> Response<T> validateFailed() {
        return failed(ResponseCode.PARAM_ILLEGAL);
    }

    /**
     * 参数验证失败返回结果
     *
     * @param message 提示信息
     */
    public static <T> Response<T> validateFailed(String message) {
        return new Response(ResponseCode.PARAM_ILLEGAL.getCode(), message, null);
    }

    /**
     * 判断是否成功
     *
     * @return
     */
    public static boolean isSuccessful(Response response) {
        if (Objects.nonNull(response)) {
            return ResponseCode.SUCCESS.getCode().equals(response.getCode());
        }
        return false;
    }

    protected Response() {
    }

    protected Response(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
