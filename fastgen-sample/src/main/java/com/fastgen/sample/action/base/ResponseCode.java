package com.fastgen.sample.action.base;

/**
 * 基础响应码
 *
 * @Author: zet
 * @Date: 2018-02-01 23:08
 */
public class ResponseCode {
    /**
     * 成功
     */
    public static final ResponseCode SUCCESS = new ResponseCode(200, "SUCCESS");

    /**
     * 失败
     */
    public static final ResponseCode FAILED = new ResponseCode(501, "FAIL");
    public static final ResponseCode SYS_EXCEPTION = new ResponseCode(500, "EXCEPTION");
    public static final ResponseCode PARAM_ILLEGAL = new ResponseCode(400, "PARAM_ILLEGAL");
    public static final ResponseCode UN_LOGIN = new ResponseCode(401, "UN_LOGIN");
    public static final ResponseCode FORBIDDEN = new ResponseCode(403, "UN_PERMISSION");

    private Integer code;

    private String message;

    public ResponseCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    public Integer getCode() {
        return code;
    }
    public String getMessage() {
        return message;
    }
}
