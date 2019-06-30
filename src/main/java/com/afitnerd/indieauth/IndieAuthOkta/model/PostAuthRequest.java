package com.afitnerd.indieauth.IndieAuthOkta.model;

public class PostAuthRequest extends BaseAuthRequest {

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
