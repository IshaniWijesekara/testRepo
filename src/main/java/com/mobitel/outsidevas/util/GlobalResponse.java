package com.mobitel.outsidevas.util;

import java.util.Map;

public class GlobalResponse<T> implements IGlobalResponse {
    private ResponseHeader responseHeader;
    private Map<String, Object> data;
    private T body;

    public static <T> GlobalResponse<T> error() {
        GlobalResponse<T> response = new GlobalResponse<>();
        HeaderMapper.systemError(response, null, false);
        return response;
    }

    @Override
    public ResponseHeader getResponseHeader() {
        return this.responseHeader;
    }

    @Override
    public void setResponseHeader(ResponseHeader responseHeader) {
        this.responseHeader = responseHeader;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }
}
