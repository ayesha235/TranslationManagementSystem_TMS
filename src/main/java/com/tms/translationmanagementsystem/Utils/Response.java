package com.tms.translationmanagementsystem.Utils;

public class Response<T> {
    private String type;
    private String source;
    private int code;
    private T data;

    public Response(String type, String source, int code, T data) {
        this.type = type;
        this.source = source;
        this.code = code;
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public String getSource() {
        return source;
    }

    public int getCode() {
        return code;
    }

    public T getData() {
        return data;
    }
}

