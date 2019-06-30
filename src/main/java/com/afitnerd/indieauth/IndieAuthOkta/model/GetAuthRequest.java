package com.afitnerd.indieauth.IndieAuthOkta.model;

public class GetAuthRequest extends BaseAuthRequest {

    private String me;
    private String state;
    private String response_type;

    public String getMe() {
        return me;
    }

    public void setMe(String me) {
        this.me = me;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getResponseType() {
        return response_type;
    }

    public void setResponse_type(String response_type) {
        this.response_type = response_type;
    }
}
