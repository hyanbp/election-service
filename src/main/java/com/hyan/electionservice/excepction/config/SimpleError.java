package com.hyan.electionservice.excepction.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class SimpleError {

    private String message;
    private String code;
    private String traceId;
    private Object object;
    private Collection<SimpleError> details = new ArrayList<>();
    private Map<String, String> messageArgs = new HashMap<>();

    public SimpleError() {
    }

    public SimpleError(String message) {
        this.message = message;
    }

    public void addMessageArgs(String key, String value) {
        messageArgs.put(key, value);
    }

    public void addMessageArgs(Map<String, String> args) {
        messageArgs.putAll(args);
    }


    public void addDetail(SimpleError error) {
        this.details.add(error);
    }

    public void addDetail(String detailMessage) {
        addDetail(new SimpleError(detailMessage));
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public Collection<SimpleError> getDetails() {
        return details;
    }

    public void setDetails(Collection<SimpleError> details) {
        this.details = details;
    }

    public Map<String, String> getMessageArgs() {
        return messageArgs;
    }

    public void setMessageArgs(Map<String, String> messageArgs) {
        this.messageArgs = messageArgs;
    }
}
