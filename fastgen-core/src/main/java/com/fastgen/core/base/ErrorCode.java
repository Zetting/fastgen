package com.fastgen.core.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 响应状态码
 *
 * @author: zet
 * @date: 2019/9/12 0:01
 */
@AllArgsConstructor
public enum ErrorCode {
    /**
     * 成功
     */
    SUCCESS(200L, "操作成功"),
    /**
     * 失败
     */
    FAILED(500L, "操作失败");


    /**
     * 响应码
     */
    @Getter
    private Long code;
    /**
     * 响应消息
     */
    @Getter
    private String message;
}
