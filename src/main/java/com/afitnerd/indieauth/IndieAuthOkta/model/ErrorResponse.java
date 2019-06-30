package com.afitnerd.indieauth.IndieAuthOkta.model;

import java.util.Arrays;

public class ErrorResponse {

    private String message;
    private StackTraceElement[] stackTrace;
    private String exception;

    public ErrorResponse() {}

    public ErrorResponse(Exception e) {
        StackTraceElement[] stackTrace = e.getStackTrace();
        this.stackTrace = Arrays.copyOf(stackTrace, stackTrace.length < 5 ? stackTrace.length : 5);
        this.message = e.getMessage();
        this.exception = e.getClass().getName();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public StackTraceElement[] getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(StackTraceElement[] stackTrace) {
        this.stackTrace = stackTrace;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }
}
