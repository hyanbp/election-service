package com.hyan.electionservice.excepction.config;

import org.springframework.http.HttpStatus;

import java.util.Map;

public abstract class HttpException  extends ApiException {


    public HttpException() {
        super();
    }

    public HttpException(Throwable cause) {
        super(cause);
    }

    public HttpException(SimpleError error) {
        super(error);
    }

    public HttpException(Throwable cause, SimpleError error) {
        super(cause, error);
    }

    public HttpException(String message, Map<String, String>... args) {
        super(message, args);
    }

    public abstract HttpStatus getHttpStatus();
}
