package com.fastgen.core.base;

/**
 * 业务异常
 *
 * @author: zet
 * @date: 2019/9/12 0:00
 */
public class ServerException extends RuntimeException {
    private Long code;
    private String errorMessage;
    private Throwable cause;

    private ServerException() {
    }

    public ServerException(String message) {
        this.setCode(ErrorCode.FAILED.getCode());
        this.setErrorMessage(message);
    }


    public Long getCode() {
        return this.code;
    }

    public void setCode(Long code) {
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
