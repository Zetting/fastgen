package com.fastgen.sample.action.base;

/**
 * 业务异常
 *
 * @author: zet
 * @date:2019/10/11
 */
public class ServerException extends RuntimeException {
    private Integer code;
    private String errorMessage;
    private Throwable cause;

    private ServerException() {
    }

    public ServerException(String message) {
        this.setCode(ResponseCode.FAILED.getCode());
        this.setErrorMessage(message);
    }

    public ServerException(ResponseCode code) {
        this.setCode(code.getCode());
        this.setErrorMessage(code.getMessage());
        this.cause = new Throwable(code.getMessage());
    }

    public ServerException(String msg, Throwable e) {
        super(msg);
        this.setCode(ResponseCode.FAILED.getCode());
        this.setErrorMessage(msg);
        this.cause = e;
    }

    public ServerException(ResponseCode businessCode, String errorMessage) {
        this.setCode(businessCode.getCode());
        this.setErrorMessage(errorMessage);
        this.cause = new Throwable(errorMessage);
    }

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Throwable getCause() {
        return this.cause;
    }

    public void setCause(Throwable cause) {
        this.cause = cause;
    }
}

