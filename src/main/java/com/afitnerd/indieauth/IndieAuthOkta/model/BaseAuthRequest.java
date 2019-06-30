package com.afitnerd.indieauth.IndieAuthOkta.model;

// breaking java norms. is there a better way?
public class BaseAuthRequest {

    private String redirect_uri;
    private String client_id;

    public String getRedirectUri() {
        return redirect_uri;
    }

    public void setRedirect_uri(String redirect_uri) {
        this.redirect_uri = redirect_uri;
    }

    public String getCliendId() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }
}
