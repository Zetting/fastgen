package com.fastgen.core.base;

import lombok.Data;

import java.util.Objects;

/**
 * Api统一响应
 *
 * @author: zet
 * @date: 2019/9/11 23:32
 */
@Data
public class Response<T> {
    /**
     * 响应码
     */
    private Long code;
    /**
     * 消息
     */
    private String message;
    /**
     * 数据
     */
    private T data;

    public static <T> Response<T> success() {
        return new Response(ErrorCode.SUCCESS.getCode(), ErrorCode.SUCCESS.getMessage(), null);
    }

    public static <T> Response<T> success(T data, String message) {
        return new Response(ErrorCode.SUCCESS.getCode(), message, data);
    }

    public static Response failed(String message) {
        return new Response(ErrorCode.FAILED.getCode(), message, null);
    }


    public static Response failed() {
        return new Response(ErrorCode.FAILED.getCode(), ErrorCode.FAILED.getMessage(), null);
    }


    public static boolean isSuccessful(Response response) {
        if (Objects.nonNull(response)) {
            return ErrorCode.SUCCESS.getCode() == response.getCode();
        } else {
            return false;
        }
    }

    protected Response() {
    }

    protected Response(long code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
