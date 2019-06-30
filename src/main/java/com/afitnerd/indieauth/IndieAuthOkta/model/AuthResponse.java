package com.afitnerd.indieauth.IndieAuthOkta.model;

public class AuthResponse {

    private String me;

    public AuthResponse() {}

    public AuthResponse(String me) {
        this.me = me;
    }

    public String getMe() {
        return me;
    }

    public void setMe(String me) {
        this.me = me;
    }
}
