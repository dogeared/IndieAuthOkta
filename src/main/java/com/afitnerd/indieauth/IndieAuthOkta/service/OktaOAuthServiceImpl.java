package com.afitnerd.indieauth.IndieAuthOkta.service;

import com.afitnerd.indieauth.IndieAuthOkta.model.OAuthMetaData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.fluent.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

@Service
public class OktaOAuthServiceImpl implements OktaOAuthService {

    @Value("#{ @environment['okta.oauth2.issuer'] }")
    private String issuer;

    @Value("#{ @environment['okta.oauth2.client-id'] }")
    private String clientId;

    @Value("#{ @environment['okta.oauth2.client-secret'] }")
    private String clientSecret;

    @Value("#{ @environment['okta.oauth2.redirect-uri'] }")
    private String redirectUri;

    private static final String WELL_KNOWN_URI = "/.well-known/oauth-authorization-server";

    private OAuthMetaData oAuthMetaData;

    private ObjectMapper mapper = new ObjectMapper();

    @PostConstruct
    public void setup() throws IOException {
        InputStream is = Request.Get(issuer + WELL_KNOWN_URI)
            .execute()
            .returnContent()
            .asStream();
        oAuthMetaData = mapper.readValue(is, OAuthMetaData.class);
    }

    @Override
    public String buildAuthUrl(String state, String nonce) {
        return new StringBuilder(oAuthMetaData.getAuthorizationEndpoint())
            .append("?")
            .append("client_id=" + clientId)
            .append("&response_type=code")
            .append("&scope=openid")
            .append("&state=" + state)
            .append("&nonce=" + nonce)
            .append("&redirect_uri=" + redirectUri)
            .toString();
    }
}
